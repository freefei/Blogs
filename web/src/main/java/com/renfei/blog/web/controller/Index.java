package com.renfei.blog.web.controller;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * Author: songrenfei
 * Date: 15/2/9
 * Time: 下午12:55
 */
@Controller
@Slf4j
public class Index {

    @RequestMapping
    public String doRequest(HttpServletRequest request,
                                      HttpServletResponse response,
                                      Map<String, Object> context) {

        String path = request.getRequestURI().substring(request.getContextPath().length() + 1);
        if(Strings.isNullOrEmpty(path)) {
            path = "index";
        }

        return path;
    }
}
