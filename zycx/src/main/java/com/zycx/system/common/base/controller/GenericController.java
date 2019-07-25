package com.zycx.system.common.base.controller;

import com.zycx.system.common.base.constant.SystemStaticConst;
import com.zycx.system.common.base.entity.Page;
import com.zycx.system.common.base.entity.QueryBase;
import com.zycx.system.common.base.service.GenericService;
import com.zycx.system.common.util.json.JsonHelper;
import com.zycx.system.sys.controller.ExcelManageController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public abstract class GenericController<T, Q extends QueryBase> {
	private static final Logger logger = LoggerFactory.getLogger(GenericController.class);

	/**
	 * 抽象方法
	 */
	protected abstract GenericService<T, Q> getService();

	/**
	 * 添加页面路径
	 */
	public final static String ADDPAGE = "/add";
	/**
	 * 修改页面路径
	 */
	public final static String UPDATEPAGE = "/update";

	/**
	 * Controller基路径
	 * */
	protected String basePath;

	/**抽象方法，获取页面基路径
	 * @throws Exception */
	protected String getPageBaseRoot() throws Exception{
		logger.info("=====GenericController.getPageBaseRoot Begin=====");
		if(basePath==null){
			basePath = this.getClass().getName();
			Pattern p = Pattern.compile(".[a-z|A-z]+.controller.[a-z|A-z]+Controller");
			Matcher m = p.matcher(basePath);
			if(m.find()){
				basePath = m.group();
				basePath = basePath.substring(1, basePath.length()-10);
				basePath = basePath.replace(".", "/");
				basePath = basePath.replace("/controller/", "/");
				basePath = basePath.substring(0,basePath.lastIndexOf("/")+1)+ toFirstCharLowerCase(basePath.substring(basePath.lastIndexOf("/")+1));
			}else{

				logger.error("GenericController.getPageBaseRoot 获取页面基路径失败");
				throw new Exception("获取页面基路径失败");
			}
		}
		logger.info("=====GenericController.getPageBaseRoot End=====");

		return basePath;
	}


	/**
	 * 功能描述：直接跳转到更新数据的页面
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/updatePage",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public String updatePage(T entity,Model model) throws Exception{
		entity = getService().get(entity);
		model.addAttribute("entity",entity);
		return getPageBaseRoot()+UPDATEPAGE;
	}

	/** 跳转到添加对象页面
	 * @throws Exception */
	@RequestMapping(value="/addPage")
	public String addPage() throws Exception{
		return getPageBaseRoot()+ADDPAGE;
	}

	/**
	 * 功能描述：保存数据
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/save",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String,Object> save(T entity) throws Exception{
		boolean success = getService().save(entity);
		Map<String,Object> result = new HashMap<String, Object>();
		if(success==true){
			result.put(SystemStaticConst.RESULT, SystemStaticConst.SUCCESS);
			result.put(SystemStaticConst.MSG,SystemStaticConst.ADD_SUCCESS_MSG);
			result.put("entity",entity);
		}else{
			result.put(SystemStaticConst.RESULT,SystemStaticConst.FAIL);
			result.put(SystemStaticConst.MSG,SystemStaticConst.ADD_FAIL_MSG);
		}
		return result;
	}

	/**
	 * 功能描述：更新数据
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/update",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String,Object> update(T entity)  throws Exception{
		boolean success  = getService().update(entity);
		Map<String,Object> result = new HashMap<String, Object>();
		if(success==true){
			result.put(SystemStaticConst.RESULT,SystemStaticConst.SUCCESS);
			result.put(SystemStaticConst.MSG,SystemStaticConst.UPDATE_SUCCESS_MSG);
			result.put("entity",entity);
		}else{
			result.put(SystemStaticConst.RESULT,SystemStaticConst.FAIL);
			result.put(SystemStaticConst.MSG,SystemStaticConst.UPDATE_FAIL_MSG);
		}
		return result;
	}

	/**
	 * 功能描述：实现批量删除数据字典的记录
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/remove",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String,Object> remove(T entity) throws Exception{
		Map<String,Object> result = new HashMap<String, Object>();
		getService().delete(entity);
		result.put(SystemStaticConst.RESULT,SystemStaticConst.SUCCESS);
		result.put(SystemStaticConst.MSG,SystemStaticConst.DELETE_SUCCESS_MSG);
		return result;
	}


	/**
	 * 功能描述：实现批量删除的记录
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/removeBath",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String,Object> removeBath(String json) throws Exception{
		Map<String,Object> result = new HashMap<String, Object>();
		getService().removeBath((List<T>) JsonHelper.toList(json,(Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]));
		result.put(SystemStaticConst.RESULT,SystemStaticConst.SUCCESS);
		result.put(SystemStaticConst.MSG,SystemStaticConst.DELETE_SUCCESS_MSG);
		return result;
	}

	/**
	 * 功能描述：获取分页的数据
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/list",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String,Object> list(Q entity){
		Map<String,Object> result = new HashMap<String, Object>();
		Page page = getService().findByPage(entity);
		result.put("totalCount",page.getTotal());
		result.put("result",page.getRows());
		return result;
	}

	/**
	 * 功能描述：判断当前的元素是否已经存在
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/isExist",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String,Object> isExist(Q entity){
		Map<String,Object> result = new HashMap<String, Object>();
		if(getService().query(entity).size()>0){
			result.put("valid",false);
		}else{
			result.put("valid",true);
		}
		return result;
	}

	/**
	 * 将首字母变小写
	 * @param str
	 * @return
	 */
	private static String toFirstCharLowerCase(String str){
		char[]  columnCharArr = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < columnCharArr.length; i++) {
			char cur = columnCharArr[i];
			if(i==0){
				sb.append(Character.toLowerCase(cur));
			}else{
				sb.append(cur);
			}
		}
		return sb.toString();
	}

}
