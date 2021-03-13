package com.laowengs.vuedashboard.controller;

import com.laowengs.vuedashboard.common.Result;
import com.laowengs.vuedashboard.token.IToken;
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
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    private IToken tokenImpl;
    private VueUserDao userDao;
    public static final String TOKEN_PREFIX = "VUE_TOKEN:";
    public static final int TOKEN_EXPIRE_TIME = 30 * 60;

    @Autowired
    public UserController(VueUserDao userDao,IToken tokenImpl) {
        this.userDao = userDao;
        this.tokenImpl = tokenImpl;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Result<Integer> addUser(@RequestBody VueUser user){
        user.setCreateDate(new Date());
        user.setStatusDate(new Date());
        user.setStatus(0);
        return Result.getInstance(0,"success",  userDao.insert(user));
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
        tokenImpl.putTokenIfAbsent(TOKEN_PREFIX + token, "", TOKEN_EXPIRE_TIME);
        return Result.getInstance(0, "success", LoginOutput.getInstance(token));
    }


    @RequestMapping(value = "/getInfo",method = RequestMethod.GET)
    @ResponseBody
    public Result<Object> getInfo(HttpServletRequest httpServletRequest){
        String loginToken = httpServletRequest.getHeader("X-Token");
        logger.info("getInfo 接受到参数loginToken："+loginToken);
        if(!StringUtils.isBlank(loginToken)){
            if(tokenImpl.existToken(TOKEN_PREFIX + loginToken)){
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
        tokenImpl.delToken(TOKEN_PREFIX + loginToken);
        return Result.getInstance(0,"success",null);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Result<List<VueUser>> list(){
        return Result.getInstance(0,"success",userDao.selectAll());
    }
}
