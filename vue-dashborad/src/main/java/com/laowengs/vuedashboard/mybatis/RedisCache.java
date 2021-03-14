package com.laowengs.vuedashboard.mybatis;

import com.laowengs.vuedashboard.redis.IRedisCall;
import com.laowengs.vuedashboard.redis.RedisCallImpl;
import com.laowengs.vuedashboard.redis.RedisConfiguration;
import com.laowengs.vuedashboard.redis.SerializeUtil;
import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.Properties;


public class RedisCache implements Cache {
    private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);

    private String id;
    private IRedisCall redisCall;
    public static final String DB_CACHE_PREFIX = "db_cache:";


    ////必须有该构造函数
    public RedisCache(String id) throws IOException, ClassNotFoundException {
        logger.debug(" constructor mybatis custom RedisCache id {}",id);
        this.id = id;
        redisCall  = redisCall();
    }
    public IRedisCall redisCall() throws IOException {
        Properties properties = new Properties();
        properties.load(RedisConfiguration.class.getClassLoader().getResourceAsStream("redis.properties"));
        String ip = (String) properties.get("redis.pool.ip");
        int port = Integer.parseInt((String) properties.get("redis.pool.port"));
        String password = (String) properties.get("redis.pool.password");
        return new RedisCallImpl(ip,port,password,new JedisPoolConfig());
    }
    // // 获取缓存编号
    @Override
    public String getId() {
        return id;
    }

    // 保存key值缓存对象
    @Override
    public void putObject(Object o, Object o1) {
        String set = redisCall.set(DB_CACHE_PREFIX+o.toString(), o1.toString());
        logger.debug("redis cache set key {} value {} result {}",DB_CACHE_PREFIX+o, SerializeUtil.serialize(o1),set);
    }

    //通过KEY 获取对象
    @Override
    public Object getObject(Object o) {
        String result = redisCall.get(DB_CACHE_PREFIX + o);
        logger.debug("redis cache get key {} value {} ", DB_CACHE_PREFIX + o, result);
        if(result == null){
            return null;
        }
        return SerializeUtil.unserialize(result);
    }

    // 通过key删除缓存对象
    @Override
    public Object removeObject(Object o) {
        Long result = redisCall.del(DB_CACHE_PREFIX + o);
        logger.debug("redis cache del key {} result {} ",DB_CACHE_PREFIX+o,result);
        return null;
    }

    @Override
    public void clear() {
        logger.debug("redis cache clear");
    }

    @Override
    public int getSize() {
        return 0;
    }
}
