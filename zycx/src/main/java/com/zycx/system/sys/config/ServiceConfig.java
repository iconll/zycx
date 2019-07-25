package com.zycx.system.sys.config;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

/**
 * 业务处理方法配置
 * @author gly
 * @date 2019年4月23日
 */
public class ServiceConfig {


    /**
     * 计算中收 公式:分期期数*费率*金额
     * @param fqqs 分期期数
     * @param fqRate 费率 百分制
     * @param money 金额
     * @return 中收
     */
    public static BigDecimal findZS(int fqqs, BigDecimal fqRate, BigDecimal money){
        BigDecimal resultMoney = null;

        if(fqRate != null && money != null && fqqs > 0){

            //计算出小数费率
            BigDecimal xsRate = fqRate.divide(new BigDecimal("100"));

            //计算中收，分期期数*费率*金额
            BigDecimal tezs = xsRate.multiply(new BigDecimal(fqqs)).multiply(money);

            //四舍五入保留两位小数
            resultMoney = tezs.setScale(2, BigDecimal.ROUND_HALF_UP);

        }

        return resultMoney;
    }
    /**
     * 对手机号码进行安全处理
     * @param phoneNumber 手机号码
     * @param start 开始位置
     * @param end 结束位置
     * @param replace 替换字符
     * @return 处理后的手机号码
     */
    public static String disposePhoneNumber(String phoneNumber,int start,int end,String replace){
        String result = "";

        if(StringUtils.isNotBlank(phoneNumber) && phoneNumber.length() >= 11){

            StringBuilder sbd = new StringBuilder(phoneNumber);
            sbd.replace(start,end,replace);

            result = sbd.toString();
        }

        return result;
    }

    /**
     * 对身份证号码进行安全处理
     * @param identityCode 18位身份证号码
     * @param start 开始位置
     * @param end 结束位置
     * @param replace 替换字符
     * @return 处理后的身份证号码
     */
    public static String disposeIdentityCode(String identityCode,int start,int end,String replace){
        String result = "";

        if(StringUtils.isNotBlank(identityCode) && identityCode.length() >= 18){

            StringBuilder sbd = new StringBuilder(identityCode);
            sbd.replace(start,end,replace);

            result = sbd.toString();
        }

        return result;
    }
}
