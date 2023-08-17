package com.yc.controller;

import com.yc.bean.Account;
import com.yc.bean.OpRecord;
import com.yc.biz.AccountBiz;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api("银行系统")
public class BankController {

    @Autowired
    private AccountBiz accountBiz;

    @PostMapping("/openAccount")
    @ApiOperation("开户操作")
    public Account openAccount(double money){
        return accountBiz.openAccount(money);
    }

    @PostMapping("/deposite")
    @ApiOperation("存款")
    public Account deposite(int accountid,double money){
        return accountBiz.deposite(accountid,money);
    }

    @PostMapping("/withdraw")
    @ApiOperation("取款")
    public Account withdraw(int accountid,double money){
        return accountBiz.withdraw(accountid,money);
    }

    @PostMapping("/transfer")
    @ApiOperation("转账")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accountid",value = "转出账户",required = true),
            @ApiImplicitParam(name = "money",value = "操作金额",required = true),
            @ApiImplicitParam(name = "toAccountId",value = "转入账户",required = true),
    })
    public Account transfer(int accountid,double money,int toAccountId){
        return accountBiz.transfer(accountid,money,toAccountId);
    }

    @PostMapping("/findAccount")
    @ApiOperation("查找账户")
    public Account findAccount(int accountid){
        return accountBiz.findAccount(accountid);
    }

    @GetMapping("/findOpRecord")
    @ApiOperation(value = "查询指定日期的日志")
    public Map findOpRecord(String date){
        Map result = new HashMap();
        try{
            List<OpRecord>list = this.accountBiz.findOpRecord(date);
            result.put("code",1);
            result.put("data",list);
        }catch (RuntimeException e){
            result.put("code",0);
            result.put("errMsg",e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

}
