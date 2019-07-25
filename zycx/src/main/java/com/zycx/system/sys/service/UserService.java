package com.zycx.system.sys.service;


import com.zycx.system.common.base.dao.GenericDao;
import com.zycx.system.common.base.entity.Page;
import com.zycx.system.common.base.service.GenericService;
import com.zycx.system.common.config.security.CustomPasswordEncoder;
import com.zycx.system.common.util.ResponseData;
import com.zycx.system.common.util.user.UserInfo;
import com.zycx.system.sys.dao.UserAssociateRoleDao;
import com.zycx.system.sys.dao.UserDao;
import com.zycx.system.sys.entity.QueryUser;
import com.zycx.system.sys.entity.User;
import com.zycx.system.sys.entity.UserAssociateRole;
import com.zycx.system.sys.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 *@author linzf
 **/
@Service("userService")
@Transactional(rollbackFor={IllegalArgumentException.class})
public class UserService extends GenericService<User, QueryUser> {
	@Autowired
	@SuppressWarnings("SpringJavaAutowiringInspection")
	private UserDao userDao;

	@Inject
	@SuppressWarnings("SpringJavaAutowiringInspection")
	private UserAssociateRoleDao userAssociateRoleDao;

	@Override
	protected GenericDao<User, QueryUser> getDao() {
		return userDao;
	}

	/**
	 * 获取当前登陆用户的主菜单
	 * @param user
	 * @return
	 */
	public List<String> findCurrentLoginUserTreeCodeList(User user){

		return userDao.findUserTreeCodeByUserId(user.getId());
	}
	/**
	 * 修改用户密码
	 * @param entity 用户信息
	 */
	public Map<String,Object> updatePassword(User entity){

		if(entity == null){

			return ResponseData.returnResponseData(false,"操作失败，用户信息错误.",null);
		}

		User user = userDao.findByLogin(entity.getLogin());

		if(user == null){

			return ResponseData.returnResponseData(false,"该账号信息不存在.",null);
		}


		//判断用户填写的当前密码是否正确
		String oldPassword = entity.getOldPassword();

		CustomPasswordEncoder encoder = new CustomPasswordEncoder();
		if(!encoder.matches(oldPassword,user.getPassword())){

			return ResponseData.returnResponseData(false,"当前密码错误",null);
		}

		//将新的密码加密，并更新实体类的密码
		user.setPassword(encoder.encode(entity.getPassword()));

		try {
			userDao.updatePassword(user);

			return ResponseData.returnResponseData(true,"修改密码成功",null);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseData.returnResponseData(false,"修改用户密码失败.",null);
		}

	}
	/**
	 * 分页查询组织架构底下的用户
	 * @param queryUser 查询条件
	 *  */
	public Page findByGroupUserPage(QueryUser queryUser){
		List<User> list =  userDao.findGroupUserByPage(queryUser);
		int count = userDao.countGroupUser(queryUser);
		return new Page(list, count);
	}

	/**
	 * 功能描述：实现增加用户
	 * @param entity 保存对象
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean save(User entity) throws Exception {
		entity.setAddress(entity.getProvince()+entity.getCity()+entity.getDistrict()+entity.getStreetAddress());
		entity.setPassword(UserInfo.encode(entity.getPassword()));
		entity.setState("1");
		entity.packagingRoles(entity.getRoleArray());
		List<UserRole> userRoleList = entity.getRoles();
		boolean success = userDao.save(entity)>0;
		if(success){
			if(userRoleList.size()>0){
				for(UserRole userRole:userRoleList){
					userAssociateRoleDao.save(new UserAssociateRole(entity.getId(),userRole.getId()));
				}
			}
		}
		return success;
	}

	/**
	 * 功能描述：实现更新用户
	 * @param entity 修改对象
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean update(User entity) throws Exception {
		entity.packagingRoles(entity.getRoleArray());
		entity.setAddress(entity.getProvince()+entity.getCity()+entity.getDistrict()+entity.getStreetAddress());
		userAssociateRoleDao.removeUserRole(entity);
		if(entity.getRoles().size()>0){
			for(UserRole userRole:entity.getRoles()){
				userAssociateRoleDao.save(new UserAssociateRole(entity.getId(),userRole.getId()));
			}
		}
		return super.update(entity);
	}

	/**
	 * 功能描述：批量删除用户
	 * @param entityList
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean removeBath(List<User> entityList) throws Exception {
		for(User user:entityList){
			userAssociateRoleDao.removeUserRole(user);
		}
		return super.removeBath(entityList);
	}

	/**
	 * 功能描述：更新用户状态为可用或者不可用
	 * @param user
	 * @return
	 */
	public boolean userControl(User user){
		return userDao.userControl(user)>0;
	}

	/**
	 * 功能描述：根据账号来获取用户信息
	 * @param login
	 * @return
	 */
	public User findByLogin(String login){
		return userDao.findByLogin(login);
	}

}