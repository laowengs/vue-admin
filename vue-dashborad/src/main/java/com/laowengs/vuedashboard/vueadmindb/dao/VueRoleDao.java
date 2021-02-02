package com.laowengs.vuedashboard.vueadmindb.dao;

import com.laowengs.vuedashboard.vueadmindb.po.VueRole;

public interface VueRoleDao {
    int deleteByPrimaryKey(Long roleId);

    int insert(VueRole record);

    int insertSelective(VueRole record);

    VueRole selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(VueRole record);

    int updateByPrimaryKey(VueRole record);
}