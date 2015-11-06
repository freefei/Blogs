package com.renfei.blog.common.utils;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-10-28
 */
public class Response<T> implements Serializable {
    private static final long serialVersionUID = -750644833749014618L;

    private boolean success; //调用是否成功

    private T result;       // 如果success = true,则通过result可以获得调用结果

    private String error;   //如果success = false,则通过error可以查看错误信息

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.success = true;
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.success = false;
        this.error = error;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("success", success)
                .add("result", result)
                .add("error", error)
                .omitNullValues()
                .toString();
    }
}
