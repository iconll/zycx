package com.zycx.system.sys.controller;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import com.zycx.system.common.base.controller.GenericController;
import com.zycx.system.common.base.service.GenericService;

import com.zycx.system.sys.entity.FqtKyedInfo;
import com.zycx.system.sys.entity.QueryFqtKyedInfo;
import com.zycx.system.sys.service.FqtKyedInfoService;

/**
 *@author gly
 **/
@Controller
@RequestMapping("/fqtKyedInfo")
public class FqtKyedInfoController extends GenericController<FqtKyedInfo, QueryFqtKyedInfo> {
	@Inject
	private FqtKyedInfoService fqtKyedInfoService;
	@Override
	protected GenericService<FqtKyedInfo, QueryFqtKyedInfo> getService() {
		return fqtKyedInfoService;
	}
}