package com.laowengs.vuedashboard.vueadmindb.dao;

import com.laowengs.vuedashboard.vueadmindb.po.VueRolePermission;

import java.util.List;

public interface VueRolePermissionDao {
    int deleteByPrimaryKey(Long rolePermissionId);

    int insert(VueRolePermission record);

    int insertSelective(VueRolePermission record);

    VueRolePermission selectByPrimaryKey(Long rolePermissionId);

    int updateByPrimaryKeySelective(VueRolePermission record);

    int updateByPrimaryKey(VueRolePermission record);

    List<VueRolePermission> selectByRoleId(Long roleId);
}