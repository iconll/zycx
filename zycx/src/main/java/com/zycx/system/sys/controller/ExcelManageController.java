package com.zycx.system.sys.controller;

import com.zycx.system.common.base.constant.SystemStaticConst;
import com.zycx.system.common.util.ResponseData;
import com.zycx.system.sys.config.ExcelConfig;
import com.zycx.system.sys.entity.QueryFqtBaseInfo;
import com.zycx.system.sys.entity.QueryJzBaseInfo;
import com.zycx.system.sys.service.ExcelManageService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 分期通基础数据管理功能
 * @author gly
 **/
@Controller
@RequestMapping("/excelManage")
public class ExcelManageController {
	@Inject
	private ExcelManageService excelManageService;

	private static final Logger logger = LoggerFactory.getLogger(ExcelManageController.class);

	@RequestMapping(value = "/download")
	public void download(HttpServletRequest request,HttpServletResponse response) throws IOException {

	}
	/**
	 * 家装分期数据导出
	 */
	@RequestMapping(value = "exportJzExcel")
	public void exportJzExcel(QueryJzBaseInfo entity, HttpServletRequest request, HttpServletResponse response) {
		logger.info("=====ExcelManageController.exportJzExcel Begin=====");
		//根据参数条件获取导出Excel文件名
		String fileName = ExcelConfig.getExportExcelFileName(entity.getKey(),entity.getValidDays(),entity.getPassMonth());

		try {
			//获取处理后的文件名
			String newFileName = ExcelConfig.findDownloadExcelFileName(request,fileName);

			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-Disposition", "attachment;filename="+ newFileName + ".xls");

			logger.info("ExcelManageController.exportJzExcel fileName = " + fileName + ",newFileName = " + newFileName);

			excelManageService.exportJzExcel(entity,fileName,response);
		} catch (Exception e) {
			logger.error("ExcelManageController.exportJzExcel Exception:" + e);
		}
		logger.info("=====ExcelManageController.exportJzExcel End=====");
	}
	/**
	 * 分期通数据导出
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "exportExcel")
	public void exportExcel(QueryFqtBaseInfo entity, HttpServletRequest request, HttpServletResponse response) {
		logger.info("=====ExcelManageController.exportExcel Begin=====");
		//根据参数条件获取导出Excel文件名
		String fileName = ExcelConfig.getExportExcelFileName(entity.getKey(),entity.getValidDays(),entity.getTeMonth());

		try {
			//获取处理后的文件名
			String newFileName = ExcelConfig.findDownloadExcelFileName(request,fileName);

			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-Disposition", "attachment;filename="+ newFileName + ".xls");

			logger.info("ExcelManageController.exportExcel fileName = " + fileName + ",newFileName = " + newFileName);

			excelManageService.exportExcel(entity,fileName,response);
		} catch (Exception e) {
			logger.error("ExcelManageController.exportExcel Exception:" + e);
		}
		logger.info("=====ExcelManageController.exportExcel End=====");
	}
	/*
	  由于前端fileInput提交数据文件其中name名为前台input文件输入框的id值
	  如果同一个页面有多个input文件框，那么id值不能重复
	  所以暂时针对不同文件框提交对应多个映射方法接收对应的文件参数，调用同一个方法进行业务处理
	*/
	/**
	 * 家装分期剩余可用额度数据导入
	 * @param file 导入的Excel文件
	 * @return
	 */
	@RequestMapping(value = "importJzKyedData")
	@ResponseBody
	public Map<String,Object> importJzKyedData(@RequestParam("kyedDataImport") MultipartFile file,@RequestParam("key") String key) {
		logger.info("=====ExcelManageController.importJzKyedData Begin=====");
		return importExcelData(file,key);
	}
	/**
	 * 家装分期调额数据导入
	 * @param file 导入的Excel文件
	 * @return
	 */
	@RequestMapping(value = "importJzTeData")
	@ResponseBody
	public Map<String,Object> importJzTeData(@RequestParam("teDataImport") MultipartFile file,@RequestParam("key") String key) {
		return importExcelData(file,key);
	}
	/**
	 * 家装分期卡号数据导入
	 * @param file 导入的Excel文件
	 * @return
	 */
	@RequestMapping(value = "importJzKHData")
	@ResponseBody
	public Map<String,Object> importJzKHData(@RequestParam("khDataImport") MultipartFile file,@RequestParam("key") String key) {
		return importExcelData(file,key);
	}
	/**
	 * 家装分期基础数据导入
	 * @param file 导入的Excel文件
	 * @return
	 */
	@RequestMapping(value = "importJzBaseData")
	@ResponseBody
	public Map<String,Object> importJzBaseData(@RequestParam("baseDataImport") MultipartFile file,@RequestParam("key") String key) {
		return importExcelData(file,key);
	}
	/**
	 * 分期通剩余可用额度数据导入
	 * @param file 导入的Excel文件
	 * @return
	 */
	@RequestMapping(value = "importFqtKYEDData")
	@ResponseBody
	public Map<String,Object> importFqtKYEDData(@RequestParam("kyedDataImport") MultipartFile file,@RequestParam("key") String key) {
		return importExcelData(file,key);
	}
	/**
	 * 分期通卡号数据导入
	 * @param file 导入的Excel文件
	 * @return
	 */
	@RequestMapping(value = "importFqtKHData")
	@ResponseBody
	public Map<String,Object> importFqtKHData(@RequestParam("khDataImport") MultipartFile file,@RequestParam("key") String key) {
		return importExcelData(file,key);
	}
	/**
	 * 分期通基础数据导入
	 * @param file 导入的Excel文件
	 * @return
	 */
	@RequestMapping(value = "importFqtBaseData")
	@ResponseBody
	public Map<String,Object> importFqtBaseData(@RequestParam("baseDataImport") MultipartFile file,@RequestParam("key") String key) {
		return importExcelData(file,key);
	}

	/**
	 * 处理Excel导入
	 * @param file 前端上传的Excel文件
	 * @param key 模块Key值
	 * @return
	 */
	private Map<String,Object> importExcelData(MultipartFile file,String key){
		logger.info("=====ExcelManageController.importExcelData Begin=====");
		Map<String,Object> result = new HashMap<String, Object>(16);

		if (file == null || file.isEmpty()) {
			logger.info("ExcelManageController.importExcelData file is Null or file is Empty");
			return ResponseData.returnResponseData(SystemStaticConst.FAIL,"上传文件为空，请检查上传的文件。",null);
		}
		// 获取文件名
		String fileName = file.getOriginalFilename();
		logger.info("ExcelManageController.importExcelData 上传的文件名为：" + fileName);
		// 获取文件的后缀名
		String suffixName = fileName.substring(fileName.lastIndexOf("."));

		if(StringUtils.isBlank(suffixName) || !".xls".equals(suffixName)){

			return ResponseData.returnResponseData(SystemStaticConst.FAIL,"文件类型错误，只支持上传xls类型的Excel。",null);
		}

		logger.info("ExcelManageController.importExcelData 上传的后缀名为：" + suffixName);

		try {

			//处理上传文件
			result = excelManageService.disposeImportExcelData(file.getInputStream(),key);
		} catch (Exception e) {
			result = ResponseData.returnResponseData(SystemStaticConst.FAIL,"操作失败",null);
			logger.info("ExcelManageController.importExcelData Exception:" + e);

		}

		logger.info("=====ExcelManageController.importExcelData End=====");

		return result;
	}

}