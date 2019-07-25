package com.zycx.system.common.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.zycx.system.common.util.ApiResponse;
import com.zycx.system.common.util.IllegalStrFilterUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author: gly
 * @Description: 使用AOP调用接口打印性能日志以及接口报错之后记录错误日志
 * @Date: 2019/5/7
 */
//@Component
//@Aspect
public class InterfaceRequestErrrorAndPerformanceLog {

    public static final Logger logger = LoggerFactory.getLogger(InterfaceRequestErrrorAndPerformanceLog.class);


    @Pointcut("execution(* com.zycx.system.sys.controller.*.*.*(..))")
    public void pointCut(){}

    @Around("pointCut()")
    public ApiResponse handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable{
        Stopwatch stopwatch = Stopwatch.createStarted();

        ApiResponse apiResponse;
        try {
            logger.info("执行Controller开始: " + pjp.getSignature() + " 参数：" + Lists.newArrayList(pjp.getArgs()).toString());
            //处理入参特殊字符和sql注入攻击
            //this.checkRequestParam(pjp);
            apiResponse = (ApiResponse) pjp.proceed(pjp.getArgs());
            logger.info("执行Controller结束: " + pjp.getSignature() + "， 返回值：" + apiResponse.toString());
            logger.info("耗时：" + stopwatch.stop().elapsed(TimeUnit.MILLISECONDS) + "(毫秒).");
        } catch (Throwable throwable) {
            apiResponse = handlerException(pjp, throwable);
        }

        return apiResponse;
    }

    /**
     * @Author: gmy
     * @Description: 处理接口调用异常
     * @Date: 15:13 2018/10/25
     */
    private ApiResponse handlerException(ProceedingJoinPoint pjp, Throwable e) {
        ApiResponse apiResponse = null;
        if (e instanceof RuntimeException) {
            logger.error("RuntimeException{方法：" + pjp.getSignature() + "， 参数：" + pjp.getArgs() + ",异常：" + e.getMessage() + "}", e);
            apiResponse = new ApiResponse(ApiResponse.FALSE,e.getMessage());
        } else {
            logger.error("异常{方法：" + pjp.getSignature() + "， 参数：" + pjp.getArgs() + ",异常：" + e.getMessage() + "}", e);
            apiResponse = new ApiResponse(ApiResponse.FALSE,e.getMessage());
        }

        return apiResponse;
    }

    /**
     * @Author: gmy
     * @Description: 处理入参特殊字符和sql注入攻击
     * @Date: 15:37 2018/10/25
     */
    private void checkRequestParam(ProceedingJoinPoint pjp){
        String str = String.valueOf(pjp.getArgs());
        if (!IllegalStrFilterUtil.sqlStrFilter(str)) {
            logger.info("访问接口：" + pjp.getSignature() + "，输入参数存在SQL注入风险！参数为：" + Lists.newArrayList(pjp.getArgs()).toString());
        }
        if (!IllegalStrFilterUtil.isIllegalStr(str)) {
            logger.info("访问接口：" + pjp.getSignature() + ",输入参数含有非法字符!，参数为：" + Lists.newArrayList(pjp.getArgs()).toString());
        }
    }
}
