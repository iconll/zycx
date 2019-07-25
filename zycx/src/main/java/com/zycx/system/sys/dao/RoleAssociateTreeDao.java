package com.zycx.system.sys.dao;


import com.zycx.system.common.base.dao.GenericDao;
import com.zycx.system.sys.entity.QueryRoleAssociateTree;
import com.zycx.system.sys.entity.RoleAssociateTree;
import com.zycx.system.sys.entity.Tree;
import com.zycx.system.sys.entity.UserRole;

/**
 *@author linzf
 **/
public interface RoleAssociateTreeDao extends GenericDao<RoleAssociateTree, QueryRoleAssociateTree> {

    /**
     * 功能描述：根据菜单ID来删除关联的菜单数据
     * @param tree
     * @return
     */
    int removeTreeByTreeId(Tree tree);

    /**
     * 功能描述：根据角色ID来删除关联的菜单数据
     * @param userRole
     * @return
     */
    int removeTreeByRoleId(UserRole userRole);
	
}