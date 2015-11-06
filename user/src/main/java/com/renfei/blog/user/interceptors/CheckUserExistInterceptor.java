package com.renfei.blog.user.interceptors;

import com.renfei.blog.user.UserInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * Author: songrenfei
 * Date: 15/8/14
 * Time: 下午6:34
 */
@Component
@Slf4j
public class CheckUserExistInterceptor implements UserInterceptor {
    @Override
    public boolean preHandle(Map<String, Serializable> context) throws Exception {
        if(context.get("user")!=null){

            return true;
        }
        return false;
    }

    @Override
    public void postHandle(Map<String, Serializable> context) throws Exception {
        log.error("is exist user.........");
    }
}
