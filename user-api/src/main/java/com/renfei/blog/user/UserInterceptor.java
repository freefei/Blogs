/*
 * Copyright (c) 2014 杭州端点网络科技有限公司
 */

package com.renfei.blog.user;



import java.io.Serializable;
import java.util.Map;

/**
 * 结算模块拦截器
 * Author:  <a href="mailto:i@terminus.io">jlchen</a>
 * Date: 2014-09-10
 */
public interface UserInterceptor {

    /**
     * @param context 参数上下文
     * @return 如果需要继续交给后面chain中的interceptor处理, 则返回true, 否则认为已经处理完毕, 返回false
     * @throws Exception 如果出现错误, 抛出异常
     */
    boolean preHandle( Map<String, Serializable> context)
            throws Exception;

    /**
     * 这个将在主订单和子订单持久化之后被处理, 通常这里做些营销活动需要的持久化等
     * @param context 参数上下文
     * @throws Exception 如果出现错误, 抛出异常
     */
    void postHandle( Map<String, Serializable> context)
            throws Exception;
}
