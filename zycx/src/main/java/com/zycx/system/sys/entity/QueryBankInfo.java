package com.zycx.system.sys.entity;

import com.zycx.system.common.base.entity.QueryBase;

/**
 *@author gly
 **/
public class QueryBankInfo extends QueryBase {
	//网点名称 
	private String bankName;
	//网点代码 
	private String bankCode;
	//联系人 
	private String contacts;
	//电话号码 
	private String phoneCode;
	//所属管辖支行 
	private String parentId;

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}
