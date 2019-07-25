package com.zycx.system.sys.dao;

import com.zycx.system.common.base.dao.GenericDao;

import com.zycx.system.sys.entity.JzBaseInfo;
import com.zycx.system.sys.entity.QueryJzBaseInfo;

import java.util.List;

/**
 *@author gly
 **/
public interface JzBaseInfoDao extends GenericDao<JzBaseInfo, QueryJzBaseInfo> {
    /**
     * 查询家装分期基础数据统计（员工）
     * @param queryJzBaseInfo  查询实体类
     * @return
     */
    List<JzBaseInfo> findJzYgReportData(QueryJzBaseInfo queryJzBaseInfo);
    /**
     * 查询家装分期基础数据统计（员工）数据条数
     * @param queryJzBaseInfo
     * @return
     */
    int findJzYgReportDataCount(QueryJzBaseInfo queryJzBaseInfo);
    /**
     * 查询家装分期基础数据统计（网点）
     * @param queryJzBaseInfo  查询实体类
     * @return
     */
    List<JzBaseInfo> findJzWdReportData(QueryJzBaseInfo queryJzBaseInfo);
    /**
     * 查询家装分期基础数据统计（网点）数据条数
     * @param queryJzBaseInfo
     * @return
     */
    int findJzWdReportDataCount(QueryJzBaseInfo queryJzBaseInfo);
    /**
     * 根据申请编码查询家装分期对应的数据
     * @param applyCode 申请编码
     * @return
     */
    JzBaseInfo getJzBaseInfoByApplyCode(String applyCode);
}