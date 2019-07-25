package com.zycx.system.sys.controller;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import com.zycx.system.common.base.controller.GenericController;
import com.zycx.system.common.base.service.GenericService;

import com.zycx.system.sys.entity.JzKyedInfo;
import com.zycx.system.sys.entity.QueryJzKyedInfo;
import com.zycx.system.sys.service.JzKyedInfoService;

/**
 *@author gly
 **/
@Controller
@RequestMapping("/jzKyedInfo")
public class JzKyedInfoController extends GenericController<JzKyedInfo, QueryJzKyedInfo> {
	@Inject
	private JzKyedInfoService jzKyedInfoService;
	@Override
	protected GenericService<JzKyedInfo, QueryJzKyedInfo> getService() {
		return jzKyedInfoService;
	}
}