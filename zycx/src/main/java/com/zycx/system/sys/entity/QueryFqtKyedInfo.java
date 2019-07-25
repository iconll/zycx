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
public class QueryFqtKyedInfo extends QueryBase {
	//分期通基础数据主键ID，根据申请编码找到基础数据，然后使用外键关联 
	private Integer fqtBaseInfoId;
	//剩余可用额度 
	private Double surplusMoney;
	//额度失效时间 
	private String expiryDate;
	//当前时间：额度使用时间 
	private String operationTime;
	//当前支用金额 
	private Double currentExpendMoney;
	//导入时间 
	private String importTime;
}
