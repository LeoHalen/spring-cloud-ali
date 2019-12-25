package site.zhigang.pocketcloud.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import site.zhigang.pocketcloud.constant.ResultStatusEnum;

/**
 * 错误结果响应
 * @Author Zg.Li · 2019/12/25
 */
@Data
@AllArgsConstructor
public class ErrorResponseResult<T> {
    private ResultStatusEnum errorResultStatus;
    private T errorData;

    public static <M> ErrorResponseResult<M> result(ResultStatusEnum errorResultStatus, M errorData) {
        return new ErrorResponseResult<>(errorResultStatus, errorData);
    }

    public static <M> ErrorResponseResult<M> result(M errorData) {
        return new ErrorResponseResult<>(ResultStatusEnum.FAILURE, errorData);
    }

    public static ErrorResponseResult defaultResult() {
        return new ErrorResponseResult(ResultStatusEnum.FAILURE, "服务异常，请稍后重试或联系客服！");
    }
}
