package com.yc.bean;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("oprecord")
public class OpRecord implements Serializable {
    @TableId(type = IdType.AUTO)
    private int id;
    private int accountid;
    private double opmoney;
    @TableField(fill = FieldFill.INSERT)
    private String optime;  //数据库是datetime 在java转为string

    private OpType optype;  //OpType枚举
    private Integer transferid;
}
