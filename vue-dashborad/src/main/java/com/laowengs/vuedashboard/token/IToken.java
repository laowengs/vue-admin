package com.laowengs.vuedashboard.token;

public interface IToken {

    boolean putTokenIfAbsent(String token,String tokenValue,int time);

    boolean existToken(String token);

    void reTokenTime(String token,int time);

    void delToken(String token);


}
