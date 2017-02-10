package com.albert.domain;

/**
 * @Description: 通用枚举接口，用于获取名称
 */
public interface NameEnum {
    String getName(); /** 每个枚举值的名称, 一般直接使用 this.name() **/
    String getDesc(); /** 每个枚举值的描述 **/
}
