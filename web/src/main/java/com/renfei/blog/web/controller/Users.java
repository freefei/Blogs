package com.renfei.blog.web.controller;

import com.google.common.collect.Maps;
import com.renfei.blog.common.exception.JsonResponseException;
import com.renfei.blog.common.utils.Response;
import com.renfei.blog.user.internal.UserInterceptorChainInvocation;
import com.renfei.blog.user.model.User;
import com.renfei.blog.user.service.UserReadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * Author: songrenfei
 * Date: 15/2/9
 * Time: 下午12:55
 */
@RequestMapping("/api/user")
@Slf4j
@Controller
public class Users {

    @Autowired
    private UserReadService userReadService;

    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public User loadById(@PathVariable Long id) {

        Response<User> userResponse = userReadService.load(id);
        if(!userResponse.isSuccess()){
            throw new JsonResponseException(userResponse.getError());
        }
        return userResponse.getResult();
    }


    @RequestMapping(value = "listAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String listAll(HttpServletRequest request) {

        Response<List<User>> usersResponse = userReadService.listAll();
        if(!usersResponse.isSuccess()){
            throw new JsonResponseException(usersResponse.getError());
        }
        request.setAttribute("users",usersResponse.getResult());

        return "users";
    }

    @RequestMapping(value = "test_interceptor", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void testInterceptor(HttpServletRequest request) {

        Map<String, Serializable> context = Maps.newHashMap();
        context.put("user",123);
        //拦截器链入口
        UserInterceptorChainInvocation sci = applicationContext.getBean(UserInterceptorChainInvocation.class);
        Boolean success = null;
        try {
            success = sci.applyPreHandlers(context);
            if(success){
                sci.applyPostHandlers(context);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
