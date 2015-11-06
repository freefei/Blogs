package com.renfei.blog.user.dao.mysql;


import com.renfei.blog.user.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDaoTest extends BaseDaoTest {

    @Autowired
    private UserDao userDao;

    private User user;

    @Before
    public void init(){
        mock();

    }

    @Test
    public void testCreate(){

        Assert.assertTrue(userDao.create(user));
    }


    private void mock(){
        user = new User();
        user.setNickname("test");
        user.setPasswd("22222");
        user.setMobile("1351111111");
        user.setEmail("x@terminus.io");
        user.setType(User.TYPE.ADMIN.toNumber());
        user.setStatus(User.Status.FROZEN.value());
    }
}