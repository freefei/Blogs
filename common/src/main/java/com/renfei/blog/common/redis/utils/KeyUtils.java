package com.renfei.blog.common.redis.utils;

import com.google.common.base.CaseFormat;
import com.google.common.base.Strings;

import static com.google.common.base.Preconditions.checkArgument;

/*
* Author: jlchen
* Date: 2012-11-14
*/
public abstract class KeyUtils {
    /**
     * *************  common ********************************
     */
    public static <T> String entityCount(Class<T> entityClass) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, entityClass.getSimpleName()) + ":count";
    }

    public static <T> String entityId(Class<T> entityClass, long id) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, entityClass.getSimpleName()) + ":" + id;
    }

    public static <T> String entityId(Class<T> entityClass, final String id) {
        checkArgument(!Strings.isNullOrEmpty(id), "id can not be null");
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, entityClass.getSimpleName()) + ":" + id;
    }

    //得到不用类型用户的id集合
    public static String getIdsByType(final Integer type) {
        return "user-type:" + type +":ids";
    }

}
