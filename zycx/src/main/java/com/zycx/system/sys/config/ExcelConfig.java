package com.zycx.system.sys.config;


import com.zycx.system.sys.entity.QueryFqtBaseInfo;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Excel导入导出字段属性配置类，统一维护
 */
public class ExcelConfig {

    /**
     * 解决不同浏览器下载文件，中文文件名乱码问题
     * @param request 浏览器请求，获取浏览器类型
     * @param fileName 文件名
     * @return 处理后的文件名
     */
    public static String findDownloadExcelFileName(HttpServletRequest request,String fileName) {
        String newFileName = "";

        //解决不同浏览器下载文件，中文文件名乱码问题
        try {
            //firefox浏览器
            if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
                newFileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            } else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
                // IE浏览器
                newFileName = URLEncoder.encode(fileName, "UTF-8");
            } else if (request.getHeader("User-Agent").toUpperCase().indexOf("CHROME") > 0) {
                // 谷歌
                newFileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            }else{
                return fileName;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return fileName;
        }

        return newFileName;
    }

    /**
     * 分期通根据参数获取导出Excel的文件名
     * @param key 根据key获取对应的文件名
     * @param validDays 额度有效期天数
     * @param month 统计月份
     * @return
     */
    public static String getExportExcelFileName(String key,Integer validDays,String month){
        String fileName = "";

        switch (key){
            //分期通额度有效期预警导出
            case "fqt_edyxqyj":
                if(validDays == null || validDays == 0){

                    fileName = "分期通额度有效期预警";
                }else{

                    fileName = "分期通额度有效期"+validDays+"天内预警";
                }
                break;
            //分期通基础数据统计客户报表导出
            case "fqt_khReportExport":
                fileName = "分期通基础数据"+month+"统计报表（客户）";
                break;
            //分期通基础数据统计员工报表导出
            case "fqt_ygReportExport":
                fileName = "分期通基础数据"+month+"统计报表（员工）";
                break;
            //分期通基础数据统计网点报表导出
            case "fqt_wdReportExport":
                fileName = "分期通基础数据"+month+"统计报表（网点）";
                break;
            //家装分期额度有效预警数据导出
            case "jz_edyxqyj":
                if(validDays == null || validDays == 0){

                    fileName = "家装分期额度有效期预警";
                }else{

                    fileName = "家装分期额度有效期"+validDays+"天内预警";
                }
                break;
            //家装分期基础数据统计（员工）
            case "jz_ygReportExport":
                fileName = "家装分期基础数据"+month+"统计报表（员工）";
                break;
            //家装分期基础数据统计（网点）
            case "jz_wdReportExport":
                fileName = "家装分期基础数据"+month+"统计报表（网点）";
                break;
        }

        return fileName;
    }

    /**
     * 维护导出Excel列头和实体类字段对应关系
     * @param key 根据key值获取对应维护的value
     * @return
     */
    public static LinkedHashMap<String, String> getExportExcelMetaField(String key){
        Map<String,LinkedHashMap<String, String>> metaMap = new HashMap<String,LinkedHashMap<String, String>>();
        LinkedHashMap<String, String> fqtyj = new LinkedHashMap<String, String>();
        fqtyj.put("serialNumber","序号");fqtyj.put("salesman","营销员");
        fqtyj.put("bankName","网点名称");fqtyj.put("parentBankName","所属管辖支行");
        fqtyj.put("jjTime","进件时间");fqtyj.put("customerName","客户姓名");
        fqtyj.put("identityCode","客户身份证号码"); fqtyj.put("contactNumber","联系电话");
        fqtyj.put("fqqs","分期期数");fqtyj.put("fqRate","费率(%)");
        fqtyj.put("fqMoney","分期金额");fqtyj.put("applyCode","申请编号");
        fqtyj.put("teMoney","调额金额");fqtyj.put("teTime","调额时间");
        fqtyj.put("tezsMoney","调额中收");
        fqtyj.put("surplusMoney","剩余可用额度");fqtyj.put("disburseMoney","支用金额");
        fqtyj.put("disburseZSMoney","支用中收");fqtyj.put("cardNumber","卡号");
        fqtyj.put("expiryDate","额度失效时间");fqtyj.put("validDays","额度剩余有效天数");

        //分期通额度有效期预警导出Excel
        metaMap.put("fqt_edyxqyj",fqtyj);

        LinkedHashMap<String, String> fqtyg = new LinkedHashMap<String, String>();
        //分期通基础数据统计报表（员工）
        fqtyg.put("serialNumber","序号");fqtyg.put("salesman","营销员");
        fqtyg.put("teMoney","调额金额");fqtyg.put("tezsMoney","调额中收");
        fqtyg.put("dyzyMoney","当月支用金额");fqtyg.put("dyzyzsMoney","当月支用中收");
        fqtyg.put("disburseMoney","累计支用金额"); fqtyg.put("disburseZSMoney","累计支用中收");
        fqtyg.put("surplusMoney","剩余可用额度");fqtyg.put("syzsMoney","剩余中收");
        fqtyg.put("zyRate","支用率(%)");

        metaMap.put("fqt_ygReportExport",fqtyg);

        //分期通基础数据统计报表（网点）
        LinkedHashMap<String, String> fqtwd = new LinkedHashMap<String, String>();
        fqtwd.put("serialNumber","序号");fqtwd.put("bankName","网点名称");
        fqtwd.put("parentBankName","所属管辖支行");fqtwd.put("teMoney","调额金额");
        fqtwd.put("tezsMoney","调额中收");fqtwd.put("dyzyMoney","当月支用金额");
        fqtwd.put("dyzyzsMoney","当月支用中收");fqtwd.put("disburseMoney","累计支用金额");
        fqtwd.put("disburseZSMoney","累计支用中收");fqtwd.put("surplusMoney","剩余可用额度");fqtwd.put("syzsMoney","剩余中收");
        fqtwd.put("zyRate","支用率(%)");

        metaMap.put("fqt_wdReportExport",fqtwd);

        //家装分期额度有效期预警导出
        LinkedHashMap<String, String> jzEdyxqyj = new LinkedHashMap<String, String>();

        jzEdyxqyj.put("serialNumber","序号");jzEdyxqyj.put("employeeName","营销员");
        jzEdyxqyj.put("employeeId","营销员编号");jzEdyxqyj.put("bankName","网点名称");
        jzEdyxqyj.put("parentBankName","所属管辖支行");jzEdyxqyj.put("jjTime","进件时间");
        jzEdyxqyj.put("jjType","进件类型");jzEdyxqyj.put("customerName","客户姓名");
        jzEdyxqyj.put("identityCode","客户身份证号码");jzEdyxqyj.put("contactNumber","联系电话");
        jzEdyxqyj.put("workCompany","工作单位");jzEdyxqyj.put("houseType","房屋性质");
        jzEdyxqyj.put("jzFqQs","分期期数");jzEdyxqyj.put("jzFqRate","费率(%)'");
        jzEdyxqyj.put("jzFqMoney","分期金额");jzEdyxqyj.put("jzSpMoney","审批金额");
        jzEdyxqyj.put("passDate","通过时间");jzEdyxqyj.put("firstTeMoney","一次调额金额");
        jzEdyxqyj.put("firstTeTime","一次调额时间");jzEdyxqyj.put("firstZyFlag","一次支用标识");
        jzEdyxqyj.put("secondTeMoney","二次调额金额");jzEdyxqyj.put("secondTeTime","二次调额时间");
        jzEdyxqyj.put("secondZyFlag","二次支用标识");jzEdyxqyj.put("disburseMoney","支用金额");
        jzEdyxqyj.put("disburseZSMoney","支用中收");jzEdyxqyj.put("surplusMoney","剩余可用额度");
        jzEdyxqyj.put("cardNumber","卡号");jzEdyxqyj.put("edEndTime","额度失效时间");
        jzEdyxqyj.put("validDays","额度剩余有效天数");


        metaMap.put("jz_edyxqyj",jzEdyxqyj);

        //家装分期基础数据统计员工报表导出
        LinkedHashMap<String, String> jzYg = new LinkedHashMap<String, String>();

        jzYg.put("serialNumber","序号");jzYg.put("employeeId","营销员");
        jzYg.put("employeeName","营销员编号");
        jzYg.put("teMoney","调额金额");jzYg.put("tezsMoney","调额中收");
        jzYg.put("dyzyMoney","当月支用金额");jzYg.put("dyzyzsMoney","当月支用中收");
        jzYg.put("disburseMoney","累计支用金额"); jzYg.put("disburseZSMoney","累计支用中收");
        jzYg.put("surplusMoney","剩余可用额度");jzYg.put("syzsMoney","剩余中收");
        jzYg.put("zyRate","支用率(%)");

        metaMap.put("jz_ygReportExport",jzYg);

        LinkedHashMap<String, String> jzWd = new LinkedHashMap<String, String>();

        jzWd.put("serialNumber","序号");jzWd.put("bankName","网点名称");
        jzWd.put("parentBankName","所属管辖支行");jzWd.put("teMoney","调额金额");
        jzWd.put("tezsMoney","调额中收");jzWd.put("dyzyMoney","当月支用金额");
        jzWd.put("dyzyzsMoney","当月支用中收");jzWd.put("disburseMoney","累计支用金额");
        jzWd.put("disburseZSMoney","累计支用中收");jzWd.put("surplusMoney","剩余可用额度");
        jzWd.put("syzsMoney","剩余中收");jzWd.put("zyRate","支用率(%)");
        metaMap.put("jz_wdReportExport",jzWd);

        return metaMap.get(key);
    }

    /**
     * 维护导入数据节点：Excel导入数据列和实体类字段对应关系
     * @param key 导入数据节点
     * @return 实体类对应的字段
     */
    public static String getImportExcelMetaField(String key){
        Map<String,String> metaMap = new HashMap<String,String>();

        //分期通基础数据
        metaMap.put("fqt_base_data", "serialNumber,salesman,bankCode,jjTime,customerName,identityCode," +
                "applyCode,contactNumber,fqqs,fqRate,fqMoney,teTime,teMoney");

        //分期通卡号数据
        metaMap.put("fqt_kh_data", "serialNumber,applyCode,cardNumber");

        //分期通剩余可用额度数据
        metaMap.put("fqt_kyed_data", "serialNumber,applyCode,expiry_Date,surplusMoney,operation_Time");

        //家装分期基础数据
        metaMap.put("jz_base_data","serialNumber,employeeName,employeeId,jjType,bankCode,jjTime,customerName," +
                "identityCode,applyCode,contactNumber,workCompany,houseType,jzFqQs,jzFqRate,jzFqMoney,jzSpMoney," +
                "passDate,governor,acceptorg,yxqd,isGd,guidangDate");

        //家装分期卡号数据
        metaMap.put("jz_kh_data","serialNumber,applyCode,cardNumber");

        //家装分期调额数据
        metaMap.put("jz_te_data","serialNumber,applyCode,firstTeTime,firstTeMoney,firstZyFlag,secondTeTime," +
                "secondTeMoney,secondZyFlag");

        //家装分期可用额度数据
        metaMap.put("jz_kyed_data","serialNumber,applyCode,yxorgId,employeeId,expiry_date,edEndTime," +
                "surplusMoney,operation_time");


        return metaMap.get(key);
    }
}