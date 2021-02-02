package com.laowengs.vuedashboard.vueadmindb.dao;

import com.laowengs.vuedashboard.vueadmindb.po.VueUserRole;

public interface VueUserRoleDao {
    int deleteByPrimaryKey(Long userRoleId);

    int insert(VueUserRole record);

    int insertSelective(VueUserRole record);

    VueUserRole selectByPrimaryKey(Long userRoleId);

    int updateByPrimaryKeySelective(VueUserRole record);

    int updateByPrimaryKey(VueUserRole record);
}