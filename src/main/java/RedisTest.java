import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;

public class RedisTest {

    public static void main(String[] args) {
        Jedis jedis=null;
        try {
            jedis = new Jedis("127.0.0.1",6379);
            jedis.get("hello");
            System.out.println("success:"+ jedis.get("hello"));
        }catch (Exception e){

            System.out.println("fail");
        }finally {
            if (jedis!=null){
                jedis.close();
            }
        }

    }

}
