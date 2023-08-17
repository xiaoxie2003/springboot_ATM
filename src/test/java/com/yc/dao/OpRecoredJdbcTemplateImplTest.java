package com.yc.dao;

import com.yc.AppMain;
import com.yc.bean.OpRecord;
import com.yc.bean.OpType;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AppMain.class})
@Slf4j
public class OpRecoredJdbcTemplateImplTest {

    @Autowired
    private OpRecoedDao opRecoedDao;

    @Test
    public void insertOpRecord() {
        OpRecord opRecord = new OpRecord();
        opRecord.setAccountid(56);
        opRecord.setOpmoney(500);
        opRecord.setOptype(OpType.DEPOSITE);
        this.opRecoedDao.insertOpRecord(opRecord);
    }

    @Test
    public void findOpRecord() {
        List<OpRecord>list = this.opRecoedDao.findOpRecord(35);
        System.out.println(list);
    }

    @Test
    public void findOpRecordId() {
        List<OpRecord>list = this.opRecoedDao.findOpRecordId(34,"DEPOSITE");
        System.out.println(list);
    }

    @Test
    public void findOprecord() {
    }
}