package com.laowengs.vuedashboard.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.laowengs.vuedashboard.common.Result;
import com.laowengs.vuedashboard.redis.IRedisCall;
import com.laowengs.vuedashboard.service.MenuService;
import com.laowengs.vuedashboard.vo.MenuInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    private MenuService menuService;
    private IRedisCall redisCall;
    public static final String VUE_ALL_MENU ="VUE_ALL_MENU";
    public static final String VUE_USER_MENU_INFO ="VUE_USER_MENU_INFO";

    @Autowired
    public MenuController(MenuService menuService,IRedisCall redisCall) {
        this.menuService = menuService;
        this.redisCall = redisCall;
    }

    @RequestMapping("/getAllMenuInfo")
    public Result<List<MenuInfo>> getAllMenuInfo(){
        List<MenuInfo> data = null;
        if(!redisCall.exists(VUE_ALL_MENU)){
            data = menuService.selectAllMenuInfoBySystemId(1L, 1L);
            redisCall.setex(VUE_ALL_MENU,30 * 60, JSON.toJSONString(data));
        }else {
            String json = redisCall.get(VUE_ALL_MENU);
            data = JSON.parseArray(json, MenuInfo.class);
        }


        return Result.getInstance(0,"success", data);
    }

    @RequestMapping("/getMenuInfo")
    public Result<JSONArray> getMenuInfo(){
        List<MenuInfo> data = null;
        if(!redisCall.exists(VUE_USER_MENU_INFO)){
            data = menuService.selectMenuInfoByUserId(1L, 1L);
            redisCall.setex(VUE_USER_MENU_INFO,30 * 60, JSON.toJSONString(data));
        }
        String json = redisCall.get(VUE_USER_MENU_INFO);
        JSONArray jsonObject = JSONArray.parseArray(json);

        return Result.getInstance(0, "success", jsonObject);
    }
}

