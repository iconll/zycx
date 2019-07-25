package com.zycx.system.sys.dao;


import com.zycx.system.common.base.dao.GenericDao;
import com.zycx.system.sys.entity.QueryUserRole;
import com.zycx.system.sys.entity.UserRole;

/**
 *@author linzf
 **/
public interface UserRoleDao extends GenericDao<UserRole, QueryUserRole> {

    /**
     * 功能描述：获取权限菜单数据
     * @param entity
     * @return
     */
    UserRole getUserRoleAssociate(UserRole entity);
	
}