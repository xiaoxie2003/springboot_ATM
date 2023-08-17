package com.yc.config;

import com.yc.biz.AccountBiz;
import com.yc.email.Email;
import com.yc.email.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.*;

//@Configuration
@Slf4j
public class QuartzConfig {

    @Autowired
    private AccountBiz accountBiz;

    @Autowired
    private EmailService emailService;


    // 任务实现类
    public class Task1 extends QuartzJobBean {
        @Override
        protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
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
            // 任务执行完成后，关闭Scheduler调度器
//            Scheduler scheduler = context.getScheduler();
//            try {
//                scheduler.shutdown();
//                System.out.println("--------scheduler shutdown ! ------------");
//
//            } catch (SchedulerException e) {
//                e.printStackTrace();
//            }
        }
    }

    // 任务明细
    @Bean
    public JobDetail jobDetail(){
        return JobBuilder.newJob(Task1.class).storeDurably().build();
    }

    // 绑定任务触发器
    @Bean
    public Trigger printJobTrigger(@Autowired JobDetail jobDetail){
        // cron表达式 0 */1 * * * ? 每分钟执行一次
        return TriggerBuilder.newTrigger().forJob(jobDetail)
                .withSchedule(CronScheduleBuilder.cronSchedule("0 */1 * * * ?")).build();
//                .withSchedule(CronScheduleBuilder.cronSchedule("01 50 11 15 8 ? 2023")).build();
    }



    // 任务实现类
    public class Task2 extends QuartzJobBean {
        @Override
        protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
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
            email.setContent(formattedTime + "\t" + "总金额为：" + accountBiz.total());
            //设置模板数据
            Map<String,Object> data = new HashMap<>();
            data.put("content",email.getContent());

            //发送邮件
            boolean b = emailService.sendSimpleEmail(email,"index.ftl",data);
            log.info("发送结果:{}",b);

            System.out.println(formattedTime + "\t" + "总金额为：" + accountBiz.total());

            // 任务执行完成后，关闭Scheduler调度器
//            Scheduler scheduler = context.getScheduler();
//            try {
//                scheduler.shutdown();
//                System.out.println("--------scheduler shutdown ! ------------");
//
//            } catch (SchedulerException e) {
//                e.printStackTrace();
//            }
        }
    }


    // 任务明细
    @Bean
    public JobDetail jobDetail2(){
        return JobBuilder.newJob(Task2.class).storeDurably().build();
    }

    // 绑定任务触发器
    @Bean
    public Trigger printJobTrigger2(@Autowired JobDetail jobDetail2){
        // cron表达式 0 */1 * * * ? 每分钟执行一次
        return TriggerBuilder.newTrigger().forJob(jobDetail2)
                .withSchedule(CronScheduleBuilder.cronSchedule("0 */2 * * * ?")).build();
//                .withSchedule(CronScheduleBuilder.cronSchedule("01 50 11 15 8 ? 2023")).build();
    }
}
