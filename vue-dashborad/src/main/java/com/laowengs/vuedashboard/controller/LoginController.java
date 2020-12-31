package com.laowengs.vuedashboard.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/vue")
public class LoginController {

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "fallback")
    public Map<String,Object> login(){
        Map<String,Object> result = new HashMap<>();
        result.put("code",0);
        result.put("msg","成功");
        result.put("data",true);
        return  result;
    }

    //zuul超时设置1000ms，这里设置为500ms，证明可以重置zuul的超时时间，可以根据不同的接口需求指定不同的超时时间
    //HystrixCommand的优先级会大于Zuul FallbackProvider接口实现的优先级
    @RequestMapping(value = "sleep/{source}",method = RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "sleepFallback", commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500")
            })
    public String sleep(@PathVariable Integer source) throws InterruptedException {
        Thread.sleep(source.longValue());
        return "sleep:"+source;
    }

    public String sleepFallback( Integer source){
        return "sleep:"+source + "sleepFallback";
    }

    public Map<String,Object> fallback(){
        Map<String,Object> result = new HashMap<>();
        result.put("code",-1);
        result.put("msg","系统繁忙");
        result.put("data",true);
        return  result;
    }
}
