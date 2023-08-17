package com.yc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

import javax.sql.DataSource;

@SpringBootTest
@ActiveProfiles("init") //表示启用 application-init配置文件
public class TestInit {

    @Autowired
    private DataSource ds;

    @Test
    public void testDataSource(){
        Assert.notNull(ds);
        System.out.println(ds);
    }

}
