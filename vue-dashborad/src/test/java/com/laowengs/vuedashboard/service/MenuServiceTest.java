package com.laowengs.vuedashboard.service;

import com.alibaba.fastjson.JSONObject;
import com.laowengs.vuedashboard.DataSourceConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


class MenuServiceTest {
    AnnotationConfigApplicationContext applicationContext;
    MenuService menuService;
    @BeforeEach
    void setUp() {
        applicationContext = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        menuService = applicationContext.getBean(MenuService.class);
    }

    @Test
    void selectAllMenuInfoBySystemId() {
        System.out.println(JSONObject.toJSONString(menuService.selectAllMenuInfoBySystemId(1L,0L)));
    }
}