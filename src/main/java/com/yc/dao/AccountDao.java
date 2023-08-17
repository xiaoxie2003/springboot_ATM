package com.yc.dao;

import com.yc.bean.Account;

import java.util.List;

public interface AccountDao {

    /**
     * 添加account账号
     * @param money
     * @return 新账号的编号 如何取出 auto_increment自动生成的id号
     */
    public int insert(double money);

    /**
     * 更新账号 balance=balance+money
     * @param accountid
     * @param money 正数表示 存 负数表示 取
     */
    public void update(int accountid,double money);

    /**
     * 删除账号
     * @param accountid
     */
    public void delete(int accountid);

    /**
     * 查询账户总数
     * @return
     */
    public int findCount();

    /**
     * 查询所有的账户
     * @return
     */
    public List<Account> findAll();

    /**
     * 根据id查询账号
     * @param accountid
     * @return
     */
    public Account findById(int accountid);
}
