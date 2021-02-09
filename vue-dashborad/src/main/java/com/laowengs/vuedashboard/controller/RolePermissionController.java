package com.laowengs.vuedashboard.controller;

import com.laowengs.vuedashboard.common.Result;
import com.laowengs.vuedashboard.vueadmindb.dao.VueRolePermissionDao;
import com.laowengs.vuedashboard.vueadmindb.po.VueRolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role/permission")
public class RolePermissionController {

    private VueRolePermissionDao rolePermissionDao;

    @Autowired
    public RolePermissionController(VueRolePermissionDao rolePermissionDao) {
        this.rolePermissionDao = rolePermissionDao;
    }

    @RequestMapping(value = "",method = RequestMethod.PUT)
    public Result<Integer> addRolePermission(@RequestBody VueRolePermission vueRolePermission){
        return Result.getInstance(0,"success",  rolePermissionDao.insert(vueRolePermission));
    }


    @RequestMapping(value = "",method = RequestMethod.DELETE)
    public Result<Integer> deleteRolePermission(@RequestBody Long rolePermissionId){
        return Result.getInstance(0,"success",  rolePermissionDao.deleteByPrimaryKey(rolePermissionId));
    }

}
