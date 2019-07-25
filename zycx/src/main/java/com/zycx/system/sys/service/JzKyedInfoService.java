package com.zycx.system.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import com.zycx.system.common.base.service.GenericService;
import com.zycx.system.common.base.dao.GenericDao;

import com.zycx.system.sys.entity.JzKyedInfo;
import com.zycx.system.sys.entity.QueryJzKyedInfo;
import com.zycx.system.sys.dao.JzKyedInfoDao;

/**
 *@author gly
 **/
@Service("jzKyedInfoService")
@Transactional(rollbackFor={Exception.class})
public class JzKyedInfoService extends GenericService<JzKyedInfo, QueryJzKyedInfo> {
	@Autowired
	@SuppressWarnings("SpringJavaAutowiringInspection")
	private JzKyedInfoDao jzKyedInfoDao;
	@Override
	protected GenericDao<JzKyedInfo, QueryJzKyedInfo> getDao() {
		return jzKyedInfoDao;
	}
}