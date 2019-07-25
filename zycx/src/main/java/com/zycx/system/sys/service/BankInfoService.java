package com.zycx.system.sys.service;

import com.zycx.system.sys.entity.BankInfo;
import com.zycx.system.sys.entity.QueryBankInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import com.zycx.system.common.base.service.GenericService;
import com.zycx.system.common.base.dao.GenericDao;

import com.zycx.system.sys.dao.BankInfoDao;

import java.util.List;

/**
 *@author gly
 **/
@Service("bankInfoService")
@Transactional(rollbackFor={IllegalArgumentException.class})
public class BankInfoService extends GenericService<BankInfo, QueryBankInfo> {
	@Autowired
	@SuppressWarnings("SpringJavaAutowiringInspection")
	private BankInfoDao bankInfoDao;
	@Override
	protected GenericDao<BankInfo, QueryBankInfo> getDao() {
		return bankInfoDao;
	}

	/**
	 * 获取所有网点信息
	 * @return
	 */
	public List<BankInfo> findAllBankInfo(){

		return bankInfoDao.findAllBankInfo();
	}
}