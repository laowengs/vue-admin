package com.laowengs.vuedashboard.vueadmindb.dao;

import com.laowengs.vuedashboard.vueadmindb.po.VueRolePermission;

public interface VueRolePermissionDao {
    int deleteByPrimaryKey(Long rolePermissionId);

    int insert(VueRolePermission record);

    int insertSelective(VueRolePermission record);

    VueRolePermission selectByPrimaryKey(Long rolePermissionId);

    int updateByPrimaryKeySelective(VueRolePermission record);

    int updateByPrimaryKey(VueRolePermission record);
}