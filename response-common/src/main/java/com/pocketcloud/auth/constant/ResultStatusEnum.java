package com.pocketcloud.auth.constant;

/**
 * @Author Zg.Li · 2019/12/25
 */
public enum ResultStatusEnum {
    /**
     * 默认成功响应结果
     */
    SUCCESS(200, "success"),
    /**
     * 默认失败响应结果
     */
    FAILURE(500, "failed");
    private Integer code;
    private String message;

    /**
     * 可以使用 HttpStatusEnum类 指定响应状态码和自定义消息
     */
    ResultStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
