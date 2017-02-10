package com.albert.spring.mybatis.dao.mapper;

import com.albert.spring.mybatis.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    public User findUserById(Integer id);

    public Integer insertUser(User user);

    public List<User> findUserByAccount(String account);

}
