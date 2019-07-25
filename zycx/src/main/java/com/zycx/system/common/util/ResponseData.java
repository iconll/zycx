package com.zycx.system.common.util;


import com.zycx.system.common.base.constant.SystemStaticConst;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 封装响应结果数据
 */
public class ResponseData implements Serializable {
    private static Map<String,Object> responseData = null;

    /**
     * 返回响应数据并设置属性值
     * @param flag 响应成功还是失败标识:成功:true,失败:false
     * @param msg 响应消息
     * @param data 响应数据
     * @return
     */
    public static Map<String,Object> returnResponseData(boolean flag, String msg, Object data){
        responseData = new HashMap<String,Object>(16);

        responseData.put(SystemStaticConst.RESULT,flag);
        responseData.put(SystemStaticConst.MSG,msg);
        responseData.put(SystemStaticConst.DATA,data);

        return responseData;
    }

}
