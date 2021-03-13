package com.laowengs.vuedashboard.redis;

import redis.clients.jedis.Jedis;

public interface IRedisCall {

    Jedis getJedis();
    void closeRedis(Jedis jedis);
    Long expire(String key,int exTime);
    String setex(String key,int exTime,String value);
    String set(String key,String value);
    String get(String key);
    Long del(String key);
    Long del(String...keys);
    Boolean setnx(String key, String value);
    String setnx(String key, String value,int exTime);
    Boolean exists(String key);
}
