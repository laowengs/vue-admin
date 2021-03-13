package com.laowengs.vuedashboard.token;

import com.laowengs.vuedashboard.redis.IRedisCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisTokenImpl implements IToken{

    private IRedisCall redisCall;

    @Autowired
    public RedisTokenImpl(IRedisCall redisCall) {
        this.redisCall = redisCall;
    }

    @Override
    public boolean putTokenIfAbsent(String token, String tokenValue,int time) {
        String s = redisCall.setnx(token, tokenValue, time);
        return true;
    }

    @Override
    public boolean existToken(String token) {
        return redisCall.exists(token);
    }

    @Override
    public void reTokenTime(String token,int time) {
        redisCall.expire(token,time);
    }

    @Override
    public void delToken(String token) {
        redisCall.del(token);
    }
}
