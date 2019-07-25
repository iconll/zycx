package com.zycx.system.sys.service;

import com.zycx.system.common.base.entity.Page;
import com.zycx.system.sys.config.ServiceConfig;
import com.zycx.system.common.base.dao.GenericDao;
import com.zycx.system.common.base.service.GenericService;
import com.zycx.system.sys.controller.FqtBaseInfoController;
import com.zycx.system.sys.dao.FqtKyedInfoDao;
import com.zycx.system.sys.entity.FqtBaseInfo;
import com.zycx.system.sys.entity.FqtKyedInfo;
import com.zycx.system.sys.entity.QueryFqtBaseInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import com.zycx.system.sys.dao.FqtBaseInfoDao;

import java.math.BigDecimal;
import java.util.*;

/**
 *@author gly
 **/
@Service("fqtBaseInfoService")
@Transactional(rollbackFor={Exception.class})
public class FqtBaseInfoService extends GenericService<FqtBaseInfo, QueryFqtBaseInfo> {
	private static final Logger logger = LoggerFactory.getLogger(FqtBaseInfoService.class);

	@Autowired
	@SuppressWarnings("SpringJavaAutowiringInspection")
	private FqtBaseInfoDao fqtBaseInfoDao;
	@Autowired
	private FqtKyedInfoDao fqtKyedInfoDao;
	@Override
	protected GenericDao<FqtBaseInfo, QueryFqtBaseInfo> getDao() {
		return fqtBaseInfoDao;
	}

	/**
	 * 重写父类中的修改方法
	 * @param entity 修改对象
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean update(FqtBaseInfo entity) throws Exception{

		int update = fqtBaseInfoDao.update(entity);

		if(update > 0){

			//判断剩余可用额度是否发生改变
			BigDecimal surplusMoney = entity.getSurplusMoney();

			String lastSurplusMoney = fqtKyedInfoDao.findLastSurplusMoneyByFqtBaseInfoId(entity.getId());
			//如果发生改变就修改到剩余可用额度表中
			if(StringUtils.isNotBlank(lastSurplusMoney)){
				BigDecimal beforeSurplusMoney = new BigDecimal(lastSurplusMoney);

				if(surplusMoney != null && beforeSurplusMoney != null){

					//如果剩余可用额度不相等,就将新的剩余可用额度保存到数据库中
					if(surplusMoney.compareTo(beforeSurplusMoney) != 0){

						this.saveFqtKyedInfo(entity);
					}
				}
			}else{//如果没有导入过剩余可用额度，就直接添加一条
				this.saveFqtKyedInfo(entity);
			}
			return true;
		}else{

			return false;
		}
	}

	/**
	 * 保存剩余可用额度数据
	 * @param entity
	 */
	private void saveFqtKyedInfo(FqtBaseInfo entity)throws Exception{
		BigDecimal surplusMoney = entity.getSurplusMoney();

		FqtKyedInfo fqtKyedInfo = new FqtKyedInfo();
		Date nowDate = new Date();

		fqtKyedInfo.setFqtBaseInfoId(entity.getId());
		fqtKyedInfo.setCurrentExpendMoney(new BigDecimal(0+""));
		fqtKyedInfo.setImportTime(nowDate);
		fqtKyedInfo.setExpiryDate(entity.getExpiryDate());
		fqtKyedInfo.setOperationTime(nowDate);
		fqtKyedInfo.setSurplusMoney(surplusMoney);

		int save = fqtKyedInfoDao.save(fqtKyedInfo);

		if(save <= 0){
			throw new RuntimeException();
		}
	}
	/**
	 * 重写父类中的get方法
	 * @param entity
	 * @return
	 */
	@Override
	public FqtBaseInfo get(FqtBaseInfo entity){

		FqtBaseInfo fqtBaseInfo = fqtBaseInfoDao.get(entity);

		if(fqtBaseInfo != null){
			//查询剩余可用额度
			String lastSurplusMoney = fqtKyedInfoDao.findLastSurplusMoneyByFqtBaseInfoId(entity.getId());

			//获取调额金额
			BigDecimal teMoney = fqtBaseInfo.getTeMoney();

			if(StringUtils.isNotBlank(lastSurplusMoney)){
				BigDecimal surplusMoney = new BigDecimal(lastSurplusMoney);

				//计算支用金额
				if(surplusMoney != null && teMoney != null){

					//4.计算支用金额:调额金额-剩余可用额度
					BigDecimal disburseMoney = teMoney.subtract(surplusMoney);

					fqtBaseInfo.setSurplusMoney(surplusMoney);
					fqtBaseInfo.setDisburseMoney(disburseMoney);
				}
			}else{
				//剩余可用额度等于调额金额
				fqtBaseInfo.setSurplusMoney(teMoney);
				//支用金额等于0
				fqtBaseInfo.setDisburseMoney(new BigDecimal(0+""));
			}

		}
		return fqtBaseInfo;
	}
	/**
	 * 处理统计数据并分页
	 * @param queryModel 查询实体类
	 * @return
	 */
	public Page loadReportData(QueryFqtBaseInfo queryModel){
		logger.info("=====FqtBaseInfoService.loadReportData Begin=====");
		logger.info("FqtBaseInfoService.loadReportData Param:teMonth = " + queryModel.getTeMonth()+",reportType = " + queryModel.getReportType());
		//如果没有选择支用月份那么就默认使用调额月份
		if(StringUtils.isBlank(queryModel.getZyMonth())){

			queryModel.setZyMonth(queryModel.getTeMonth());
		}
		//根据查询条件查询数据库
		List<FqtBaseInfo> list =  new ArrayList<FqtBaseInfo>();

		int count = 0;
		//查询数据库获取分页数据
		Page page = new Page();
		switch (queryModel.getReportType()){
			//员工
			case "yg":
				list =  fqtBaseInfoDao.findYgReportData(queryModel);
				count = fqtBaseInfoDao.findYgReportDataCount(queryModel);
				break;
			//网点
			case "wd":
				list =  fqtBaseInfoDao.findWdReportData(queryModel);
				count = fqtBaseInfoDao.findWdReportDataCount(queryModel);
				break;
		}
		logger.info("=====FqtBaseInfoService.loadReportData End=====");

		return new Page(list, count);
	}
	/**
	 * 获取报表类型为客户的数据 暂未使用，先留着
	 * @param queryModel 查询实体类
	 */
	public Page findKhReportData(QueryFqtBaseInfo queryModel){
		//设置额度有效期过滤掉已失效的数据
		List<FqtBaseInfo> list =  getDao().findByPage(queryModel);
		int count = getDao().count(queryModel);

		if(list != null && list.size() > 0){
			for(FqtBaseInfo fqtBaseInfo : list){

				//1.处理身份证号码
				String identityCode = ServiceConfig.disposeIdentityCode(fqtBaseInfo.getIdentityCode(), 4, 16, "**********");
				if(StringUtils.isNotBlank(identityCode)){

					fqtBaseInfo.setIdentityCode(identityCode);
				}

				//2.处理手机号码
				String phoneNumber = ServiceConfig.disposePhoneNumber(fqtBaseInfo.getContactNumber(), 3, 8, "*****");
				if(StringUtils.isNotBlank(phoneNumber)){

					fqtBaseInfo.setContactNumber(phoneNumber);
				}
				queryModel.setId(fqtBaseInfo.getId());
				//1.获取当月支用额度
				String dyzyMoney = fqtBaseInfoDao.findDyzyMoneyByBaseIdAndMonth(queryModel);

                //分期期数
				int fqqs = fqtBaseInfo.getFqqs();
                //分期费率：百分比
				BigDecimal fqRate = fqtBaseInfo.getFqRate();

				if(StringUtils.isNotBlank(dyzyMoney)){

					BigDecimal dyzyMoneyObj = new BigDecimal(dyzyMoney);

                    //设置当月支用金额
                    fqtBaseInfo.setDyzyMoney(dyzyMoneyObj);

                    //2.计算当月支用中收
                    BigDecimal zs = ServiceConfig.findZS(fqqs, fqRate, dyzyMoneyObj);

                    fqtBaseInfo.setDyzyzsMoney(zs);//设置当月支用中收

				}

				//获取剩余可用额度
				BigDecimal surplusMoney = fqtBaseInfo.getSurplusMoney();

				if(surplusMoney != null){
                    //3.计算剩余可用中收
					BigDecimal zs = ServiceConfig.findZS(fqqs, fqRate, surplusMoney);

                    //剩余中收
                    fqtBaseInfo.setSyzsMoney(zs);

					//获取调额金额
					BigDecimal teMoney = fqtBaseInfo.getTeMoney();

					if(teMoney != null){
						//计算调额中收
						BigDecimal tezs = ServiceConfig.findZS(fqqs, fqRate, teMoney);
						fqtBaseInfo.setTezsMoney(tezs);
						//4.计算支用金额:调额金额-剩余可用额度
						BigDecimal disburseMoney = teMoney.subtract(surplusMoney);

						if(disburseMoney != null){
                            //设置支用金额
                            fqtBaseInfo.setDisburseMoney(disburseMoney);
                            //5.计算支用中收
                            BigDecimal disburseZSMoney = ServiceConfig.findZS(fqqs, fqRate, disburseMoney);

                            if(disburseZSMoney != null){

                                //设置支用中收
                                fqtBaseInfo.setDisburseZSMoney(disburseZSMoney);

                                //6.计算支用率:支用金额/调额金额*100 (百分比)
                                BigDecimal zyRate = disburseMoney.divide(teMoney,2, BigDecimal.ROUND_HALF_EVEN).multiply(new BigDecimal(100+""));

                                //设置支用率
                                fqtBaseInfo.setZyRate(zyRate);
                            }
                        }
					}
				}
			}
		}

		return new Page(list, count);
	}
	/**
	 * 分页查询分期通数据
	 * @param queryModel 查询条件
	 *  */
	@Override
	public Page findByPage(QueryFqtBaseInfo queryModel){
		logger.info("=====FqtBaseInfoService.findByPage Begin=====");

		//查询数据库获取分页数据
		List<FqtBaseInfo> list =  getDao().findByPage(queryModel);
		int count = getDao().count(queryModel);

		if(list != null && list.size() > 0){
			//对数据进行处理
			for(FqtBaseInfo fqtBaseInfo : list){

				//1.处理身份证号码
				String identityCode = ServiceConfig.disposeIdentityCode(fqtBaseInfo.getIdentityCode(), 4, 16, "**********");
				if(StringUtils.isNotBlank(identityCode)){

					fqtBaseInfo.setIdentityCode(identityCode);
				}

				//2.处理手机号码
				String phoneNumber = ServiceConfig.disposePhoneNumber(fqtBaseInfo.getContactNumber(), 3, 8, "*****");
				if(StringUtils.isNotBlank(phoneNumber)){

					fqtBaseInfo.setContactNumber(phoneNumber);
				}

				//3.调额中收
				int fqqs = fqtBaseInfo.getFqqs();//分期期数
				BigDecimal fqRate = fqtBaseInfo.getFqRate();//分期费率：百分比
				BigDecimal teMoney = fqtBaseInfo.getTeMoney();//调额金额
				//计算调额中收
				BigDecimal tezs = ServiceConfig.findZS(fqqs, fqRate, teMoney);
				fqtBaseInfo.setTezsMoney(tezs);

				//4.查询剩余可用额度
				BigDecimal surplusMoney = fqtBaseInfo.getSurplusMoney();

				//如果还没导入剩余可用额度，那么就取调额金额作为剩余可用额度
				if(surplusMoney == null){
					surplusMoney = teMoney;
					fqtBaseInfo.setSurplusMoney(surplusMoney);
				}

				fqtBaseInfo.setSyzsMoney(ServiceConfig.findZS(fqqs, fqRate, surplusMoney));
				if(surplusMoney != null && teMoney != null){
					//判断如果剩余额度不为空，就计算支用额度和支用中收
					BigDecimal disburseMoney = teMoney.subtract(surplusMoney);
					//5.支用金额
					fqtBaseInfo.setDisburseMoney(disburseMoney);
					//6.支用中收
					BigDecimal disburseZSMoney = ServiceConfig.findZS(fqqs, fqRate, disburseMoney);
					fqtBaseInfo.setDisburseZSMoney(disburseZSMoney);
				}
			}
		}
		logger.info("=====FqtBaseInfoService.findByPage End=====");

		return new Page(list, count);
	}
}