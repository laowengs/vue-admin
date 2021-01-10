package com.laowengs.alibaba.client.controller;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;
import java.util.concurrent.Executor;

@RestController
@RequestMapping("config")
@NacosPropertySource(dataId = "test", groupId = "DEFAULT_GROUP" ,autoRefreshed = true)
public class ConfigController implements InitializingBean {
    private static String serverAddr = "wengjp.local:8848";
    private static String dataId = "test";
    private static String group = "DEFAULT_GROUP";

    private ConfigService configService;

    @RequestMapping("readConfigByJavaApi")
    public String readConfigByJavaApi() throws NacosException {
        return configService.getConfig(dataId, group, 5000);
    }

    @NacosValue(value = "${wengjp.name:8080}", autoRefreshed = true)
    private String wengjpName;

    @RequestMapping("readConfigBySpringBoot")
    public String readConfigBySpringBoot() throws NacosException {
        return wengjpName;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Properties properties = new Properties();
        properties.put("serverAddr", serverAddr);
        configService = NacosFactory.createConfigService(properties);
        configService.addListener(dataId, group, new Listener() {
            @Override
            public Executor getExecutor() {
                System.out.println("Listener getExecutor:");
                return null;
            }

            @Override
            public void receiveConfigInfo(String configInfo) {
                System.out.println("Listener recieve:" + configInfo);
            }
        });
    }
}
