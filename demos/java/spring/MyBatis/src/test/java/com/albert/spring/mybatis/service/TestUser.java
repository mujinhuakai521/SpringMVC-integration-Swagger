package com.albert.spring.mybatis.service;

import com.albert.spring.mybatis.entity.User;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/ApplicationContext.xml"})
public class TestUser {

    @Autowired
    UserService userService;

    @Test
    @Ignore
    public void testAdd(){
        User user = new User();
        user.setAccount("albert");
        user.setName("yy");
        Integer id = userService.addUser(user);
        System.out.println(id);
        Assert.assertNotNull(id);
    }

    @Test
    public void testGetUser(){
        User user1 = userService.getUser(1);
        System.out.println(user1);
        User user2 = userService.getUser(1);
        System.out.println(user2);
    }

}
