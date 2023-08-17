package com.yc.dao;

import com.yc.bean.OpRecord;

import java.util.List;

public interface OpRecoedDao {

    /**
     * 设计日志的添加接口方式
     * @param opRecord
     */
    public void insertOpRecord(OpRecord opRecord);

    /**
     * 查找日志信息 根据时间信息排序
     * @param accountid
     * @return
     */
    public List<OpRecord> findOpRecord(int accountid);

    /**
     * 查询accountid账号 opType类型的操作 根据 时间排序
     * @param accountid
     * @param opType
     * @return
     */
    public List<OpRecord> findOpRecordId(int accountid,String opType);

    /**
     * 待开发 其他特殊查询
     * @param opRecord
     * @return
     */
    public List<OpRecord> findOprecord(OpRecord opRecord);
}
