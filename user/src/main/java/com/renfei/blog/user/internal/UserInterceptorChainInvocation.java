/*
 * Copyright (c) 2014 杭州端点网络科技有限公司
 */

package com.renfei.blog.user.internal;

import com.renfei.blog.user.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Author:  <a href="mailto:i@terminus.io">jlchen</a>
 * Date: 2014-09-10
 */
@Component
@Scope("prototype")
public class UserInterceptorChainInvocation {

    @Autowired
    private UserInterceptorChain userInterceptorChain;

    private int interceptorIndex = -1;

    /**
     * 创建结算单之前要处理的逻辑
     * @param context  上下文
     * @return    是否要继续执行下一个拦截器
     * @throws Exception   如果发生错误则抛出异常
     */
    public boolean applyPreHandlers( Map<String, Serializable> context) throws Exception {
        for (int i = 0; i<getSettleInterceptors().size(); i++) {
            this.interceptorIndex = i;
            UserInterceptor interceptor = getSettleInterceptors().get(i);
            if (!interceptor.preHandle(context)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param context   上下文
     * @throws Exception   如果发生错误则抛出异常
     */
    public void applyPostHandlers( Map<String, Serializable> context) throws Exception {
        for (int i = interceptorIndex; i >= 0; i--) {
            UserInterceptor interceptor = getSettleInterceptors().get(i);
            interceptor.postHandle(context);
        }
    }

    /**
     * 处理null的情况
     * @return 拦截器列表
     */
    private List<UserInterceptor> getSettleInterceptors(){
        if(userInterceptorChain !=null){
            return userInterceptorChain.getUserInterceptors();
        }else{
            return Collections.emptyList();
        }
    }
}
