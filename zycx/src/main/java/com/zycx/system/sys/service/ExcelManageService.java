package com.zycx.system.sys.service;

import com.alibaba.fastjson.JSON;
import com.zycx.system.common.base.constant.SystemStaticConst;
import com.zycx.system.common.util.ResponseData;
import com.zycx.system.common.util.excel.ExcelReader;
import com.zycx.system.common.util.excel.ExcelUtil;
import com.zycx.system.common.util.exception.ExcelFormatException;
import com.zycx.system.sys.config.ExcelConfig;
import com.zycx.system.sys.config.ServiceConfig;
import com.zycx.system.sys.dao.*;
import com.zycx.system.sys.dto.BaseDto;
import com.zycx.system.sys.entity.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UserDetails;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 *@author gly
 **/
@Service("excelManageService")
@Transactional(rollbackFor={Exception.class})
public class ExcelManageService {
	private static final Logger logger = LoggerFactory.getLogger(ExcelManageService.class);

	@Autowired
	@SuppressWarnings("SpringJavaAutowiringInspection")
	private FqtBaseInfoDao fqtBaseInfoDao;
	@Inject
	private UserDao userDao;
	@Inject
	private BankInfoDao bankInfoDao;
	@Inject
	private FqtKyedInfoDao fqtKyedInfoDao;
	@Inject
	private JzBaseInfoDao jzBaseInfoDao;
	@Inject
	private JzKyedInfoDao JzKyedInfoDao;

	/**
	 * 家装分期数据导出
	 * @param entity 查询实体类
	 * @return
	 */
	public void exportJzExcel(QueryJzBaseInfo entity,String sheetName, HttpServletResponse response) throws IOException {
		logger.info("=====ExcelManageService.exportJzExcel Begin=====");
		List<JzBaseInfo> jzBaseInfos = null;
		LinkedHashMap<String, String> fieldMap = null;

		logger.info("ExcelManageService.exportJzExcel Key = " + entity.getKey());
		switch(entity .getKey()){
			//家装分期额度有效预警导出
			case "jz_edyxqyj":
				jzBaseInfos = this.jzEdyxqyjExportExcelData(entity.getValidDays());
				fieldMap = ExcelConfig.getExportExcelMetaField("jz_edyxqyj");
				break;
			//家装分期基础数据统计员工报表导出
			case "jz_ygReportExport":
				jzBaseInfos = this.findJzYgReportExcelData(entity);
				fieldMap = ExcelConfig.getExportExcelMetaField("jz_ygReportExport");
				break;
			//家装分期基础数据统计网点报表导出
			case "jz_wdReportExport":
				jzBaseInfos = this.findJzWdReportExcelData(entity);
				fieldMap = ExcelConfig.getExportExcelMetaField("jz_wdReportExport");
				break;
		}
		ExcelUtil.listToExcel(jzBaseInfos,fieldMap,sheetName, response.getOutputStream());

		logger.info("=====ExcelManageService.exportJzExcel End=====");
	}
	/**
	 * 分期通数据导出
	 * @param entity 查询实体类
	 * @return
	 */
	public void exportExcel(QueryFqtBaseInfo entity,String sheetName, HttpServletResponse response) throws IOException {
		logger.info("=====ExcelManageService.exportExcel Begin=====");

		List<FqtBaseInfo> fqtBaseInfos = null;
		LinkedHashMap<String, String> fieldMap = null;

		logger.info("ExcelManageService.exportExcel Key = " + entity.getKey());

		switch(entity .getKey()){
			//分期通额度有效期预警导出
			case "fqt_edyxqyj":
				//1.根据预警天数查询对应的数据
				fqtBaseInfos = this.fqtEdyxqyjExportExcelData(entity.getValidDays());
				//2.获取实体类字段名和Excel列名对应Map
				fieldMap = ExcelConfig.getExportExcelMetaField("fqt_edyxqyj");
				//3.调用方法进行Excel处理
				break;
			//分期通基础信息统计客户报表导出
			case "fqt_ygReportExport":
				fqtBaseInfos = this.findFqtYgReportExcelData(entity);
				fieldMap = ExcelConfig.getExportExcelMetaField("fqt_ygReportExport");
				break;
			//分期通基础信息统计网点报表导出
			case "fqt_wdReportExport":
				fqtBaseInfos = this.findFqtWdReportExcelData(entity);
				fieldMap = ExcelConfig.getExportExcelMetaField("fqt_wdReportExport");
				break;
		}
		ExcelUtil.listToExcel(fqtBaseInfos,fieldMap,sheetName, response.getOutputStream());

		logger.info("=====ExcelManageService.exportExcel End=====");
	}
	/**
	 * 家装分期基础数据统计（网点）-导出报表数据
	 * @param entity
	 * @return
	 */
	private List<JzBaseInfo> findJzWdReportExcelData(QueryJzBaseInfo entity){
		logger.info("=====ExcelManageService.findJzWdReportExcelData Begin=====");
		logger.info("ExcelManageService.findJzWdReportExcelData Param:passMonth = " + entity.getPassMonth());

		if(StringUtils.isBlank(entity.getZyMonth())){

			entity.setZyMonth(entity.getPassMonth());
		}
		List<JzBaseInfo> result = jzBaseInfoDao.findJzWdReportData(entity);

		//循环设置行号
		if(result != null && result.size() > 0){
			logger.info("ExcelManageService.findJzWdReportExcelData list.size = " + result.size());
			int index = 0;

			for(JzBaseInfo jzBaseInfo : result){

				jzBaseInfo.setSerialNumber(++index);
			}
		}else{
			logger.info("ExcelManageService.findJzWdReportExcelData list is null or list.size() == 0");
		}

		logger.info("=====ExcelManageService.findJzWdReportExcelData End=====");

		return result;
	}
	/**
	 * 家装分期基础数据统计（员工）-导出报表数据
	 * @param entity
	 * @return
	 */
	private List<JzBaseInfo> findJzYgReportExcelData(QueryJzBaseInfo entity){
		logger.info("=====ExcelManageService.findJzYgReportExcelData Begin=====");
		logger.info("ExcelManageService.findJzYgReportExcelData Param:passMonth = " + entity.getPassMonth());


		if(StringUtils.isBlank(entity.getZyMonth())){

			entity.setZyMonth(entity.getPassMonth());
		}
		List<JzBaseInfo> result = jzBaseInfoDao.findJzYgReportData(entity);

		//循环设置行号
		if(result != null && result.size() > 0){
			int index = 0;

			for(JzBaseInfo jzBaseInfo : result){

				jzBaseInfo.setSerialNumber(++index);
			}
		}else{
			logger.info("ExcelManageService.findJzYgReportExcelData list is null or list.size() == 0");
		}

		logger.info("=====ExcelManageService.findJzYgReportExcelData End=====");

		return result;
	}
	/**
	 * 获取分期通基础数据统计网点报表数据
	 * @param entity 查询实体类
	 * @return 查询到的结果集
	 */
	private List<FqtBaseInfo> findFqtWdReportExcelData(QueryFqtBaseInfo entity){
		logger.info("=====ExcelManageService.findFqtWdReportExcelData Begin=====");
		logger.info("ExcelManageService.findFqtWdReportExcelData Param:teMonth = " + entity.getTeMonth());

		if(StringUtils.isBlank(entity.getZyMonth())){

			entity.setZyMonth(entity.getTeMonth());
		}

		List<FqtBaseInfo> result = fqtBaseInfoDao.findWdReportData(entity);

		//循环设置行号
		if(result != null && result.size() > 0){
			int index = 0;

			for(FqtBaseInfo fqtBaseInfo : result){

				fqtBaseInfo.setSerialNumber(++index);
			}
		}else{
			logger.info("ExcelManageService.findFqtWdReportExcelData list is null or list.size() == 0");
		}

		logger.info("=====ExcelManageService.findFqtWdReportExcelData End=====");

		return result;
	}
	/**
	 * 获取分期通基础数据统计员工报表数据
	 * @param entity 查询实体类
	 * @return 查询到的结果集
	 */
	private List<FqtBaseInfo> findFqtYgReportExcelData(QueryFqtBaseInfo entity){
		logger.info("=====ExcelManageService.findFqtYgReportExcelData Begin=====");
		logger.info("ExcelManageService.findFqtYgReportExcelData Param:teMonth = " + entity.getTeMonth());

		if(StringUtils.isBlank(entity.getZyMonth())){

			entity.setZyMonth(entity.getTeMonth());
		}

		List<FqtBaseInfo> result = fqtBaseInfoDao.findYgReportData(entity);

		//循环设置行号
		if(result != null && result.size() > 0){
			int index = 0;

			for(FqtBaseInfo fqtBaseInfo : result){

				fqtBaseInfo.setSerialNumber(++index);
			}
		}else{
			logger.info("ExcelManageService.findFqtYgReportExcelData list is null or list.size() == 0");
		}

		logger.info("=====ExcelManageService.findFqtYgReportExcelData End=====");

		return result;
	}
	/**
	 * 家装分期额度有效期预警数据
	 * @return
	 */
	private List<JzBaseInfo> jzEdyxqyjExportExcelData(Integer validDays){
		logger.info("=====ExcelManageService.jzEdyxqyjExportExcelData Begin=====");
		logger.info("ExcelManageService.jzEdyxqyjExportExcelData Param:validDays = " + validDays);

		QueryJzBaseInfo q = new QueryJzBaseInfo();
		q.setOrder("ASC");
		q.setSort("jj_time");//根据进件时间进行升序排序
		q.setValidDays(validDays);
		List<JzBaseInfo> list = jzBaseInfoDao.query(q);

		if(list != null && list.size() > 0){
			logger.info("ExcelManageService.jzEdyxqyjExportExcelData list.size = " + list.size());

			int serialNumber = 0;
			//对数据进行处理
			for(JzBaseInfo jzBaseInfo : list){
                //Excel行序号
                jzBaseInfo.setSerialNumber(++serialNumber);
				//3.计算审批中收
				//分期期数
				int fqqs = jzBaseInfo.getJzFqQs();
				//分期费率：百分比
				BigDecimal fqRate = jzBaseInfo.getJzFqRate();

				//获取一次调额金额
				BigDecimal firstTeMoney = jzBaseInfo.getFirstTeMoney();
				//获取二次调额金额
				BigDecimal secondTeMoney = jzBaseInfo.getSecondTeMoney();

				//计算总的调额金额
				BigDecimal teMoney = null;
				if(firstTeMoney != null && secondTeMoney != null){

					teMoney = firstTeMoney.add(secondTeMoney);
				}else if(firstTeMoney != null && secondTeMoney == null){

					teMoney = firstTeMoney;
				}else if(firstTeMoney == null && secondTeMoney != null){

					teMoney = secondTeMoney;
				}

				//计算调额中收
				BigDecimal tezs = ServiceConfig.findZS(fqqs, fqRate, teMoney);
				jzBaseInfo.setTezsMoney(tezs);

				//获取剩余可用额度
				BigDecimal surplusMoney = jzBaseInfo.getSurplusMoney();

				if(surplusMoney != null && teMoney != null){
					//判断如果剩余额度不为空，就计算支用额度和支用中收：调额金额-剩余可用额度
					BigDecimal disburseMoney = teMoney.subtract(surplusMoney);
					//5.支用金额
					jzBaseInfo.setDisburseMoney(disburseMoney);
					//6.支用中收
					BigDecimal disburseZSMoney = ServiceConfig.findZS(fqqs, fqRate, disburseMoney);
					jzBaseInfo.setDisburseZSMoney(disburseZSMoney);
				}
			}
		}else{
			logger.info("ExcelManageService.jzEdyxqyjExportExcelData list is null or list.size() == 0");
		}

		logger.info("=====ExcelManageService.jzEdyxqyjExportExcelData End=====");

		return list;
	}
	/**
	 * 分期通额度有效期预警数据
	 * @return
	 */
	private List<FqtBaseInfo> fqtEdyxqyjExportExcelData(Integer validDays){
		logger.info("=====ExcelManageService.fqtEdyxqyjExportExcelData Begin=====");
		logger.info("ExcelManageService.fqtEdyxqyjExportExcelData Param:validDays = " + validDays);

		QueryFqtBaseInfo q = new QueryFqtBaseInfo();
		q.setOrder("ASC");
		q.setSort("jj_time");//根据进件时间进行升序排序
		q.setValidDays(validDays);
		List<FqtBaseInfo> list = fqtBaseInfoDao.query(q);

		if(list != null && list.size() > 0){
			logger.info("ExcelManageService.fqtEdyxqyjExportExcelData list.size = " + list.size());

			int serialNumber = 0;
			//对数据进行处理
			for(FqtBaseInfo fqtBaseInfo : list){
				//Excel行序号
				fqtBaseInfo.setSerialNumber(++serialNumber);
				//1.调额中收
				int fqqs = fqtBaseInfo.getFqqs();//分期期数
				BigDecimal fqRate = fqtBaseInfo.getFqRate();//分期费率：百分比
				BigDecimal teMoney = fqtBaseInfo.getTeMoney();//调额金额
				//计算调额中收
				BigDecimal tezs = ServiceConfig.findZS(fqqs, fqRate, teMoney);
				fqtBaseInfo.setTezsMoney(tezs);

				//2.获取剩余可用额度
				BigDecimal surplusMoney = fqtBaseInfo.getSurplusMoney();

				if(surplusMoney != null && teMoney != null){
					//判断如果剩余额度不为空，就计算支用额度和支用中收
					BigDecimal disburseMoney = teMoney.subtract(surplusMoney);
					//3.支用金额
					fqtBaseInfo.setDisburseMoney(disburseMoney);
					//4.支用中收
					BigDecimal disburseZSMoney = ServiceConfig.findZS(fqqs, fqRate, disburseMoney);
					fqtBaseInfo.setDisburseZSMoney(disburseZSMoney);
				}
			}
		}else{
			logger.info("ExcelManageService.fqtEdyxqyjExportExcelData list is null or list.size() == 0");
		}

		logger.info("=====ExcelManageService.fqtEdyxqyjExportExcelData End=====");


		return list;
	}


	/**
	 * 处理Excel导入数据
	 * @param excelStream Excel文件流
	 * @param key 导入模板标识
	 * @return Map<String,Object> 处理结果
	 */
	public Map<String,Object> disposeImportExcelData(InputStream excelStream, String key) throws Exception {
		logger.info("=====ExcelManageService.disposeImportExcelData Begin=====");
		logger.info("ExcelManageService.disposeImportExcelData Param:key="+key);

		if(excelStream == null){

			return ResponseData.returnResponseData(SystemStaticConst.FAIL,"Excel文件流数据为空",null);
		}

		//获取数据库表字段和Excel表中列对应
		String metaData = ExcelConfig.getImportExcelMetaField(key);

        logger.info("ExcelManageService.disposeImportExcelData metaData = " + metaData);

        //传入文件输入流到Excel读取器
		ExcelReader excelReader = new ExcelReader(metaData, excelStream);

		List<BaseDto> list = null;

		try{

			//获取从第二行开始解析Excel所有数据到list集合中
			list = excelReader.read(1, 0);

		}catch (ExcelFormatException e){
            logger.error("ExcelManageService.disposeImportExcelData ExcelFormatException:" + e);

            return ResponseData.returnResponseData(SystemStaticConst.FAIL,e.getMessage(),null);
		}catch (Exception e){
            logger.error("ExcelManageService.disposeImportExcelData Exception:" + e);

            return ResponseData.returnResponseData(SystemStaticConst.FAIL,"读取Excel文件数据失败。",null);
		}
		//导入成功的数据条数
		int successCount = 0;
		//导入失败的数据条数
		int defeatedCount = 0;

		if(list != null && list.size() > 0){
            logger.info("ExcelManageService.disposeImportExcelData list.size = " + list.size());

            //存储提示信息
			List<String> errorMsgList = new ArrayList<String>();

			for(int i  = 0; i < list.size();i++){
				BaseDto entity = list.get(i);

				StringBuilder errorMsg = new StringBuilder();

				try{
					//1.数据校验
					errorMsg = this.validateImportExcelData(entity,key);
				}catch(Exception e){
					e.printStackTrace();
					errorMsg.append("处理数据失败，请检查数据格式.");
				}

				if(errorMsg.length() <= 0){

					successCount++;
				}else{//如果有错误消息，那么校验失败，添加错误消息

					//获取Excel中每行序号
					String serialNumber = entity.getAsString("serialNumber");
					errorMsg.insert(0,"Excel行序号：" + serialNumber +".");

					defeatedCount++;
					errorMsgList.add(errorMsg.toString());
				}
			}

			String result = "本次导入共" + list.size() + "条数据，成功" + successCount + "条，失败"+ defeatedCount + "条！";

			logger.info("=====ExcelManageService.disposeImportExcelData result: " + result);

			if(defeatedCount > 0){

				result += "失败原因：";
			}

			errorMsgList.add(0, result);

			logger.info("=====ExcelManageService.disposeImportExcelData End=====");
			return ResponseData.returnResponseData(SystemStaticConst.SUCCESS,"操作成功",errorMsgList);
		}else{
			logger.info("=====ExcelManageService.disposeImportExcelData importExcel no Data=====");
			return ResponseData.returnResponseData(SystemStaticConst.FAIL,"导入Excel中没有数据！",null);
		}
	}
	/**
	 * 分期通基础数据校验如果校验通过保存到数据库中
	 * @param entity Excel导入的数据
	 * @return errorMsg 为空校验通过，不为空校验不通过
	 */
	private StringBuilder validateFqtBaseData(BaseDto entity){
		logger.info("=====ExcelManageService.validateFqtBaseData Begin=====");

		StringBuilder errorMsg = new StringBuilder();

		if(entity != null && !entity.isEmpty()){
			String entityStr = JSON.toJSONString(entity);
			FqtBaseInfo fqtBaseInfo = JSON.parseObject(entityStr, FqtBaseInfo.class);

			//2.校验是否有调额时间:因为导入的数据是调额(审批)通过的数据
			if(entity.get("teTime") == null){
				errorMsg.append("调额时间不能为空.");
			}

			//3.校验申请编码(F码)是否存在
			String applyCode = entity.getAsString("applyCode");

			if(StringUtils.isBlank(applyCode)){

				errorMsg.append("申请编码不能为空.");
			}else{

				QueryFqtBaseInfo query = new QueryFqtBaseInfo();
				query.setApplyCode(applyCode);

				//根据申请编码
				int count = fqtBaseInfoDao.count(query);

				if(count > 0){

					errorMsg.append("申请编码对应的基础数据已存在，不能重复导入.");
				}
			}

			//4.校验网点代码是否存在
			String bankCode = entity.getAsString("bankCode");

			if(StringUtils.isBlank(bankCode)){

				errorMsg.append("网点代码不能为空.");
			}else{

				BankInfo bankInfo = bankInfoDao.getBankInfoByBankCode(bankCode);

				if(bankInfo == null){

					errorMsg.append("网点代码在系统中不存在.");
				}else{

					fqtBaseInfo.setBankInfoId(bankInfo.getId());
				}
			}

			//没有错误消息的话就直接添加到数据库中
			if(errorMsg.length() <= 0){
				//设置缺省值
				fqtBaseInfo.setImportTime(new Date());

				try {
					//获取当前登陆用户的用户名
					UserDetails userDetails = (UserDetails)SecurityContextHolder
															.getContext()
															.getAuthentication()
															.getPrincipal();

					//根据用户名查询用户信息
					User user = userDao.findByLogin(userDetails.getUsername());

					if(user != null){
						//设置操作人
						fqtBaseInfo.setRecordPerson(user.getId());
					}

					//如果没有错误信息就保存到数据库中
					int save = fqtBaseInfoDao.save(fqtBaseInfo);

					if(save <= 0){
						errorMsg.append("保存数据失败.");
					}
				} catch (Exception e) {
					logger.error("=====ExcelManageService.validateFqtBaseData Exception : " + e);
					errorMsg.append("保存数据失败.");
				}
			}
		}
		logger.info("=====ExcelManageService.validateFqtBaseData End=====");

		return errorMsg;
	}
	/**
	 * 分期通剩余可用额度数据校验，如果校验通过保存到数据库中
	 * @param entity Excel导入的数据
	 * @return errorMsg 为空校验通过，不为空校验不通过
	 */
	private StringBuilder validateFqtKYEDData(BaseDto entity){
		logger.info("=====ExcelManageService.validateFqtKYEDData Begin=====");

		StringBuilder errorMsg = new StringBuilder();

		if(entity != null && !entity.isEmpty()) {
			String entityStr = JSON.toJSONString(entity);

            FqtKyedInfo fqtKyedInfo = JSON.parseObject(entityStr, FqtKyedInfo.class);
			FqtBaseInfo fqtBaseInfo = null;

			//2.校验申请编码
			String applyCode = entity.getAsString("applyCode");

			//2.1校验申请编码是否为空
			if(StringUtils.isBlank(applyCode)){

				errorMsg.append("申请编码不能为空.");
			}else{

				//2.2校验申请编码对应的基础数据是否已经导入
				fqtBaseInfo = fqtBaseInfoDao.getFqtBaseInfoByApplyCode(applyCode);

				if(fqtBaseInfo == null){
					errorMsg.append("未找到对应的申请编码基础数据.");
				}
			}

			//3.校验剩余可用额度不能为空
			BigDecimal surplusMoney = entity.getAsBigDecimal("surplusMoney");
			if(surplusMoney == null){

				errorMsg.append("剩余可用额度不能为空.");
			}
			//4.校验当前时间不能为空
			String operationTime = entity.getAsString("operation_Time");

			if(StringUtils.isBlank(operationTime)){

				errorMsg.append("当前时间不能为空.");
			}

			if(errorMsg.length() <= 0){//如果没有错误消息代表校验通过
				//计算当前支用额度
				//查询上一次剩余可用额度
				String lastSurplusMoney = fqtKyedInfoDao.findLastSurplusMoneyByFqtBaseInfoId(fqtBaseInfo.getId());

				//上次剩余可用额度
				BigDecimal expendMoney = null;
				//本次支用金额
				BigDecimal currentExpendMoney = null;
				//判断是否导入过剩余可用额度
				if(StringUtils.isBlank(lastSurplusMoney)){

					//如果没有导入过就使用调额金额
					expendMoney = fqtBaseInfo.getTeMoney();
				}else{
					//如果导入过就直接使用
					expendMoney = new BigDecimal(lastSurplusMoney);
				}
				//本次支用金额 = 上一次剩余可用额度-本次剩余可用额度
				currentExpendMoney = expendMoney.subtract(surplusMoney);

				//判断当前支用金额是否小于0
				int r = currentExpendMoney.compareTo(BigDecimal.ZERO);
				//如果小于0代表导入的剩余可用额度无效
				if(r < 0){

					errorMsg.append("导入的剩余可用额度无效.");
					return errorMsg;
				}

				//修改分期通基础数据中的额度失效时间
				fqtBaseInfo.setExpiryDate(entity.getAsDate("expiry_Date"));

				try {
					fqtBaseInfoDao.update(fqtBaseInfo);
				} catch (Exception e) {

					logger.error("ExcelManageService.validateFqtKYEDData Exception : " + e);
					throw new RuntimeException();
				}

				fqtKyedInfo.setCurrentExpendMoney(currentExpendMoney);
				fqtKyedInfo.setImportTime(new Date());
				fqtKyedInfo.setFqtBaseInfoId(fqtBaseInfo.getId());
				//将剩余可用额度信息保存到数据库
				try {
					int save = fqtKyedInfoDao.save(fqtKyedInfo);

					if(save <= 0){

						errorMsg.append("保存信息失败.");
					}
				} catch (Exception e) {
					errorMsg.append("保存信息失败.");
					logger.error("ExcelManageService.validateFqtKYEDData Exception : " + e);
				}
			}
		}
		logger.info("=====ExcelManageService.validateFqtKYEDData End=====");

		return errorMsg;
	}
	/**
	 * 分期通卡号数据校验如果校验通过保存到数据库中
	 * @param entity Excel导入的数据
	 * @return errorMsg 为空校验通过，不为空校验不通过
	 */
	private StringBuilder validateFqtKHData(BaseDto entity){
		logger.info("=====ExcelManageService.validateFqtKHData Begin=====");

		StringBuilder errorMsg = new StringBuilder();

		if(entity != null && !entity.isEmpty()) {
			FqtBaseInfo fqtBaseInfo = null;
			//2.获取卡号
			String cardNumber = entity.getAsString("cardNumber");

			if(StringUtils.isBlank(cardNumber)){

				errorMsg.append("卡号不能为空.");
			}

			//3.获取F码
			String applyCode = entity.getAsString("applyCode");

			if(StringUtils.isBlank(applyCode)){

				errorMsg.append("申请编码不能为空.");
			}else{
				//查询申请编码对应的数据是否存在
				fqtBaseInfo = fqtBaseInfoDao.getFqtBaseInfoByApplyCode(applyCode);

				if(fqtBaseInfo == null){
					errorMsg.append("未找到对应的申请编码基础数据.");
				}
			}

			if(errorMsg.length() <= 0){//如果没有错误消息

				fqtBaseInfo.setCardNumber(cardNumber);

				try{
					//修改数据
					int update = fqtBaseInfoDao.update(fqtBaseInfo);

					if(update <= 0){

						errorMsg.append("修改数据失败.");
					}
				}catch(Exception e){
					errorMsg.append("修改数据失败.");
					logger.error("=====ExcelManageService.validateFqtKHData Exception : " + e);
				}
			}
		}
		logger.info("=====ExcelManageService.validateFqtKHData End=====");

		return errorMsg;
	}
	/**
	 * 家装分期可用额度数据校验，如果校验通过保存到数据库中
	 * @param entity Excel导入的数据
	 * @return errorMsg 为空校验通过，不为空校验不通过
	 */
	private StringBuilder validateJzKyedData(BaseDto entity){
		logger.info("=====ExcelManageService.validateJzKyedData Begin=====");

		StringBuilder errorMsg = new StringBuilder();

		if(entity != null && !entity.isEmpty()) {

			String entityStr = JSON.toJSONString(entity);

			JzKyedInfo jzKyedInfo = JSON.parseObject(entityStr, JzKyedInfo.class);
			JzBaseInfo jzBaseInfo = null;

			//2.校验申请编码
			String applyCode = entity.getAsString("applyCode");

			//2.1校验申请编码是否为空
			if(StringUtils.isBlank(applyCode)){

				errorMsg.append("申请编码不能为空.");
			}else{

				//2.2校验申请编码对应的基础数据是否已经导入
				jzBaseInfo = jzBaseInfoDao.getJzBaseInfoByApplyCode(applyCode);

				if(jzBaseInfo == null){
					errorMsg.append("未找到对应的申请编码数据.");
				}

			}
			//判断如果没有导入过调额数据的话不能导入剩余可用额度数据
			//3.校验剩余可用额度不能为空
			BigDecimal surplusMoney = entity.getAsBigDecimal("surplusMoney");
			if(surplusMoney == null){

				errorMsg.append("剩余可用额度不能为空.");
			}
			//4.校验当前时间不能为空
			String operationTime = entity.getAsString("operation_time");

			if(StringUtils.isBlank(operationTime)){

				errorMsg.append("当前时间不能为空.");
			}

			//额度失效时间不能为空
			Date edEndTime = entity.getAsDate("edEndTime");

			if(edEndTime == null){

				errorMsg.append("额度失效时间不能为空.");
			}
			if(errorMsg.length() <= 0){//如果没有错误消息代表校验通过
				//6.计算当前支用额度:上一次剩余可用额度-本次导入的剩余可用额度
				BigDecimal currentExpendMoney = null;
				//获取上一次剩余可用额度
				BigDecimal beforeMoney = jzBaseInfo.getSurplusMoney();

				//如果上一次没有剩余可用额度,就说明没有导入过调额金额，提示用户先导入调额金额
				if(beforeMoney == null){

					errorMsg.append("未导入调额金额，请先导入调额金额.");
					return errorMsg;
					//currentExpendMoney = new BigDecimal(0+"");
				}else{
					//计算当前支用金额
					currentExpendMoney = beforeMoney.subtract(surplusMoney);

					//判断当前支用金额是否小于0
					int r = currentExpendMoney.compareTo(BigDecimal.ZERO);
					//如果小于0代表导入的剩余可用额度无效
					if(r < 0){

						errorMsg.append("导入的剩余可用额度无效.");
						return errorMsg;
					}
				}

				jzKyedInfo.setCurrentExpendMoney(currentExpendMoney);
				jzKyedInfo.setImportTime(new Date());
				jzKyedInfo.setJzId(jzBaseInfo.getId());
				//7.将剩余可用额度信息保存到数据库
				try {
					int save = JzKyedInfoDao.save(jzKyedInfo);

					if(save <= 0){

						errorMsg.append("保存信息失败.");
					}
				} catch (Exception e) {
					logger.error("=====ExcelManageService.validateJzKyedData Exception : " + e);
					errorMsg.append("保存信息失败.");
				}

				//5.修改分期通基础数据中的额度失效时间
				jzBaseInfo.setEdEndTime(edEndTime);
				jzBaseInfo.setSurplusMoney(surplusMoney);//更新剩余可用额度

				try {
					jzBaseInfoDao.update(jzBaseInfo);
				} catch (Exception e) {
					logger.error("=====ExcelManageService.validateJzKyedData Exception : " + e);
					e.printStackTrace();
					throw new RuntimeException();
				}
			}

		}
		logger.info("=====ExcelManageService.validateJzKyedData End=====");

		return errorMsg;
	}
	/**
	 * 家装分期调额数据校验，如果校验通过保存到数据库中
	 * @param entity Excel导入的数据
	 * @return errorMsg 为空校验通过，不为空校验不通过
	 */
	private StringBuilder validateJzTeData(BaseDto entity){
		logger.info("=====ExcelManageService.validateJzTeData Begin=====");

		StringBuilder errorMsg = new StringBuilder();

		if(entity != null && !entity.isEmpty()) {

			JzBaseInfo jzBaseInfo = null;

			//3.获取F码
			String applyCode = entity.getAsString("applyCode");

			if(StringUtils.isBlank(applyCode)){

				errorMsg.append("申请编码不能为空.");
			}else{
				//查询申请编码对应的数据是否存在
				jzBaseInfo = jzBaseInfoDao.getJzBaseInfoByApplyCode(applyCode);

				if(jzBaseInfo == null){
					errorMsg.append("未找到对应申请编码的基础数据.");
				}
			}

			if(errorMsg.length() <= 0){
				//一次调额金额
				BigDecimal firstTeMoney = null;
				//二次调额金额
				BigDecimal secondTeMoney = null;

				//判断如果一次调额金额未导入过就更新一次调额金额
				if(jzBaseInfo.getFirstTeMoney() == null){
					firstTeMoney = entity.getAsBigDecimal("firstTeMoney");

					jzBaseInfo.setFirstTeMoney(firstTeMoney);
					jzBaseInfo.setFirstTeTime(entity.getAsDate("firstTeTime"));
					jzBaseInfo.setFirstZyFlag(entity.getAsString("firstZyFlag"));
				}
				//如果二次调额金额未导入过就更新二次金额
				if(jzBaseInfo.getSecondTeMoney() == null){
					secondTeMoney = entity.getAsBigDecimal("secondTeMoney");

					jzBaseInfo.setSecondTeMoney(secondTeMoney);
					jzBaseInfo.setSecondTeTime(entity.getAsDate("secondTeTime"));
					jzBaseInfo.setSecondZyFlag(entity.getAsString("secondZyFlag"));
				}

				BigDecimal teMoney = null;
				if(firstTeMoney != null && secondTeMoney != null){

					teMoney = firstTeMoney.add(secondTeMoney);
				}else if(firstTeMoney != null && secondTeMoney == null){

					teMoney = firstTeMoney;
				}else if(firstTeMoney == null && secondTeMoney != null){

					teMoney = secondTeMoney;
				}

				//获取剩余可用额度
				BigDecimal surplusMoney = jzBaseInfo.getSurplusMoney();

				if(teMoney != null){

					if(surplusMoney == null){
						jzBaseInfo.setSurplusMoney(teMoney);
					}else{
						jzBaseInfo.setSurplusMoney(surplusMoney.add(teMoney));
					}
				}

				try{
					//修改数据
					int update = jzBaseInfoDao.update(jzBaseInfo);

					if(update <= 0){

						errorMsg.append("修改数据失败.");
					}
				}catch(Exception e){
					logger.error("ExcelManageService.validateJzTeData Exception : " + e);
					errorMsg.append("修改数据失败.");
				}
			}


		}
		logger.info("=====ExcelManageService.validateJzTeData End=====");
		return errorMsg;
	}
	/**
	 * 家装分期卡号数据校验，如果校验通过保存到数据库中
	 * @param entity Excel导入的数据
	 * @return errorMsg 为空校验通过，不为空校验不通过
	 */
	private StringBuilder validateJzKhData(BaseDto entity){
		logger.info("=====ExcelManageService.validateJzKhData Begin=====");

		StringBuilder errorMsg = new StringBuilder();

		if(entity != null && !entity.isEmpty()) {
			JzBaseInfo jzBaseInfo = null;
			//2.获取卡号
			String cardNumber = entity.getAsString("cardNumber");

			if(StringUtils.isBlank(cardNumber)){

				errorMsg.append("卡号不能为空.");
			}

			//3.获取F码
			String applyCode = entity.getAsString("applyCode");

			if(StringUtils.isBlank(applyCode)){

				errorMsg.append("申请编码不能为空.");
			}else{
				//查询申请编码对应的数据是否存在
				jzBaseInfo = jzBaseInfoDao.getJzBaseInfoByApplyCode(applyCode);

				if(jzBaseInfo == null){
					errorMsg.append("未找到对应的申请编码数据.");
				}
			}

			if(errorMsg.length() <= 0){//如果没有错误消息

				jzBaseInfo.setCardNumber(cardNumber);

				try{
					//修改数据
					int update = jzBaseInfoDao.update(jzBaseInfo);

					if(update <= 0){

						errorMsg.append("修改数据失败.");
					}
				}catch(Exception e){
					logger.error("=====ExcelManageService.validateJzKhData Exception : " + e);
					errorMsg.append("修改数据失败.");
				}
			}
		}
		logger.info("=====ExcelManageService.validateJzKhData End=====");
		return errorMsg;
	}
	/**
	 * 家装分期基础数据校验，如果校验通过保存到数据库中
	 * @param entity Excel导入的数据
	 * @return errorMsg 为空校验通过，不为空校验不通过
	 */
	private StringBuilder validateJzBaseData(BaseDto entity){
		logger.info("=====ExcelManageService.validateJzBaseData Begin=====");

		StringBuilder errorMsg = new StringBuilder();

		if(entity != null && !entity.isEmpty()) {
			String entityStr = JSON.toJSONString(entity);
			JzBaseInfo jzBaseInfo = JSON.parseObject(entityStr, JzBaseInfo.class);


			//校验申请编码(F码)是否存在
			String applyCode = entity.getAsString("applyCode");

			if(StringUtils.isBlank(applyCode)){

				errorMsg.append("申请编码不能为空.");
			}else{

				QueryJzBaseInfo query = new QueryJzBaseInfo();
				query.setApplyCode(applyCode);

				//根据申请编码
				int count = jzBaseInfoDao.count(query);

				if(count > 0){

					errorMsg.append("申请编码对应的数据已存在，不能重复导入.");
				}
			}

			//校验营销员编号不能为空
			String employeeId = entity.getAsString("employeeId");

			if(StringUtils.isBlank(employeeId)){

				errorMsg.append("营销员编号不能为空.");
			}

			//校验网点编码是否在系统中存在
			String bankCode = entity.getAsString("bankCode");

			if(StringUtils.isBlank(bankCode)){

				errorMsg.append("网点代码不能为空.");
			}else{

				BankInfo bankInfo = bankInfoDao.getBankInfoByBankCode(bankCode);

				if(bankInfo == null){

					errorMsg.append("网点代码在系统中不存在.");
				}else{

					jzBaseInfo.setBankInfoId(bankInfo.getId());
				}
			}

			//校验审批通过时间不能为空
			String passDate = entity.getAsString("passDate");

			if(StringUtils.isBlank(passDate)){

				errorMsg.append("审批通过时间不能为空");
			}


			//没有错误消息的话就直接添加到数据库中
			if(errorMsg.length() <= 0){
				try {
					//获取当前登陆用户的用户名
					UserDetails userDetails = (UserDetails)SecurityContextHolder
							.getContext()
							.getAuthentication()
							.getPrincipal();

					//根据用户名查询用户信息
					User user = userDao.findByLogin(userDetails.getUsername());

					if(user != null){
						//设置操作人
						jzBaseInfo.setOperater(user.getId());
					}

					//如果没有错误信息就保存到数据库中
					int save = jzBaseInfoDao.save(jzBaseInfo);

					if(save <= 0){
						errorMsg.append("保存数据失败.");
					}
				} catch (Exception e) {
					logger.error("=====ExcelManageService.validateJzBaseData Exception : " + e);
					errorMsg.append("保存数据失败.");
				}
			}
		}
		logger.info("=====ExcelManageService.validateJzBaseData End=====");
		return errorMsg;
	}
	/**
	 * 导入数据校验
	 * @param entity 每一行数据map集合 key value结构,key根据key值调用不同的方法进行校验
	 * @return 错误消息：为空校验通过，不为空校验失败
	 */
	private StringBuilder validateImportExcelData(BaseDto entity,String key){
		logger.info("=====ExcelMangeService.validateImportExcelData Begin");
		logger.info("ExcelMangeService.validateImportExcelData Param:key="+key);
		StringBuilder errorMsg = new StringBuilder();
		//根据key调用相应的方法进行校验
		switch(key){
			case "fqt_base_data": // 分期通基础数据导入校验
				errorMsg = this.validateFqtBaseData(entity);
				break;
			case "fqt_kh_data": // 分期通卡号数据导入校验
				errorMsg = this.validateFqtKHData(entity);
				break;
			case "fqt_kyed_data": // 分期通剩余可用额度数据导入校验
				errorMsg = this.validateFqtKYEDData(entity);
				break;
			case "jz_base_data": // 家装分期基础数据导入校验
				errorMsg = this.validateJzBaseData(entity);
				break;
			case "jz_kh_data": // 家装分期卡号数据导入校验
				errorMsg = this.validateJzKhData(entity);
				break;
			case "jz_te_data": // 家装分期调额数据导入校验
				errorMsg = this.validateJzTeData(entity);
				break;
			case "jz_kyed_data": // 家装分期剩余可用额度数据导入校验
				errorMsg = this.validateJzKyedData(entity);
				break;
		}
		logger.info("ExcelMangeService.validateImportExcelData errorMsg = " + errorMsg);
		logger.info("=====ExcelMangeService.validateImportExcelData End=====");

		return errorMsg;
	}

}