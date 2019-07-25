package com.zycx.system.sys.entity;

import com.zycx.system.common.base.entity.QueryBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 *@author gly
 **/
@NoArgsConstructor
@Getter
@Setter
@ToString
public class QueryJzBaseInfo extends QueryBase {
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
	private String jjTime;
	/**
	 * 进件开始时间
	 */
	private String jjStartTime;
	/**
	 * 进件结束时间
	 */
	private String jjEndTime;
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
	private Double jzFqRate;
	/**
	*分期金额
	*/
	private Double jzFqMoney;
	/**
	*审批金额
	*/
	private Double jzSpMoney;
	/**
	*通过时间
	*/
	private String passDate;
	/**
	*一次调额时间
	*/
	private String firstTeTime;
	/**
	*一次调额金额
	*/
	private Double firstTeMoney;
	/**
	*二次调额时间
	*/
	private String secondTeTime;
	/**
	*二次调额金额
	*/
	private Double secondTeMoney;
	/**
	*剩余可用额度
	*/
	private Double surplusMoney;
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
	private String edEndTime;
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
	private Date guidangDate;

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
	//审批通过月份
	private String passMonth;
}
