/*
 * Copyright (c) 2014 杭州端点网络科技有限公司
 */

package com.renfei.blog.user.internal;


import com.renfei.blog.user.UserInterceptor;

import java.util.Collections;
import java.util.List;

/**
 * 拦截器链
 * Author:  <a href="mailto:i@terminus.io">jlchen</a>
 * Date: 2014-09-10
 */
public class UserInterceptorChain {


    private List<UserInterceptor> userInterceptors;

    /**
     * 注入拦截器列表
     * @param userInterceptors 拦截器列表
     */
    public void setUserInterceptors(List<UserInterceptor> userInterceptors){
        this.userInterceptors = userInterceptors ==null? Collections.<UserInterceptor>emptyList(): userInterceptors;
    }

    /**
     * 获取拦截器列表
     * @return  拦截器列表
     */
    public List<UserInterceptor> getUserInterceptors() {
        return userInterceptors == null?  Collections.<UserInterceptor>emptyList(): userInterceptors;
    }
}
