package com.zycx.system.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import com.zycx.system.common.base.service.GenericService;
import com.zycx.system.common.base.dao.GenericDao;

import com.zycx.system.sys.entity.FqtKyedInfo;
import com.zycx.system.sys.entity.QueryFqtKyedInfo;
import com.zycx.system.sys.dao.FqtKyedInfoDao;

/**
 *@author gly
 **/
@Service("fqtKyedInfoService")
@Transactional(rollbackFor={IllegalArgumentException.class})
public class FqtKyedInfoService extends GenericService<FqtKyedInfo, QueryFqtKyedInfo> {
	@Autowired
	@SuppressWarnings("SpringJavaAutowiringInspection")
	private FqtKyedInfoDao fqtKyedInfoDao;
	@Override
	protected GenericDao<FqtKyedInfo, QueryFqtKyedInfo> getDao() {
		return fqtKyedInfoDao;
	}
}