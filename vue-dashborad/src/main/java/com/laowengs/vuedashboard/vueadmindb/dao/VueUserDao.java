package com.laowengs.vuedashboard.vueadmindb.dao;

import com.laowengs.vuedashboard.vueadmindb.po.VueUser;

public interface VueUserDao {
    int deleteByPrimaryKey(Long userId);

    int insert(VueUser record);

    int insertSelective(VueUser record);

    VueUser selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(VueUser record);

    int updateByPrimaryKey(VueUser record);

    VueUser selectByUsernameAndPassword(String username,String password);
}