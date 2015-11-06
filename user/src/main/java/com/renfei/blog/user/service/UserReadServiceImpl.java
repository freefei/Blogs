package com.renfei.blog.user.service;

import com.google.common.base.Throwables;
import com.renfei.blog.common.utils.Response;
import com.renfei.blog.user.dao.mysql.UserDao;
import com.renfei.blog.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created with IntelliJ IDEA
 * Author: songrenfei
 * Date: 15/2/4
 * Time: 下午6:00
 */

@Service @Slf4j
public class UserReadServiceImpl implements UserReadService {

    @Autowired
    private UserDao userDao;

    @Override
    public Response<User> load(Long id) {

        Response<User> result = new Response<User>();
        try {
            User exit = userDao.load(id);
            checkState(exit!=null,"用户不存在");
            result.setResult(exit);

        }catch (IllegalStateException e){
            log.error("user not exist where id is {}",id);
            result.setError(e.getMessage());

        }catch (Exception e){
            log.error("find user by id {} fail,cause:{}",id, Throwables.getStackTraceAsString(e));
            result.setError("查询失败");
        }
        return result;
    }

    @Override
    public Response<List<User>> listAll() {
        Response<List<User>> result = new Response<List<User>>();
        try {
            result.setResult(userDao.listAll());
        }catch (Exception e){
            log.error("list all user fail,cause:{}",Throwables.getStackTraceAsString(e));
            result.setError("list.all.user.fail");
        }
        return result;
    }
}
