package com.albert.spring.mybatis.entity;

import java.io.Serializable;

/**
 * user 表实体
 */
public class User  implements Serializable {
    private Integer id;
    private String account;
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
