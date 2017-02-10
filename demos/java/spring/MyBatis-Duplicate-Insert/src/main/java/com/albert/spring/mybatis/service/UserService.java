package com.albert.spring.mybatis.service;

import com.albert.spring.mybatis.entity.User;

/**
 * 用户道增删查功能
 *
 * @author Albert Chen
 * @since JDK 1.7
 * @version 1.0
 */
public interface UserService {

    /**
     * 根据用户主键获取用户
     *
     * @param id 用户主键
     * @return 用户信息实体，未找到则为null
     */
    public User getUser(Integer id);

    /**
     * 根据用户账号获取用户
     * 如果出现错误，如同账号有多个则随即只取第一条
     *
     * @param account 用户账号，应当唯一
     * @return 用户信息实体，未找到则为null
     */
    public User getUser(String account);

    /**
     * 新增用户，同账号添加会失败，未加锁同步
     *
     * @param user 新用户信息实体 保存成功后，用户主键可通过user.getId()获得
     * @return 成功返回1，添加失败返回0
     */
    public Integer addUser(User user);

    /**
     * 新增用户，同账号添加会失败，加锁同步
     *
     * @param user 新用户信息实体 保存成功后，用户主键可通过user.getId()获得
     * @return 成功返回1，添加失败返回0
     */
    public Integer addUserSync(User user);

}
