package com.laowengs.vuedashboard.controller;

import com.laowengs.vuedashboard.common.Result;
import com.laowengs.vuedashboard.vueadmindb.dao.VueRoleDao;
import com.laowengs.vuedashboard.vueadmindb.po.VueRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class RolePermissionController {

    private VueRoleDao roleDao;

    @Autowired
    public RolePermissionController(VueRoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Result<List<VueRole>> list(){
        List<VueRole> users = roleDao.selectAll();
        return Result.getInstance(0,"success", users);
    }


    @RequestMapping(value = "",method = RequestMethod.PUT)
    public Result<Integer> addUser(@RequestBody VueRole role){
        return Result.getInstance(0,"success",  roleDao.insert(role));
    }


}
