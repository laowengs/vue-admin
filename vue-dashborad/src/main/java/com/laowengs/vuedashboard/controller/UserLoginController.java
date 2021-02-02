package com.laowengs.vuedashboard.controller;

import com.laowengs.vuedashboard.common.Result;
import com.laowengs.vuedashboard.vo.LoginInput;
import com.laowengs.vuedashboard.vo.LoginOutput;
import com.laowengs.vuedashboard.vueadmindb.dao.VueUserDao;
import com.laowengs.vuedashboard.vueadmindb.po.VueUser;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private VueUserDao userDao;

    @Autowired
    public UserLoginController(VueUserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Result<LoginOutput> loginPost(@RequestBody LoginInput loginInput){
        String username = loginInput.getUsername();
        String password = loginInput.getPassword();
        if(StringUtils.isBlank(username)){
            return Result.getInstance(-1,"username is null",null);
        }
        if(StringUtils.isBlank(password)){
            return Result.getInstance(-1,"password is null",null);
        }
        VueUser vueUser = userDao.selectByUsernameAndPassword(username, password);
        if(vueUser == null){
            return Result.getInstance(-1,"auth failed,password error",null);
        }
        if(vueUser.getStatus() != 0){
            return Result.getInstance(-1,"auth failed,user status error",null);
        }
        String token = UUID.randomUUID().toString().replace("-", "");
        tokenInfo.putIfAbsent(token, new Object());
        return Result.getInstance(0, "success", LoginOutput.getInstance(token));
    }


    @RequestMapping(value = "/getInfo",method = RequestMethod.GET)
    @ResponseBody
    public Result<Object> getInfo(HttpServletRequest httpServletRequest){
        String loginToken = httpServletRequest.getHeader("X-Token");
        logger.info("getInfo 接受到参数loginToken："+loginToken);
        if(!StringUtils.isBlank(loginToken)){
           if(tokenInfo.containsKey(loginToken)){
               return Result.getInstance(0,"success","admin-admin");
           }
        }
        return Result.getInstance(0,"no login or login timeout",null);
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    @ResponseBody
    public Result<Object> logout(HttpServletRequest httpServletRequest){
        String loginToken = httpServletRequest.getHeader("X-Token");
        logger.info("logout 接受到参数loginToken："+loginToken);
        tokenInfo.remove(loginToken);
        return Result.getInstance(0,"success",null);
    }

}
