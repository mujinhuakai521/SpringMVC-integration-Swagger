package com.albert.spring.mybatis.web;

import com.albert.spring.mybatis.entity.User;
import com.albert.spring.mybatis.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAction {

    @Autowired
    UserService userService;

    Log log = LogFactory.getLog(UserAction.class);

    /**
     * 非同步插入
     *
     * @param user 用户信息
     * @return 成功则返回新用户主键id，失败返回0
     */
    @RequestMapping(value = "/add")
    public Integer addUser(User user){
        log.info("request url: /add");
        log.info("request params: " + user);
        if(userService.addUser(user) == 1){
            return user.getId();
        } else {
            return 0;
        }
    }

    /**
     * 同步插入
     *
     * @param user 用户信息
     * @return 成功则返回新用户主键id，失败返回0
     */
    @RequestMapping(value = "/addSync")
    public Integer addUserSync(User user){
        log.info("request url: /addSync");
        log.info("request params: " + user);
        if(userService.addUserSync(user) == 1){
            return user.getId();
        } else {
            return 0;
        }
    }
}
