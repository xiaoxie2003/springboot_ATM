package com.yc.biz;

import com.yc.bean.Account;
import com.yc.bean.OpRecord;

import java.util.List;
import java.util.Map;

public interface AccountBiz {
    /**
     * 银行开户
     * @param money
     */
    public Account openAccount(double money);

    public Account deposite(int accountid,double money,Integer transferId);

    public Account withdraw(int accountid,double money,Integer transferId);

    /**
     * 取款
     * 给accountid取出money 并返回最后的余额信息
     * @param accountid
     * @param money
     */
    public Account withdraw(int accountid,double money);

    /**
     * 转账
     *从accountid中转成money到toAccountid账户
     * @param accountid
     * @param money
     */
    public Account transfer(int accountid,double money,int toAccountId);

    /**
     * 存款
     * 给accountid存入money 并返回最后的余额信息
     * @param accountid
     * @param money
     */
    public Account deposite(int accountid,double money);

    /**
     * 查询是否存在在accountId账户
     * @param accountid
     */
    public Account findAccount(int accountid);

    /**
     * 统计总共的金额
     * @return
     */
    public Double total();

    /**
     * 查找单日流水账
     * @return
     */
    public List<OpRecord> findOpRecord(String data);
}
