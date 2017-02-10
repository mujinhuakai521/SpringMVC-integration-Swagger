package com.albert.spring.mybatis.service;

import com.albert.spring.mybatis.entity.User;

public interface UserService {

    public User getUser(Integer id);

    public Integer addUser(User user);

}
