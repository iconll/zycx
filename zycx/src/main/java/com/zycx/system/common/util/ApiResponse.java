package com.zycx.system.common.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author: ly
 * @Description: 接口统一返回格式
 * @Date: 2019/5/7
 */
@Setter
@Getter
@ToString
public class ApiResponse implements Serializable {

    public final transient static boolean TRUE = true;

    public final transient static boolean FALSE = false;

    private boolean success;

    private String msg;

    private Object data;

    public ApiResponse() {
    }
    public ApiResponse(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public ApiResponse(boolean success, String msg, Object data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
    }
}
