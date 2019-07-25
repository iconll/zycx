package com.zycx.system.sys.entity;

import java.io.Serializable;

/**
 *@author linzf
 **/
public class UserAssociateRole  implements Serializable {
	private int userId;
	private long roleId;

	public UserAssociateRole(){
		super();
	}

	public UserAssociateRole(int userId,long roleId){
		this.userId = userId;
		this.roleId = roleId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

}
