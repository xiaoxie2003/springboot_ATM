package com.yc.dao;

import com.yc.AppMain;
import com.yc.bean.Account;
import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AppMain.class})
@Slf4j
public class AccountDaoJdbcTemplateImplTest {

    @Autowired
    private AccountDao accountDao;


    @Test
    public void update() {
        accountDao.update(31,10);
    }

    @Test
    public void findCount() {
        int total = accountDao.findCount();
        log.info("总账户数：" + total);
        Assert.assertEquals(32,total);
    }

    @Test
    public void findAll() {
        List<Account> list = this.accountDao.findAll();
        log.info(list.toString());
    }

    @Test
    public void findById() {
        Account account = this.accountDao.findById(35);
        log.info(account.toString());
    }

    @Test
    public void insert() {
        int accountid = accountDao.insert(100);
        log.info("新开账号为：" + accountid);
        Assert.assertNotNull(accountid);
    }

    @Test
    public void delete() {
        accountDao.delete(68);
    }
}