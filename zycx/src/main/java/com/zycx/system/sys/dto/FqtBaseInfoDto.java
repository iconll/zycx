package com.zycx.system.sys.dto;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *@author gly
 **/
public class FqtBaseInfoDto implements Serializable {
	//主键ID 
	private int id;
	//系统员工ID 
	private int userId;
	//营销员名称
	private String userName;
	//营销员编码
	private String userCode;
	//网点ID 
	private int bankInfoId;
	//网点名称
	private String bankName;
	//所属支行名称
	private String parentBankName;
	//进件时间
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date jjTime;
	//客户姓名 
	private String customerName;
	//客户身份证号码 
	private String identityCode;
	//申请编码 
	private String applyCode;
	//联系电话 
	private String contactNumber;
	//分期期数 
	private int fqqs;
	//分期费率 
	private BigDecimal fqRate;
	//分期金额 
	private BigDecimal fqMoney;
	//调额时间
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date teTime;
	//调额金额 
	private BigDecimal teMoney;
	//调额中收
	private BigDecimal tezsMoney;
	//剩余可用额度
	private BigDecimal surplusMoney;
	//支用金额
	private BigDecimal disburseMoney;
	//支用中收
	private  BigDecimal disburseZSMoney;
	//卡号 
	private String cardNumber;
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	//额度失效日期 
	private Date expiryDate;
	//额度剩余有效天数
	private int validDays;
	//渠道 
	private String source;
	//录入人 
	private int recordPerson;
	//是否删除:1:未删除，2:已删除 
	private String isDel;
	//导入时间 
	private Date importTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getBankInfoId() {
		return bankInfoId;
	}

	public void setBankInfoId(int bankInfoId) {
		this.bankInfoId = bankInfoId;
	}

	public Date getJjTime() {
		return jjTime;
	}

	public void setJjTime(Date jjTime) {
		this.jjTime = jjTime;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getIdentityCode() {
		return identityCode;
	}

	public void setIdentityCode(String identityCode) {
		this.identityCode = identityCode;
	}

	public String getApplyCode() {
		return applyCode;
	}

	public void setApplyCode(String applyCode) {
		this.applyCode = applyCode;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public int getFqqs() {
		return fqqs;
	}

	public void setFqqs(int fqqs) {
		this.fqqs = fqqs;
	}

	public BigDecimal getFqRate() {
		return fqRate;
	}

	public void setFqRate(BigDecimal fqRate) {
		this.fqRate = fqRate;
	}

	public BigDecimal getFqMoney() {
		return fqMoney;
	}

	public void setFqMoney(BigDecimal fqMoney) {
		this.fqMoney = fqMoney;
	}

	public Date getTeTime() {
		return teTime;
	}

	public void setTeTime(Date teTime) {
		this.teTime = teTime;
	}

	public BigDecimal getTeMoney() {
		return teMoney;
	}

	public void setTeMoney(BigDecimal teMoney) {
		this.teMoney = teMoney;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getRecordPerson() {
		return recordPerson;
	}

	public void setRecordPerson(int recordPerson) {
		this.recordPerson = recordPerson;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	public Date getImportTime() {
		return importTime;
	}

	public void setImportTime(Date importTime) {
		this.importTime = importTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
