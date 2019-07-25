package com.zycx.system.sys.dao;

import com.zycx.system.common.base.dao.GenericDao;

import com.zycx.system.sys.entity.BankInfo;
import com.zycx.system.sys.entity.QueryBankInfo;

import java.util.List;

/**
 *@author gly
 **/
public interface BankInfoDao extends GenericDao<BankInfo, QueryBankInfo> {
    /**
     * 加载所有网点信息
     * @return List<BankInfo>
     */
    List<BankInfo> findAllBankInfo();
    /**
     * 根据网点代码查询对应的网点信息
     * @param bankCode 网点代码
     * @return 网点信息
     */
    BankInfo getBankInfoByBankCode(String bankCode);
}