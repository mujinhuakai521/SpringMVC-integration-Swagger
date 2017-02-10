package com.albert.spring.mybatis.service;

import com.albert.spring.mybatis.dao.mapper.UserMapper;
import com.albert.spring.mybatis.entity.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 默认应当是单例
 */
@Service
public class UserServiceImpl implements UserService{

    Lock lock = new ReentrantLock();

    Log log = LogFactory.getLog(UserServiceImpl.class);

    @Autowired
    UserMapper userMapper;

    public User getUser(Integer id) {
        return userMapper.findUserById(id);
    }

    public User getUser(String account) {

        List<User> userList = userMapper.findUserByAccount(account);
        if(userList != null && userList.size() >= 1)
            return userList.get(0);
        else
            return null;
    }

    public Integer addUser(User user) {
        User old = this.getUser(user.getAccount());
        if(old == null)
            return userMapper.insertUser(user);
        else return 0;
    }

    public Integer addUserSync(User user) {
        lock.lock();
        log.info("get lock: " + lock.hashCode());
        try {
            User old = this.getUser(user.getAccount());
            if(old == null)
                return userMapper.insertUser(user);
            else return 0;
        } finally {
            log.info("release lock: " + lock.hashCode());
            lock.unlock();
        }
    }
}
