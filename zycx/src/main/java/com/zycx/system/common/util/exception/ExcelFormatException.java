package com.zycx.system.common.util.exception;

/**
 * Excel格式错误异常类
 */
public class ExcelFormatException extends RuntimeException {

    /**
     * 错误描述消息
     */
    private String message;

    public ExcelFormatException() {
        super();
    }

    public ExcelFormatException(String message) {
        super(message);
    }

    public ExcelFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelFormatException(Throwable cause) {
        super(cause);
    }

    protected ExcelFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
