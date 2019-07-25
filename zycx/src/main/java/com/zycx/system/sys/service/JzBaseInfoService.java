package com.zycx.system.sys.service;

import com.zycx.system.common.base.entity.Page;
import com.zycx.system.sys.config.ServiceConfig;
import com.zycx.system.sys.dao.JzKyedInfoDao;
import com.zycx.system.sys.entity.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import com.zycx.system.common.base.service.GenericService;
import com.zycx.system.common.base.dao.GenericDao;

import com.zycx.system.sys.dao.JzBaseInfoDao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *@author gly
 **/
@Service("jzBaseInfoService")
@Transactional(rollbackFor={Exception.class})
public class JzBaseInfoService extends GenericService<JzBaseInfo, QueryJzBaseInfo> {
	private static final Logger logger = LoggerFactory.getLogger(JzBaseInfoService.class);

	@Autowired
	@SuppressWarnings("SpringJavaAutowiringInspection")
	private JzBaseInfoDao jzBaseInfoDao;
	@Autowired
	private JzKyedInfoDao jzKyedInfoDao;
	@Override
	protected GenericDao<JzBaseInfo, QueryJzBaseInfo> getDao() {
		return jzBaseInfoDao;
	}


	/**
	 * 重写父类中的修改方法:暂时没问题，先不重写
	 * @param entity 修改对象
	 * @return
	 */
	//@Override
	public boolean oldUpdate(JzBaseInfo entity)throws Exception{

		//获取修改之前的数据
		JzBaseInfo jzBaseInfo = jzBaseInfoDao.get(entity);

		//获取修改之前的剩余可用额度
		BigDecimal beforeSurplusMoney = jzBaseInfo.getSecondTeMoney();
		//获取修改之后的剩余可用额度
		BigDecimal secondTeMoney = entity.getSecondTeMoney();

		if(beforeSurplusMoney != null && secondTeMoney != null){

			//如果修改之前的剩余可用额度和修改之后的剩余可用额度不相等，就插入一条
			if(beforeSurplusMoney.compareTo(secondTeMoney) != 0){


			}
		}else if(beforeSurplusMoney == null && secondTeMoney != null){
			/*
			 * 如果修改之前没有剩余可用额度
			 * 修改之后有剩余可用额度
			 * 那么直接插入一条剩余可用额度
			 */
			this.saveJzKyedInfo(entity);
		}

		return false;
	}

	/**
	 * 保存家装剩余可用额度数据
	 * @param entity
	 * @throws Exception
	 */
	private void saveJzKyedInfo(JzBaseInfo entity)throws Exception{

		if(entity != null){
			JzKyedInfo jzKyedInfo = new JzKyedInfo();
			Date nowDate = new Date();

			jzKyedInfo.setJzId(entity.getId());
			jzKyedInfo.setCurrentExpendMoney(new BigDecimal(0+""));
			jzKyedInfo.setImportTime(nowDate);
			jzKyedInfo.setOperationTime(nowDate);
			jzKyedInfo.setExpiryDate(entity.getEdEndTime());
			jzKyedInfo.setSurplusMoney(entity.getSurplusMoney());

			int save = jzKyedInfoDao.save(jzKyedInfo);

			if(save <= 0){

				throw new RuntimeException();
			}
		}
	}
	/**
	 * 处理统计数据并分页
	 * @param queryModel 查询实体类
	 * @return
	 */
	public Page loadReportData(QueryJzBaseInfo queryModel){
		logger.info("=====JzBaseInfoService.loadReportData Begin=====");
		logger.info("JzBaseInfoService.loadReportData Param:passMonth = " + queryModel.getPassMonth()+",reportType = " + queryModel.getReportType());
		//如果没有选择支用月份那么就默认使用审批通过月份
		if(StringUtils.isBlank(queryModel.getZyMonth())){

			queryModel.setZyMonth(queryModel.getPassMonth());
		}
		//根据查询条件查询数据库
		List<JzBaseInfo> list =  new ArrayList<JzBaseInfo>();

		int count = 0;
		//查询数据库获取分页数据
		Page page = new Page();
		switch (queryModel.getReportType()){
			//员工
			case "yg":
				list =  jzBaseInfoDao.findJzYgReportData(queryModel);
				count = jzBaseInfoDao.findJzYgReportDataCount(queryModel);
				break;
			//网点
			case "wd":
				list =  jzBaseInfoDao.findJzWdReportData(queryModel);
				count = jzBaseInfoDao.findJzWdReportDataCount(queryModel);
				break;
		}
		logger.info("=====JzBaseInfoService.loadReportData End=====");

		return new Page(list, count);
	}
	/**
	 * 分页查询家装分期基础数据
	 * @param queryModel 查询条件
	 *  */
	@Override
	public Page findByPage(QueryJzBaseInfo queryModel){
		logger.info("=====JzBaseInfoService.findByPage Begin=====");
		//查询数据库获取分页数据
		List<JzBaseInfo> list =  getDao().findByPage(queryModel);
		int count = getDao().count(queryModel);

		if(list != null && list.size() > 0){
			//对数据进行处理
			for(JzBaseInfo jzBaseInfo : list){

				//1.处理身份证号码
				String identityCode = ServiceConfig.disposeIdentityCode(jzBaseInfo.getIdentityCode(), 4, 16, "**********");
				if(StringUtils.isNotBlank(identityCode)){

					jzBaseInfo.setIdentityCode(identityCode);
				}

				//2.处理手机号码
				String phoneNumber = ServiceConfig.disposePhoneNumber(jzBaseInfo.getContactNumber(), 3, 8, "*****");
				if(StringUtils.isNotBlank(phoneNumber)){

					jzBaseInfo.setContactNumber(phoneNumber);
				}

				//3.审批中收
				int fqqs = jzBaseInfo.getJzFqQs();//分期期数
				BigDecimal fqRate = jzBaseInfo.getJzFqRate();//分期费率：百分比

				BigDecimal firstTeMoney = jzBaseInfo.getFirstTeMoney();//获取一次调额金额
				BigDecimal secondTeMoney = jzBaseInfo.getSecondTeMoney();//获取二次调额金额

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

				jzBaseInfo.setSyzsMoney(ServiceConfig.findZS(fqqs, fqRate, surplusMoney));
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
		}
		logger.info("=====JzBaseInfoService.findByPage End=====");

		return new Page(list, count);
	}
}