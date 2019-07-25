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
public class FqtKyedInfo  implements Serializable {
	//主键ID 
	private Integer id;
	/**
	 * 分期通基础数据主键ID，根据申请编码找到基础数据，然后使用外键关联
	 */
	private Integer fqtBaseInfoId;
	//剩余可用额度 
	private BigDecimal surplusMoney;
	//额度失效时间
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date expiryDate;
	//当前时间：额度使用时间
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date operationTime;
	//当前支用金额 
	private BigDecimal currentExpendMoney;
	//导入时间
	private Date importTime;

}
