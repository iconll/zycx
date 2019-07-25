package com.zycx.system.common.config;

import com.zycx.system.common.base.constant.SystemStaticConst;
import com.zycx.system.common.util.ResponseData;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author 统一异常处理
 * @date 2019年5月7日
 */
@ResponseBody
@ControllerAdvice
public class MyControllerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(MyControllerAdvice.class);

    /**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public Map<String,Object> errorHandler(Exception ex) {
        logger.error("MyControllerAdvice.errorHandler Exception:",ex);
        String msg = "";
        if(StringUtils.isBlank(ex.getMessage())){

            msg = "操作失败！";
        }else{
            msg = ex.getMessage();
        }
        return ResponseData.returnResponseData(SystemStaticConst.FAIL,msg ,null);
    }
}
