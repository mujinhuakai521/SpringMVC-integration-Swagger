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
    public void testCache(){
        //删除操作会清空缓存
        userService.delUser("albert");

        //会从数据库取数据并以sql为key存放到缓存
        User user1 = userService.getUser(1);
        System.out.println(user1);
        //第二次查询会从缓存中直接获取 注意是一个新的sqlSession
        User user2 = userService.getUser(1);
        System.out.println(user2);

        //插入操作会清理缓存
        User user = new User();
        user.setAccount("albert");
        user.setName("yy");
        Integer id = userService.addUser(user);
        System.out.println(id);

        //第三次重新查询会从数据库取数据
        User user3 = userService.getUser(1);
        System.out.println(user3);

    }

}
