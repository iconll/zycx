package com.zycx.system.common.exception;

/**
 * @description重复提交异常
 * @author gly
 * @date 2019年5月8日
 */
public class DuplicateSubmitException extends RuntimeException {
    public DuplicateSubmitException(String msg) {
        super(msg);
    }

    public DuplicateSubmitException(String msg, Throwable cause){
        super(msg,cause);
    }
}