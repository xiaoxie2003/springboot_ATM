package com.yc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@Slf4j
@EnableSwagger2
@EnableScheduling //开启定时器
@EnableAspectJAutoProxy  //表示启用 AspectJ支持
public class AppMain {
    public static void main(String[] args) {
        SpringApplication.run(AppMain.class);
    }
}