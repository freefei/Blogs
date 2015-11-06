/*
 * Copyright (c) 2014 杭州端点网络科技有限公司
 */

package com.renfei.blog.user.internal;



import com.renfei.blog.user.UserInterceptor;

import java.io.Serializable;
import java.util.Map;

/**
 * Author:  <a href="mailto:i@terminus.io">jlchen</a>
 * Date: 2014-09-10
 */
public class UserInterceptorAdaptor implements UserInterceptor {
    /**
     * @param context    参数上下文
     * @return 如果需要继续交给后面chain中的interceptor处理, 则返回true, 否则认为已经处理完毕, 返回false
     * @throws Exception 如果出现错误, 抛出异常
     */
    @Override
    public boolean preHandle( Map<String, Serializable> context) throws Exception {
        return true;
    }

    /**
     * @param context    参数上下文
     * @throws Exception 如果出现错误, 抛出异常
     */
    @Override
    public void postHandle(Map<String, Serializable> context) throws Exception {

    }
}
