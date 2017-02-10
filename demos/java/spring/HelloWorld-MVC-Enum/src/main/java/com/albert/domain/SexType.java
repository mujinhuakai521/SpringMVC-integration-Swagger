package com.albert.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SexType implements IntValueEnum {
    男(1, "男"),
    女(0, "女"),
    未知(-1, "未知");
    private int value;
    private String desc;
    SexType(int value, String desc){
        this.value = value;
        this.desc = desc;
    }
    public int getValue() {
        return value;
    }
    public String getDesc() {
        return desc;
    }
    public String getName() {
        return this.name();
    }


    public static SexType of(int value) {
        switch (value) {
            case 0: return 男;
            case 1: return 女;
            case -1: return 未知;
            default: return 未知;
        }
    }
}
