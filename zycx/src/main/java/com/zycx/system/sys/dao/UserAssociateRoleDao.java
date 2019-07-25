package com.zycx.system.sys.dao;


import com.zycx.system.common.base.dao.GenericDao;
import com.zycx.system.sys.entity.QueryUserAssociateRole;
import com.zycx.system.sys.entity.User;
import com.zycx.system.sys.entity.UserAssociateRole;

/**
 *@author linzf
 **/
public interface UserAssociateRoleDao extends GenericDao<UserAssociateRole, QueryUserAssociateRole> {

    /**
     * 功能描述：根据用户的ID来删除用户的权限数据
     * @param user
     * @return
     */
    int removeUserRole(User user);
}