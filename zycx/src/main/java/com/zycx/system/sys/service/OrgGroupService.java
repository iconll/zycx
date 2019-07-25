package com.zycx.system.sys.service;


import com.zycx.system.common.base.dao.GenericDao;
import com.zycx.system.common.base.service.GenericService;
import com.zycx.system.sys.dao.OrgGroupDao;
import com.zycx.system.sys.entity.OrgGroup;
import com.zycx.system.sys.entity.QueryOrgGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *@author linzf
 **/
@Service("orgGroupService")
@Transactional(rollbackFor={IllegalArgumentException.class})
public class OrgGroupService extends GenericService<OrgGroup, QueryOrgGroup> {
	@Autowired
	@SuppressWarnings("SpringJavaAutowiringInspection")
	private OrgGroupDao orgGroupDao;
	@Override
	protected GenericDao<OrgGroup, QueryOrgGroup> getDao() {
		return orgGroupDao;
	}

	/**
	 * 功能描述：根据父节点来查询最大的节点的值
	 * @param parentNode
	 * @return
	 */
	public String getMaxOrgGroup(String parentNode){
		return orgGroupDao.getMaxOrgGroup(parentNode);
	}

	/**
	 * 功能描述：根据菜单节点NODE来查询节点数据
	 * @param node
	 * @return
	 */
	public OrgGroup findByNode(String node){
		return orgGroupDao.findByNode(node);
	}
}