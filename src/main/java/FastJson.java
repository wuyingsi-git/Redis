import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;

public class FastJson {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1",6379);
        System.out.println("连接成功");
        Articles articles=new Articles();
        articles.setTitle("fastjson");
        articles.setAuthor("author");
        articles.setContent("cc");
        articles.setTime("2019");
        Long articlesId = saveArticles(articles,jedis);
        System.out.println("保存成功");
        Articles articles1=getArticles(jedis,articlesId);
        System.out.println(articles1);
        System.out.println("success");
        articles.setTime("2017");
        Long id1=updArticles(articles,jedis,articlesId);
        Articles articles2=getArticles(jedis,id1);
        System.out.println(articles2);
    }
    public static Long saveArticles(Articles articles,Jedis jedis){
        long articlesId=jedis.incr("post");
        String posts= JSON.toJSONString(articles);
        jedis.set("post"+articlesId+"date:",posts);
        return articlesId;
    }
    public static Articles getArticles(Jedis jedis,long postId){
        String postid=jedis.get("post:" + postId + ":data");
        Articles articles=JSON.parseObject(postid, Articles.class);
        return articles;
    }
    public static Long delArticles(Jedis jedis,Long articlesId){
        jedis.del("post:"+articlesId+":data");
        return articlesId;
    }
    public static Long updArticles(Articles articles,Jedis jedis,Long articlesId){
        Long pid=articlesId;
        String stu= JSON.toJSONString(articles);
        jedis.set("post:"+pid+":data",stu);
        return pid;
    }
}
