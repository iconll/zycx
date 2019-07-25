package com.zycx.system.sys.dao;

import com.zycx.system.common.base.dao.GenericDao;

import com.zycx.system.sys.entity.FqtBaseInfo;
import com.zycx.system.sys.entity.QueryFqtBaseInfo;

import java.util.List;

/**
 *@author gly
 **/
public interface FqtBaseInfoDao extends GenericDao<FqtBaseInfo, QueryFqtBaseInfo> {
    /**
     * 分期通基础数据统计报表-员工
     * @param queryFqtBaseInfo 查询实体类
     * @return 处理计算后的结果
     */
    List<FqtBaseInfo> findYgReportData(QueryFqtBaseInfo queryFqtBaseInfo);
    /**
     * 分期通基础数据统计报表-网点
     * @param queryFqtBaseInfo 查询实体类
     * @return 总数据条数
     */
    int findYgReportDataCount(QueryFqtBaseInfo queryFqtBaseInfo);
    /**
     * 分期通基础数据统计报表-网点
     * @param queryFqtBaseInfo 查询实体类
     * @return 处理计算后的结果
     */
    List<FqtBaseInfo> findWdReportData(QueryFqtBaseInfo queryFqtBaseInfo);

    /**
     * 分期通基础数据统计报表-网点
     * @param queryFqtBaseInfo 查询实体类
     * @return 总数据条数
     */
    int findWdReportDataCount(QueryFqtBaseInfo queryFqtBaseInfo);
    /**
     * 根据分期通基础数据id和支用月份获取当月支用金额
     * @param queryFqtBaseInfo
     * @return 当月支用金额
     */
    String findDyzyMoneyByBaseIdAndMonth(QueryFqtBaseInfo queryFqtBaseInfo);

    /**
     * 根据申请编码（F码）查询对应的信息
     * @param applyCode 申请编码
     * @return
     */
    FqtBaseInfo getFqtBaseInfoByApplyCode(String applyCode);
    @Override
    int count(QueryFqtBaseInfo queryFqtBaseInfo);
}