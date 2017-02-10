package com.albert.spring.mybatis.service;

import com.albert.spring.mybatis.dao.mapper.UserMapper;
import com.albert.spring.mybatis.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserMapper userMapper;

    public User getUser(Integer id) {
        return userMapper.findUserById(id);
    }

    public Integer addUser(User user) {
        return userMapper.insertUser(user);
    }
}
