package com.laowengs.vuedashboard.controller;

import com.laowengs.vuedashboard.common.Result;
import com.laowengs.vuedashboard.vo.LoginInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/user")
public class UserLoginController {
    private Logger logger = LoggerFactory.getLogger(UserLoginController.class);
    private ConcurrentHashMap<String,Object> tokenInfo = new ConcurrentHashMap<>(1024);

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    @ResponseBody
    public Result<String> login(String username,String password){
        if(StringUtils.isBlank(username)){
            return Result.getInstance(-1,"username is null",null);
        }
        if(StringUtils.isBlank(password)){
            return Result.getInstance(-1,"password is null",null);
        }
        if("admin".equals(username) && "admin".equals(password)){
            String token = UUID.randomUUID().toString().replace("-","");
            tokenInfo.putIfAbsent(token,new Object());
            return Result.getInstance(0,"success",token);
        }
        return Result.getInstance(-1,"auth failed",null);
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Result<String> loginPost(@RequestBody LoginInfo loginInfo){
        String username = loginInfo.getUsername();
        String password = loginInfo.getPassword();
        if(StringUtils.isBlank(username)){
            return Result.getInstance(-1,"username is null",null);
        }
        if(StringUtils.isBlank(password)){
            return Result.getInstance(-1,"password is null",null);
        }
        if("admin".equals(username) && "admin".equals(password)){
            String token = UUID.randomUUID().toString().replace("-","");
            tokenInfo.putIfAbsent(token,new Object());
            return Result.getInstance(0,"success",token);
        }
        return Result.getInstance(-1,"auth failed",null);
    }


    @RequestMapping(value = "/isLogin",method = RequestMethod.GET)
    @ResponseBody
    public Result<Object> isLogin(HttpServletRequest httpServletRequest){
        String loginToken = httpServletRequest.getHeader("X-Token");
        logger.info("接受到参数loginToken："+loginToken);
        if(!StringUtils.isBlank(loginToken)){
           if(tokenInfo.containsKey(loginToken)){
               return Result.getInstance(0,"success","admin-admin");
           }
        }
        return Result.getInstance(-1,"no login or login timeout",null);
    }

}
