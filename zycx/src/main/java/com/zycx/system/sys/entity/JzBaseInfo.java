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
public class JzBaseInfo  implements Serializable {
	/**
 	 *id
 	 */
	private Integer id;
	/**
 	 *员工编号
 	 */
	private String employeeId;
	/**
 	 *员工姓名
 	 */
	private String employeeName;
	/**
 	 *进件类型（直客、间客）
 	 */
	private String jjType;
	/**
 	 *网点代码
 	 */
	private Integer bankInfoId;
	/**
	 *进件时间
 	 */
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date jjTime;
	/**
     *客户姓名
     */
	private String customerName;
	/**
 	 *客户身份证号码
 	 */
	private String identityCode;
	/**
	 *申请书编号
 	 */
	private String applyCode;
	/**
	 *联系电话
	 */
	private String contactNumber;
	/**
	 *工作单位
	 */
	private String workCompany;
	/**
	 *房屋性质
	 */
	private String houseType;
	/**
	 *分期期数
	 */
	private Integer jzFqQs;
	/**
	 *费率
	 */
	private BigDecimal jzFqRate;
	/**
	 *分期金额
	 */
	private BigDecimal jzFqMoney;
	/**
	 *审批金额
	 */
	private BigDecimal jzSpMoney;
	/**
	 *通过时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date passDate;
	/**
 	 *一次调额时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date firstTeTime;
	/**
	 *一次调额金额
	 */
	private BigDecimal firstTeMoney;
	/**
	 *二次调额时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date secondTeTime;
	/**
 	 *二次调额金额
 	 */
	private BigDecimal secondTeMoney;
	/**
	 *录入人员
	 */
	private Integer operater;
	/**
	 *一次支用标识：1：已支用，2：未支用
	 */
	private String firstZyFlag;
	/**
	 *二次支用标识：1：已支用，2：未支用
	 */
	private String secondZyFlag;
	/**
	 *额度失效时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date edEndTime;
	/**
	 * 卡号
	 */
	private String cardNumber;
	/**
	 * 主管
	 */
	private String governor;
	/**
	 * 受理机构
	 */
	private String acceptorg;
	/**
	 * 营销机构
	 */
	private String yxqd;
	/**
	 * 是否归档
	 */
	private String isGd;
	/**
	 * 归档时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date guidangDate;

	//网点名称
	private String bankName;
	//所属支行名称
	private String parentBankName;
	//额度剩余有效天数
	private Integer validDays;
	//导出Excel行序号
	private int serialNumber;
	//调额金额
	private BigDecimal teMoney;
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
