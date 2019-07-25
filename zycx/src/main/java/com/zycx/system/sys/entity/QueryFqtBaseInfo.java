package com.zycx.system.sys.entity;

import com.zycx.system.common.base.entity.QueryBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 *@author gly
 **/
@NoArgsConstructor
@Getter
@Setter
@ToString
public class QueryFqtBaseInfo extends QueryBase {
	private Integer id;
	//营销员
	private String salesman;
	//网点ID 
	private Integer bankInfoId;
	//进件时间 
	private String jjTime;
	/**
	 * 进件开始时间
	 */
	private String jjStartTime;
	/**
	 * 进件结束时间
	 */
	private String jjEndTime;
	//客户姓名 
	private String customerName;
	//客户身份证号码 
	private String identityCode;
	//申请编码 
	private String applyCode;
	//联系电话 
	private String contactNumber;
	//分期期数 
	private Integer fqqs;
	//分期费率 
	private BigDecimal fqRate;
	//分期金额 
	private BigDecimal fqMoney;
	//调额时间 
	private String teTime;
	//调额金额 
	private BigDecimal teMoney;
	//卡号 
	private String cardNumber;
	//额度失效日期 
	private String expiryDate;
	//渠道 
	private String source;
	//录入人 
	private Integer recordPerson;
	//导入时间 
	private String importTime;
	//额度剩余有效天数
	private Integer validDays;
	//支用月份
	private String zyMonth;
	//调额月份
	private String teMonth;
	//模块key
	private String key;
	//报表类型
	private String reportType;
}
