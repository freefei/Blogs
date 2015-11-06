/*
 * Copyright (c) 2014 杭州端点网络科技有限公司
 */

package com.renfei.blog.web.servlet;


import com.renfei.blog.common.exception.JsonResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Mail: xiao@terminus.io <br>
 * Date: 2014-11-26 3:05 PM  <br>
 * Author: xiao
 */
@Slf4j
public class BlogDispatcherServlet extends DispatcherServlet {
    private static final long serialVersionUID = 1048939492181326716L;

    @Override
    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            super.doService(request, response);
        } catch (JsonResponseException e) {
            log.warn("catch 500 exception:{}", e.getMessage());
        }catch (Exception e){

        }
        /*catch (NotFound404Exception e) {
            response.setStatus(404);
        } catch (UnAuthorize401Exception e) {
            response.setStatus(401);
        }*/
    }
}
