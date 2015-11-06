/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.renfei.blog.common.exception;

/**
 * Service层公用的Exception.
 * <p/>
 * 继承自RuntimeException, 从由Spring管理事务的函数中抛出时会触发事务回滚.
 *
 * @author jlchen
 */
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 657378777056762471L;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
