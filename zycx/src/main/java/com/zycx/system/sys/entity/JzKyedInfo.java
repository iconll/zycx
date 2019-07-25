package com.zycx.system.sys.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *@author gly
 **/
@NoArgsConstructor
@Getter
@Setter
@ToString
public class JzKyedInfo implements Serializable {
	/**
 	 *家装从表id
 	 */
	private Integer id;
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
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date expiryDate;
	/**
	 *剩余可用额度
 	*/
	private BigDecimal surplusMoney;
	/**
 	*当前支用金额
 	*/
	private BigDecimal currentExpendMoney;
	/**
 	*当前时间：额度使用时间
 	*/
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date operationTime;
	/**
 	*操作导入时间
 	*/
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date importTime;
}
