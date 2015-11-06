package com.renfei.blog.user.service;

import com.renfei.blog.common.utils.Response;
import com.renfei.blog.user.model.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Author: songrenfei
 * Date: 15/2/4
 * Time: 下午5:32
 */
public interface UserReadService {

    /**
     * 根据主键查询user对象
     * @param id 主键id
     * @return user对象
     */
    public Response<User> load(Long id);

    /**
     * 根据主键查询user对象
     * @return user对象
     */
    public Response<List<User>> listAll();
}
