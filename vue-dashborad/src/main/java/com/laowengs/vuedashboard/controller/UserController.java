package com.laowengs.vuedashboard.controller;

import com.laowengs.vuedashboard.common.Result;
import com.laowengs.vuedashboard.vueadmindb.dao.VueUserDao;
import com.laowengs.vuedashboard.vueadmindb.po.VueUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private VueUserDao userDao;

    @Autowired
    public UserController(VueUserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Result<List<VueUser>> list(){
        List<VueUser> users = userDao.selectAll();
        users.forEach((obj)->{obj.setPassword("*****");});
        return Result.getInstance(0,"success", users);
    }
}
