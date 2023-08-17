package com.yc;

import com.yc.biz.AccountBiz;
import com.yc.email.Email;
import com.yc.email.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

//@Component
@Slf4j
public class MyJob {
    @Autowired
    private AccountBiz accountBiz;

    @Autowired
    private EmailService emailService;

    //@Scheduled(cron = "0 */1 * * * ?")
    public void Myjob1(){
        // 编写要执行的任务逻辑
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedTime = formatter.format(currentTime);

        //邮件
        List<String> receiverList = new ArrayList<>();
        receiverList.add("1804113856@qq.com");
        //邮件对象
        Email email = new Email();
        //邮件发送方
        email.setSender("1804113856@qq.com");
        //邮件收件人
        email.setReceiverList(receiverList);
        //邮件主题
        email.setSubject("定时器");
        //邮件内容
        email.setContent(formattedTime + "\t" + "Hello World!");

        //设置模板数据
        Map<String,Object> data = new HashMap<>();
        data.put("content",email.getContent());

        //发送邮件
        boolean b = emailService.sendSimpleEmail(email,"index.ftl",data);
        log.info("发送结果:{}",b);

        System.out.println(formattedTime + "\t" + "Hello World!");
    }

    //@Scheduled(cron = "0 */1 * * * ?")
    public void Myjob2(){
        // 编写要执行的任务逻辑
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedTime = formatter.format(currentTime);

        //邮件
        List<String> receiverList = new ArrayList<>();
        //receiverList.add("1069595532@qq.com");
        receiverList.add("1804113856@qq.com");

        //邮件对象
        Email email = new Email();
        //邮件发送方
        email.setSender("1804113856@qq.com");
        //邮件收件人
        email.setReceiverList(receiverList);
        //邮件主题
        email.setSubject("定时器");
        //邮件内容
        email.setContent("银行的总金额为：" + accountBiz.total()+ "\t" + formattedTime  );

        //设置模板数据
        Map<String,Object> data = new HashMap<>();
        data.put("content",email.getContent());
        data.put("name","张老师:");

        //发送邮件
        boolean b = emailService.sendSimpleEmail(email,"index.ftl",data);
        log.info("发送结果:{}",b);

        System.out.println(formattedTime + "\t" + "总金额为：" + accountBiz.total());
    }
}
