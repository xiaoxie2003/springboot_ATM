package com.yc;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AppMain.class})
@Slf4j
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void contextLoads(){
        /**
         * RedisTemplate为操作Redis的工具封装类
         * 包含 redis中各种数据类型的操作和特殊数据类型的操作，事务操作等
         * opsForValue --操作字符串，对应Redis中的String类型
         * opsForList --操作list，对应redis中List类型
         * opsForSet
         * opsForHash
         * opsForZSet
         * opsForGeo
         * opsForHyperLogLog
         *
         * 获取连接
         * RedisConnection connection = redisTamplate.getConnectionFactory（）.getConnection
         * connection.flushDb（）
         * connection.flushAll（）
         */

        redisTemplate.opsForValue().set("name","zlc");
        System.out.println(redisTemplate.opsForValue().get("name"));

    }
}
