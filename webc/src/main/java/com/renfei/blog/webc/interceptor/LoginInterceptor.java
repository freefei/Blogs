package com.renfei.blog.webc.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 * Created with IntelliJ IDEA
 * Author: songrenfei
 * Date: 15/8/14
 * Time: 下午5:53
 */
@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //todo 是否登录判断
        log.info("valid is login...........");
        return true;
    }
}
