package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IEnum;

//enum 枚举
public enum OpType  implements IEnum<String> {
    WITHDRAW("withdraw",1),DEPOSITE("deposite",2),TRANSFER("transfer",3);
    //@EnumValue
    private String key;
    //@JsonValue
    private int value;
    OpType(String key,int value){
        this.key = key;
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.key;
    }

    public String getKey() {
        return key;
    }
}
