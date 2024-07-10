package org.example.hzau;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;

@SpringBootTest
class HzauApplicationTests {
    @Autowired
    RedisTemplate redisTemplate;

    @Test
    void contextLoads() {
        redisTemplate.opsForValue().set("ko", "鲁甲申");
        String name = (String) redisTemplate.opsForValue().get("ko");
                // 创建Jedis实例，连接本地Redis服务器（默认端口6379）
                Jedis jedis = new Jedis("localhost", 6379);


                // 检查key "ljs" 是否存在
                boolean keyExists = jedis.exists("ljs");

                // 输出结果
                if (keyExists) {
                    System.out.println("Key 'ljs' exists in Redis.");
                } else {
                    System.out.println("Key 'ljs' does not exist in Redis.");
                }

                // 关闭Jedis连接
                jedis.close();
            }
        }

