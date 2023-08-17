package com.yc.email;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Email {

    //邮件发送方
    private String sender;

    //邮件接收方
    private List<String> receiverList = new ArrayList<>();

    //邮件抄送方
    private List<String> ccList = new ArrayList<>();

    //邮件主题
    private String subject;

    //邮件内容
    private String content;

    //目前不使用
    //嵌入式资源
    private Map<String,String> inlineMap = new HashMap<>();

    /**
     * 附件列表(value:filePath)
     */
    private List<String> attachmentList = new ArrayList<>();

    public String[] getReceiverArray() {
        return this.receiverList.toArray(new String[0]);
    }

    public String[] getCcArray() {
        return this.ccList.toArray(new String[0]);
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }

    /**
     * 邮件对象主要包含：
     * 邮件发送方（sender）
     * 邮件接收方（receiverList），支持发送多人
     * 邮件抄送方（ccList），支持抄送多人
     * 邮件主题（subject）
     * 邮件内容（content），可以使用freeMark模板
     * 嵌入式资源（inlineMap），支持嵌入多个资源
     * 附件列表（attachmentList），支持多个附件发送
     */
}
