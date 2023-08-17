package com.yc.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yc.bean.Account;
import com.yc.bean.OpRecord;
import com.yc.bean.OpType;
import com.yc.dao.AccountDao;
import com.yc.dao.OpRecoedDao;
import com.yc.mappers.AccountMapper;
import com.yc.mappers.OpRecoedMapper;
import com.yc.redis.RedisUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service//spring托管
@Log4j2//日志
@Transactional//事务
@Primary
public class AccountBizImpl implements AccountBiz {

//    @Autowired
//    private AccountDao accountDao; //账单对象
//    @Autowired
//    private OpRecoedDao opRecoedDao; //日志对象

    @Autowired
    private AccountMapper accountDao;
    @Autowired
    private OpRecoedMapper opRecoedDao;

    @Autowired
    private RedisTemplate redisTemplate;

    //spring对异常处理
    //正常的程序异常

    //开户操作 返回新账号的id
    @Override
    public Account openAccount(double money) {
        //int accountid = this.accountDao.insert(money);
        Account newAccount = new Account();
        newAccount.setMoney(money); //面向对象（Account中@TableId(type = IdType.AUTO)）
        this.accountDao.insert(newAccount);
        //包装日志信息
        OpRecord opRecord = new OpRecord();
        opRecord.setAccountid(newAccount.getAccountid()); //取出Accountid
        opRecord.setOpmoney(money);
        opRecord.setOptype(OpType.DEPOSITE);
        //this.opRecoedDao.insertOpRecord(opRecord);
        this.opRecoedDao.insert(opRecord);
//        //返回新的账户信息
//        Account a = new Account();
//        a.setAccountid(accountid);
//        a.setMoney(money);
        return newAccount;
    }

    @Override
    public Account deposite(int accountid, double money, Integer transferId) {
        Account a = null;
        try {
            //a = this.accountDao.findById(accountid);
            a = this.accountDao.selectById(accountid);
        } catch (Exception e) {
            log.error(e.getMessage());//TODO:封装保存日志的操作
            throw new RuntimeException("查无此账户：" + accountid + "无法完成存款操作");
        }
        //存款时 金额累加
        a.setMoney(a.getMoney() + money);

        //this.accountDao.update(accountid, a.getMoney());
        this.accountDao.updateById(a); //根据Id来更新

        OpRecord opRecord = new OpRecord();
        opRecord.setAccountid(accountid);
        opRecord.setOpmoney(money);
        if (transferId != null) {
            //表示为转账
            opRecord.setOptype(OpType.TRANSFER);
            opRecord.setTransferid(transferId);
        } else {
            //表示单纯的存款
            opRecord.setOptype(OpType.DEPOSITE);
        }
        //this.opRecoedDao.insertOpRecord(opRecord);
        this.opRecoedDao.insert(opRecord);
        return a;
    }

    @Override
    public Account withdraw(int accountid, double money, Integer transferId) {
        Account a = null;
        try {
            //a = this.accountDao.findById(accountid);
            a = this.accountDao.selectById(accountid);
        } catch (Exception e) {
            log.error(e.getMessage());//TODO:封装保存日志的操作
            throw new RuntimeException("查无此账户：" + accountid + "无法完成取款操作");
        }
        //取款 减去金额 钱不够的话就报异常 不能取出
        a.setMoney(a.getMoney() - money);
        OpRecord opRecord = new OpRecord();
        opRecord.setAccountid(accountid);
        opRecord.setOpmoney(money);
        if (transferId != null) {
            opRecord.setOptype(OpType.TRANSFER);
            opRecord.setTransferid(transferId);
        } else {
            opRecord.setOptype(OpType.WITHDRAW);
        }
        //dao -> datasource -> connection -> jdbc 隐式事务提交 -> 一条sql提交一次 commit
        //this.opRecoedDao.insertOpRecord(opRecord); //先插入日志
        this.opRecoedDao.insert(opRecord);//先插入日志

        //this.accountDao.update(accountid, a.getMoney()); //再减金额
        this.accountDao.updateById(a);
        return a;
    }

    @Override
    public Account withdraw(int accountid, double money) {
        return this.withdraw(accountid, money, null);
    }

    @Override
    public Account transfer(int accountid, double money, int toAccountId) {
        //从accountid转money到toAccountId
        this.deposite(toAccountId, money, accountid); // 收款方
        //从account取money
        Account a = this.withdraw(accountid, money, toAccountId);
        return a;
    }

    @Override
    public Account deposite(int accountid, double money) {
        return this.deposite(accountid, money, null);
    }

    @Override
    public Account findAccount(int accountid) {
        return this.accountDao.selectById(accountid);
    }

    @Override
    public Double total() {
        QueryWrapper<Account> wrapper = new QueryWrapper<>();
        wrapper.select("sum(balance) sum_balance");
        List<Map<String, Object>> maps = accountDao.selectMaps(wrapper);
        if(maps != null && maps.size() > 0){
            Map<String,Object> map = maps.get(0);
            if(map.containsKey("sum_balance")){
                return Double.parseDouble(map.get("sum_balance").toString());
            }
        }
        //maps.forEach(System.out::println);
        return 0.0;
    }

    /**
     * 查找当天的日志
     * @return
     */
    @Override
    public List<OpRecord> findOpRecord(String date) {
        List<OpRecord> list = new ArrayList<>();
        //验证data的格式是否正确
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try{
            d=df.parse(date); //利用df的格式yyyy-MM-dd去解析 data字符串 如果能解析说明date格式正确
        }catch (Exception e){
            e.printStackTrace();
            log.error("待查询的日志格式不正确，原格式：" + date + ",要求的格式为：yyyy-MM-dd");
            throw new RuntimeException(e);
        }
        //TODO:先查缓存，缓存有则取缓存中的
        //日期: List<OpRecord> 存到redis中的OpRecord是json字符串 =》 序列化 =》 OpRecord实现java.io.Serializable接口
        if(redisTemplate.hasKey(date)){
            //如果缓存中有
            list = redisTemplate.opsForList().range(date,0,redisTemplate.opsForList().size(date));
            log.info("从缓存中的键：" + date + ",取出的值list为：" + list);
            return list;
        }
        ///缓存中没有 查数据库中的 再加入到缓存
        QueryWrapper wrapper = new QueryWrapper();
        //计算后一天
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.DATE,1);
        Date nextDate = cal.getTime();
        String nextDateString = df.format(nextDate);
        wrapper.between("optime",date,nextDateString);
        list = opRecoedDao.selectList(wrapper);
        if(list!=null && list.size()>0){
            //村存入缓存
            redisTemplate.delete(date);  //先清空原来的
            redisTemplate.opsForList().leftPush(date,list);
            redisTemplate.expire(date,15, TimeUnit.DAYS); //15天过期时间
        }
        return list;
    }


}
