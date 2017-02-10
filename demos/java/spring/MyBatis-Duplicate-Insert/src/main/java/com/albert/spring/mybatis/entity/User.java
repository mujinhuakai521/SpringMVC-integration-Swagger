package com.albert.spring.mybatis.entity;

import java.io.Serializable;

/**
 * user 表实体
 * @author Albert Chen
 * @since JDK 1.7
 * @version 1.0
 */
public class User  implements Serializable {
    //用户主键
    private Integer id;
    //用户账号 长度 10
    private String account;
    //用户姓名 长度 10
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "user: { " +
                "id : " + id + ", " +
                "account : " + account + ", "  +
                "name : " + name +
                "}";
    }
}
