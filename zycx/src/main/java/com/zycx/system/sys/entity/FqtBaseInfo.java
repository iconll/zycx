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
public class FqtBaseInfo implements Serializable {
	/**
	 *主键ID
	 */
	private Integer id;
	//营销员
	private String salesman;
	//网点ID
	private Integer bankInfoId;
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
	//申请编码（F码）
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
	//卡号
	private String cardNumber;
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	//额度失效日期
	private Date expiryDate;
	//额度剩余有效天数
	private Integer validDays;
	//渠道
	private String source;
	//录入人
	private Integer recordPerson;
	//导入时间
	private Date importTime;
	//导出Excel行序号
	private int serialNumber;
	//调额中收
	private BigDecimal tezsMoney;
	//剩余可用额度
	private BigDecimal surplusMoney;
	//剩余中收
	private BigDecimal syzsMoney;
	//支用金额
	private BigDecimal disburseMoney;
	//支用中收
	private  BigDecimal disburseZSMoney;
	//当月支用金额
	private BigDecimal dyzyMoney;
	//当月支用中收
	private BigDecimal dyzyzsMoney;
	//支用率
	private BigDecimal zyRate;

}
