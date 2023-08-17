package com.yc.biz;

import com.yc.AppMain;
import com.yc.bean.Account;
import com.yc.bean.OpRecord;
import com.yc.config.Configs;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Configs.class})
@Slf4j
public class AccountBizImplTest {

    @Autowired
    private AccountBiz accountBiz;

    @Bean
    @Test
    public void openAccount() {
        Account a = accountBiz.openAccount(100);
        Assert.assertNotNull(a);
        log.info(a.toString());
    }

    @Test
    public void deposite() {
        Account a = accountBiz.deposite(44,100);
        Assert.assertNotNull(a);
        log.info(a.toString());
    }

    @Test
    public void withdraw() {
        Account a = accountBiz.withdraw(44,10000);
        Assert.assertNotNull(a);
        log.info(a.toString());
    }

    @Test
    public void transfer() {
        Account a = accountBiz.transfer(44,100,43);
        Assert.assertNotNull(a);
        log.info(a.toString());
    }

    @Test
    public void findAccount() {
        Account a = accountBiz.findAccount(44);
        Assert.assertNotNull(a);
        log.info(a.toString());
    }

    @Test
    public void Total() {
        Double a = accountBiz.total();

    }


}