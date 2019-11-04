import redis.clients.jedis.Jedis;
import java.util.HashMap;
import java.util.Map;

public class HestDemo {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1",6379);
        System.out.println("连接成功");
        Articles articles=new Articles();
        articles.setTitle("fastjson");
        articles.setAuthor("author");
        articles.setContent("cc");
        articles.setTime("2019");
        Long articlesId=saveArticles(articles,jedis);
        System.out.println("保存成功");
        Articles articles1=getArticles(articlesId,jedis);
        System.out.println(articles1);
        System.out.println("查找成功");
        articles.setTime("2017");
        Long articlesId1=updArticles(articles,jedis,articlesId);
        Articles articles2=getArticles(articlesId1,jedis);
        System.out.println(articles2);
    }
    static Articles getArticles(Long articlesId,Jedis jedis){
        Map<String,String> myblog = jedis.hgetAll("post:" + articlesId + ":data");
        Articles articles = new Articles();
        articles.setTitle(myblog.get("title"));
        articles.setContent(myblog.get("content"));
        articles.setAuthor(myblog.get("author"));
        articles.setTime(myblog.get("time"));
        return articles;
    }
    static Long saveArticles(Articles articles,Jedis jedis){
        Long articlesId = jedis.incr("posts");
        Map<String,String> blog = new HashMap<String, String>();
        blog.put("title",articles.getTitle());
        blog.put("content",articles.getContent());
        blog.put("author",articles.getAuthor());
        blog.put("time",articles.getTime());
        jedis.hmset("post:" + articlesId + ":data",blog);
        return articlesId;
    }
    static Long delArticles(Jedis jedis,Long articlesId) {
        jedis.del("post:" + articlesId + ":data");
        return articlesId;
    }
    static Long updArticles(Articles articles,Jedis jedis,Long articlesId){
        Long pid=articlesId;
        Map<String,String> blog =new HashMap<String, String>();
        blog.put("title",articles.getTitle());
        blog.put("author",articles.getAuthor());
        blog.put("content",articles.getContent());
        blog.put("time",articles.getTime());
        jedis.hmset("post:"+articlesId+":data",blog);
        return pid;
    }
}
