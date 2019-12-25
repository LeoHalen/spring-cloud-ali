package site.zhigang.pocketcloud.bean;

import lombok.Data;
import site.zhigang.pocketcloud.constant.ResultStatusEnum;

import java.io.Serializable;

/**
 * 统一结果响应
 * @Author Zg.Li · 2019/12/25
 */
@Data
public class ResponseResultBean<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;

    public ResponseResultBean(ResultStatusEnum resultStatus, T data) {
        this.code = resultStatus.getCode();
        this.message = resultStatus.getMessage();
        this.data = data;
    }

    public ResponseResultBean(ResultStatusEnum resultStatus) {
        this.code = resultStatus.getCode();
        this.message = resultStatus.getMessage();
    }

    public static ResponseResultBean success() {
        return new ResponseResultBean(ResultStatusEnum.SUCCESS);
    }

    public static <M> ResponseResultBean<M> success(M data) {
        return new ResponseResultBean<>(ResultStatusEnum.SUCCESS, data);
    }

    public static ResponseResultBean failure(ResultStatusEnum errorResultStatus) {
        return new ResponseResultBean(errorResultStatus);
    }

    public static <M> ResponseResultBean<M> failure(ResultStatusEnum errorResultStatus, M data) {
        return new ResponseResultBean<>(errorResultStatus, data);
    }
}
