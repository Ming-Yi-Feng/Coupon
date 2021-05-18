package com.MingYi.coupon.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Test
    public void redisGet(){
        List newList = new ArrayList();
        newList.add("o");
        newList.add("p");
        newList.add("q");
        redisTemplate.opsForList().leftPushAll("list",newList);
    }
}
