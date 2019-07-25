package com.zycx.system.sys.entity;

import com.zycx.system.common.base.entity.QueryBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *@author gly
 **/
@NoArgsConstructor
@Getter
@Setter
@ToString
public class QueryJzKyedInfo extends QueryBase {
	/**
*家装主表id
*/ 
	private Integer jzId;
	/**
*营销机构编号
*/ 
	private String yxorgId;
	/**
*受理员工编号
*/ 
	private String employeeId;
	/**
*额度生效日期
*/ 
	private String expiryDate;
	/**
*剩余可用额度
*/ 
	private Double surplusMoney;
	/**
*当前支用金额
*/ 
	private Double currentExpendMoney;
	/**
*当前时间：额度使用时间
*/ 
	private String operationTime;
	/**
*操作导入时间
*/ 
	private String importTime;
}
