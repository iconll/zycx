package com.zycx.system.sys.service;

import com.zycx.system.common.base.dao.GenericDao;
import com.zycx.system.common.base.service.GenericService;
import com.zycx.system.sys.dao.RoleAssociateTreeDao;
import com.zycx.system.sys.entity.QueryRoleAssociateTree;
import com.zycx.system.sys.entity.RoleAssociateTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *@author linzf
 **/
@Service("roleAssociateTreeService")
@Transactional(rollbackFor={IllegalArgumentException.class})
public class RoleAssociateTreeService extends GenericService<RoleAssociateTree, QueryRoleAssociateTree> {
	@Autowired
	@SuppressWarnings("SpringJavaAutowiringInspection")
	private RoleAssociateTreeDao roleAssociateTreeDao;
	@Override
	protected GenericDao<RoleAssociateTree, QueryRoleAssociateTree> getDao() {
		return roleAssociateTreeDao;
	}
}