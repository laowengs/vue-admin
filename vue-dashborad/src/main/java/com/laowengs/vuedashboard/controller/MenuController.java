package com.laowengs.vuedashboard.controller;

import com.laowengs.vuedashboard.common.Result;
import com.laowengs.vuedashboard.service.MenuService;
import com.laowengs.vuedashboard.vo.MenuInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    private MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @RequestMapping("/getAllMenuInfo")
    public Result<List<MenuInfo>> getAllMenuInfo(){
        return Result.getInstance(0,"success",menuService.selectAllMenuInfoBySystemId(1L));
    }

    @RequestMapping("/getMenuInfo")
    public Result<List<MenuInfo>> getMenuInfo(){
        return Result.getInstance(0, "success", menuService.selectMenuInfoByUserId(1L, 1L));
    }
}

