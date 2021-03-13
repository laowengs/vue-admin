package com.laowengs.vuedashboard.redis;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.params.SetParams;

public class RedisCallImpl implements IRedisCall {

    private static final Logger logger = LoggerFactory.getLogger(ILoggerFactory.class);

    private JedisPool jedisPool = null;

    public RedisCallImpl(String ip, int port,String password,JedisPoolConfig config) {
        config.setMaxIdle(5);
        config.setMaxTotal(10);
        //  在获取连接的时候检查有效性, 默认false
        config.setTestOnBorrow(false);
        //  在空闲时检查有效性, 默认false
        config.setTestOnReturn(false);
        jedisPool = new JedisPool(config, ip, port,5000,password);
    }

    abstract class AbstractRedisCall<T>{
        public T execute(){
            Jedis jedis = getJedis();
            try {
                return call(jedis);
            }catch (Exception e){
                logger.error("redis调用异常",e);
            }finally {
                closeRedis(jedis);
            }
            return null;
        }
        abstract T call(Jedis jedis);
    }
    public JedisPool getPool() {
        return jedisPool;
    }


    /**
     * @Description: 获取一个jedis连接,使用完一定要调用close方法
     */
    @Override
    public Jedis getJedis() {
        return jedisPool.getResource();
    }

    /**
     * @Description: 释放jedis到jedisPool中
     */
    @Override
    public void closeRedis(Jedis jedis) {

        if (jedis != null) {
            try {
                jedis.close();
            } catch (Exception e) {
                logger.error("colse jedis failed ! " + e.getMessage(), e);
            }
        }
    }


    @Override
    public Long expire(String key, int exTime) {
        return new AbstractRedisCall<Long>(){
            @Override
            Long call(Jedis jedis) {
                return jedis.expire(key, exTime);
            }
        }.execute();
    }

    @Override
    public String setex(String key, int exTime, String value) {
        return new AbstractRedisCall<String>(){
            @Override
            String call(Jedis jedis) {
                return jedis.setex(key, exTime, value);
            }
        }.execute();
    }

    @Override
    public String set(String key, String value) {
        return new AbstractRedisCall<String>(){
            @Override
            String call(Jedis jedis) {
                return jedis.set(key, value);
            }
        }.execute();
    }

    @Override
    public String get(String key) {
        return new AbstractRedisCall<String>(){
            @Override
            String call(Jedis jedis) {
                return jedis.get(key);
            }
        }.execute();
    }

    @Override
    public Long del(String key) {
        return new AbstractRedisCall<Long>(){
            @Override
            Long call(Jedis jedis) {
                return jedis.del(key);
            }
        }.execute();
    }

    @Override
    public Long del(String... keys) {
        return new AbstractRedisCall<Long>(){
            @Override
            Long call(Jedis jedis) {
                return jedis.del(keys);
            }
        }.execute();
    }

    @Override
    public Boolean setnx(String key, String value) {
        return new AbstractRedisCall<Boolean>(){
            @Override
            Boolean call(Jedis jedis) {
                return jedis.setnx(key, value) == 1;
            }
        }.execute();
    }

    @Override
    public String setnx(String key, String value, int exTime) {


        return new AbstractRedisCall<String>(){
            @Override
            String call(Jedis jedis) {
                SetParams setParams = new SetParams();
                setParams.nx().ex(exTime);
                return jedis.set(key, value, setParams);
            }
        }.execute();
    }

    @Override
    public Boolean exists(String key) {
        return new AbstractRedisCall<Boolean>(){
            @Override
            Boolean call(Jedis jedis) {
                return jedis.exists(key);
            }
        }.execute();
    }
}