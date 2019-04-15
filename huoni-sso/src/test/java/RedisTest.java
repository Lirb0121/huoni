import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author: lirb
 * @date: 2019/4/15
 * @description:
 */
public class RedisTest {
    public static void main(String[] args) {
        ApplicationContext context=new ClassPathXmlApplicationContext("classpath:spring/upload-beans.xml");
        RedisTemplate redisTemplate = (RedisTemplate) context.getBean("redisTemplate");
        Object mykey = redisTemplate.opsForValue().get("mykey");

        System.out.println(mykey);
    }
}
