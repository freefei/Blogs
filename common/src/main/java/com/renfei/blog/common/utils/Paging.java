/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.renfei.blog.common.utils;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Desc:
 * Author: dimzfw@gmail.com
 * Date: 8/15/12 9:01 PM
 */
public class Paging<T> implements Serializable {

    private static final long serialVersionUID = 755183539178076901L;

    private Long total;

    private List<T> data;

    public Paging() {
    }

    public Paging(Long total, List<T> data) {
        this.data = data;
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }


    @SuppressWarnings("all")
    public static <T> Paging<T> empty(Class<T> clazz) {
        List<T> emptyList = Collections.emptyList();
        return new Paging<T>(0L, emptyList);
    }

}
