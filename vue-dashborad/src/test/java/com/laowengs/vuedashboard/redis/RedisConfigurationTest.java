package com.laowengs.vuedashboard.redis;


import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class RedisConfigurationTest {


    @Test
    void testRedisConfig() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(RedisConfiguration.class);
        IRedisCall redisCall = applicationContext.getBean(IRedisCall.class);
        redisCall.set("name", "");

    }
}