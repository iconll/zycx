package com.zycx.system.sys.controller;

import javax.inject.Inject;

import com.zycx.system.common.base.entity.Page;
import com.zycx.system.sys.entity.QueryFqtBaseInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import com.zycx.system.common.base.controller.GenericController;
import com.zycx.system.common.base.service.GenericService;

import com.zycx.system.sys.entity.JzBaseInfo;
import com.zycx.system.sys.entity.QueryJzBaseInfo;
import com.zycx.system.sys.service.JzBaseInfoService;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 *@author gly
 **/
@Controller
@RequestMapping("/jzBaseInfo")
public class JzBaseInfoController extends GenericController<JzBaseInfo, QueryJzBaseInfo> {
	@Inject
	private JzBaseInfoService jzBaseInfoService;
	@Override
	protected GenericService<JzBaseInfo, QueryJzBaseInfo> getService() {
		return jzBaseInfoService;
	}

	private static final Logger logger = LoggerFactory.getLogger(JzBaseInfoController.class);

	/**
	 * 家装分期额度有效预警（首页）
	 * @param entity 查询实体类
	 * @return
	 */
	@RequestMapping(value = "loadHomeJzYjData")
	@ResponseBody
	public Map<String,Object> loadHomeJzYjData(QueryJzBaseInfo entity){
		logger.info("=====JzBaseInfoController.loadHomeJzYjData Begin=====");
		entity.setOrder("ASC");
		entity.setSort("ed_end_time");
		Map<String,Object> result = new HashMap<String, Object>(16);
		try{

			Page page = jzBaseInfoService.findByPage(entity);

			result.put("totalCount",page.getTotal());
			result.put("result",page.getRows());
		}catch(Exception e){
			logger.error("JzBaseInfoController.loadHomeJzYjData Exception:" + e);
		}

		logger.info("=====JzBaseInfoController.loadHomeJzYjData End=====");
		return result;
	}
	/**
	 * 家装分期基础数据统计报表
	 * @param entity 查询实体类
	 * @return
	 */
	@RequestMapping(value = "loadReportData")
	@ResponseBody
	public Map<String,Object> loadReportData(QueryJzBaseInfo entity){
		logger.info("=====JzBaseInfoController.loadReportData Begin=====");
		Map<String,Object> result = new HashMap<String, Object>(16);

		try{
			Page page = jzBaseInfoService.loadReportData(entity);

			result.put("totalCount",page.getTotal());
			result.put("result",page.getRows());
		}catch(Exception e){
			logger.error("JzBaseInfoController.loadReportData Exception:" + e);
		}

		logger.info("=====JzBaseInfoController.loadReportData End=====");
		return result;
	}
}