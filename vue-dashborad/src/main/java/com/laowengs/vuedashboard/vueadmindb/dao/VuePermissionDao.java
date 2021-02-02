package com.laowengs.vuedashboard.vueadmindb.dao;

import com.laowengs.vuedashboard.vueadmindb.po.VuePermission;

public interface VuePermissionDao {
    int deleteByPrimaryKey(Long permissionId);

    int insert(VuePermission record);

    int insertSelective(VuePermission record);

    VuePermission selectByPrimaryKey(Long permissionId);

    int updateByPrimaryKeySelective(VuePermission record);

    int updateByPrimaryKey(VuePermission record);
}