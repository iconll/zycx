package com.zycx.system.sys.dao;


import com.zycx.system.common.base.dao.GenericDao;
import com.zycx.system.sys.entity.OrgGroup;
import com.zycx.system.sys.entity.QueryOrgGroup;

/**
 *@author linzf
 **/
public interface OrgGroupDao extends GenericDao<OrgGroup, QueryOrgGroup> {

    /**
     * 功能描述：根据父节点来查询最大的节点的值
     * @param parentNode
     * @return
     */
    String getMaxOrgGroup(String parentNode);

    /**
     * 功能描述：根据菜单节点NODE来查询节点数据
     * @param node
     * @return
     */
    OrgGroup findByNode(String node);
}