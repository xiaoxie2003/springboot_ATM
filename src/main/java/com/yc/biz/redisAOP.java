package com.yc.biz;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Aspect
@Slf4j
public class redisAOP {

    @Autowired
    private RedisTemplate redisTemplate;

    @Pointcut("execution(* com.yc.biz.AccountBizImpl.openAccount(..))")
    public void a(){}
    @Pointcut("execution(* com.yc.biz.AccountBizImpl.deposite(..))")
    public void b(){}
    @Pointcut("execution(* com.yc.biz.AccountBizImpl.withdraw(..))")
    public void c(){}
    @Pointcut("execution(* com.yc.biz.AccountBizImpl.transfer(..))")
    public void d(){}

    @AfterReturning("a()||b()||c()||d()")
    public void around(JoinPoint jp){
        log.info("当前执行的是：" + jp.getSignature() + "，清理缓存");
        Date d = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-M-d");
        System.out.println(df.format(d));
        redisTemplate.delete(df.format(d));

    }
}
