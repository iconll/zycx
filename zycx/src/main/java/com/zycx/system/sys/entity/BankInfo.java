package com.zycx.system.sys.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 *@author gly
 **/
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BankInfo  implements Serializable {
	//主键ID 
	private Integer id;
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
}
