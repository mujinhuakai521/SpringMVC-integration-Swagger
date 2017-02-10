package com.albert.spring.mybatis.dao.mapper;

import com.albert.spring.mybatis.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    public User findUserById(Integer id);

    public Integer insertUser(User user);

    public Integer deleteUser(String account);

}
