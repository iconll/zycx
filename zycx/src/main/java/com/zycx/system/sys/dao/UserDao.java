package com.zycx.system.sys.dao;



import com.zycx.system.common.base.dao.GenericDao;
import com.zycx.system.sys.entity.QueryUser;
import com.zycx.system.sys.entity.User;

import java.util.List;

/**
 *@author linzf
 **/
public interface UserDao extends GenericDao<User, QueryUser> {
    /**
     * 获取用户主菜单的Code值
     * @param id 用户ID
     * @return
     */
    List<String> findUserTreeCodeByUserId(Integer id);
    /**
     * 修改用户密码
     * @param entity 用户信息
     */
    public void updatePassword(User entity);
    /**
     * 功能描述：统计组织架构底下的用户
     * @param queryUser
     * @return
     */
    int countGroupUser(QueryUser queryUser);

    /**
     * 功能描述：查询组织架构底下的用户
     * @param queryUser
     * @return
     */
    List<User> findGroupUserByPage(QueryUser queryUser);

    /**
     * 功能描述：更新用户状态为可用或者不可用
     * @param user
     * @return
     */
    int userControl(User user);

    /**
     * 功能描述：根据账号来获取用户信息
     * @param login
     * @return
     */
    User findByLogin(String login);

    /**
     * 功能描述：更新用户的最迟登陆时间
     * @param user
     * @return
     */
    int updateLogin(User user);

	
}