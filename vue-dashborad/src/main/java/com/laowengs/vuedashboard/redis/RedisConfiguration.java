package com.laowengs.vuedashboard.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class RedisConfiguration {

    @Bean
    public IRedisCall redisCall() throws IOException {
        Properties properties = new Properties();
        properties.load(RedisConfiguration.class.getClassLoader().getResourceAsStream("redis.properties"));
        String ip = (String) properties.get("redis.pool.ip");
        int port = Integer.parseInt((String) properties.get("redis.pool.port"));
        String password = (String) properties.get("redis.pool.password");
        return new RedisCallImpl(ip,port,password,new JedisPoolConfig());
    }
}
