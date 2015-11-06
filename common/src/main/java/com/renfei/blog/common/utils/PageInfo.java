package com.renfei.blog.common.utils;

import com.google.common.base.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Date: 14-3-3
 * Time: PM4:18
 * Author: 2014年 <a href="mailto:dong.worker@gmail.com">张成栋</a>
 */
@ToString
@NoArgsConstructor
public class PageInfo {

    @Getter
    public Integer offset;

    @Getter
    public Integer limit;

    public PageInfo(Integer pageNo, Integer size) {
        pageNo = Objects.firstNonNull(pageNo, 1);
        size = Objects.firstNonNull(size, 20);
        limit = size > 0 ? size : 20;
        offset = (pageNo - 1) * size;
        offset = offset > 0 ? offset : 0;
    }
}
