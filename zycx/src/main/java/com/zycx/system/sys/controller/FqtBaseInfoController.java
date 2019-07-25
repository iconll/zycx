package com.zycx.system.sys.controller;

import javax.inject.Inject;

import com.zycx.system.common.base.controller.GenericController;
import com.zycx.system.common.base.entity.Page;
import com.zycx.system.common.base.service.GenericService;
import com.zycx.system.sys.entity.FqtBaseInfo;
import com.zycx.system.sys.entity.QueryFqtBaseInfo;
import com.zycx.system.sys.service.FqtBaseInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分期通基础数据管理功能
 * @author gly
 **/
@Controller
@RequestMapping("/fqtBaseInfo")
public class FqtBaseInfoController extends GenericController<FqtBaseInfo, QueryFqtBaseInfo> {
	@Inject
	private FqtBaseInfoService fqtBaseInfoService;
	@Override
	protected GenericService<FqtBaseInfo, QueryFqtBaseInfo> getService() {
		return fqtBaseInfoService;
	}

	private static final Logger logger = LoggerFactory.getLogger(FqtBaseInfoController.class);

	/**
	 * 分期通额度有效预警（首页）
	 * @param entity 查询实体类
	 * @return
	 */
	@RequestMapping(value = "loadHomeFqtYjData")
	@ResponseBody
	public Map<String,Object> loadHomeFqtYjData(QueryFqtBaseInfo entity){
		logger.info("=====FqtBaseInfoController.loadHomeFqtYjData Begin=====");
		entity.setOrder("ASC");
		entity.setSort("expiry_date");
		Map<String,Object> result = new HashMap<String, Object>(16);
		try{
			Page page = fqtBaseInfoService.findByPage(entity);

			result.put("totalCount",page.getTotal());
			result.put("result",page.getRows());
		}catch(Exception e){
			logger.error("FqtBaseInfoController.loadHomeFqtYjData Exception:" + e);
		}
		logger.info("=====FqtBaseInfoController.loadHomeFqtYjData End=====");
		return result;
	}
	/**
	 * 分期通基础数据统计报表
	 * @param entity 查询实体类
	 * @return
	 */
	@RequestMapping(value = "loadReportData")
	@ResponseBody
	public Map<String,Object> loadReportData(QueryFqtBaseInfo entity){
		logger.info("=====FqtBaseInfoController.loadReportData Begin=====");
		Map<String,Object> result = new HashMap<String, Object>(16);

		try{
			Page page = fqtBaseInfoService.loadReportData(entity);

			result.put("totalCount",page.getTotal());
			result.put("result",page.getRows());
		}catch(Exception e){
			logger.error("FqtBaseInfoController.loadReportData Exception:" + e);
		}

		logger.info("=====FqtBaseInfoController.loadReportData End=====");
		return result;
	}
}