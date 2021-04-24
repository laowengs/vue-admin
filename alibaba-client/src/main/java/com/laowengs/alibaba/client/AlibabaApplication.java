package com.laowengs.alibaba.client;

import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableNacosConfig(globalProperties = @NacosProperties(serverAddr = "wengjp.local:8848"))
public class AlibabaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlibabaApplication.class,args);
    }
}
