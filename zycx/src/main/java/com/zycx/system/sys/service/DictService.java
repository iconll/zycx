package com.zycx.system.sys.service;


import com.zycx.system.common.base.dao.GenericDao;
import com.zycx.system.common.base.service.GenericService;
import com.zycx.system.sys.dao.DictDao;
import com.zycx.system.sys.entity.Dict;
import com.zycx.system.sys.entity.QueryDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *@author linzf
 **/
@Service("dictService")
@Transactional(rollbackFor={IllegalArgumentException.class})
public class DictService extends GenericService<Dict, QueryDict> {
	@Autowired
	@SuppressWarnings("SpringJavaAutowiringInspection")
	private DictDao dictDao;
	@Override
	protected GenericDao<Dict, QueryDict> getDao() {
		return dictDao;
	}

	@Override
	@Cacheable(value = "dict", key = "'dict'.concat(#entity.id)", unless = "#result eq null")
	public Dict get(Dict entity) {
		return super.get(entity);
	}

	@Override
	@CachePut(value = "dict", key = "'dict'.concat(#entity.id)", unless = "#result eq null")
	public boolean update(Dict entity) throws Exception {
		return super.update(entity);
	}

	@Override
	@CacheEvict(value = "dict", key = "'dict'.concat(#entity.id)",beforeInvocation=true)
	public boolean delete(Dict entity) throws Exception {
		return super.delete(entity);
	}
}