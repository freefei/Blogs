package com.renfei.blog.common.utils;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

import java.util.Collection;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Mail: remindxiao@gmail.com <br>
 * Date: 2014-04-08 11:34 AM  <br>
 * Author: xiao
 */
public final class Arguments {

    public static <T extends List> boolean isNullOrEmpty(T t) {return isNull(t)||isEmpty(t);}

    public static boolean isNull(Object o) {return o==null;}

    public static boolean notNull(Object o) {
        return o != null;
    }

    public static boolean isEmpty(String s) {
        return Strings.isNullOrEmpty(s);
    }

    public static  <T extends Collection> boolean isEmpty(T t) {return t.isEmpty();}

    public static boolean notEmpty(String s) {
        return !isEmpty(s);
    }

    public static <T extends Collection> boolean notEmpty(T l) {
        return notNull(l) && !l.isEmpty();
    }

    public static boolean positive(Number n) {
        return n.doubleValue() > 0;
    }

    public static boolean negative(Number n) {
        return n.doubleValue() < 0;
    }

    public static <T> boolean equalWith(T source, T target) {
        return Objects.equal(source, target);
    }

    public static boolean not(Boolean t) {
        checkArgument(notNull(t));
        return !t;
    }
}
