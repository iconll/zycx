package com.zycx.system.sys.dao;


import com.zycx.system.common.base.dao.GenericDao;
import com.zycx.system.sys.entity.QueryTree;
import com.zycx.system.sys.entity.Tree;
import com.zycx.system.sys.entity.User;

import java.util.List;

/**
 *@author linzf
 **/
public interface TreeDao extends GenericDao<Tree, QueryTree> {

    /**
     * 功能描述：加载用户的菜单树的数据
     * @param user
     * @return
     */
	List<Tree> loadUserTree(User user);
}