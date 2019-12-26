package site.zhigang.pocketcloud.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import site.zhigang.pocketcloud.annotation.ResponseResult;
import site.zhigang.pocketcloud.bean.ErrorResponseResult;
import site.zhigang.pocketcloud.bean.ResponseResultBean;

import javax.servlet.http.HttpServletRequest;

/**
 * 响应体格式统一处理
 * @Author Zg.Li · 2019/12/25
 */
@Slf4j
@ControllerAdvice
public class ResponseResultHandler implements ResponseBodyAdvice<Object> {

    /**
     * 使用统一结果响应注解标记
     */
    private static final String RESPONSE_RESULT_SIGN = "RESPONSE-RESULT-SIGN";


    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        // 获取到加@ResponseResult注解的标记
        ResponseResult responseResult = (ResponseResult) request.getAttribute(RESPONSE_RESULT_SIGN);
        return responseResult != null;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType
            , Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest
            , ServerHttpResponse serverHttpResponse) {
        log.info("Uniform result response formatting.");
        if (body instanceof ErrorResponseResult) {
            log.info("Uniform error result response formatting.");
            ErrorResponseResult errorResult = (ErrorResponseResult) body;
            return ResponseResultBean.failure(errorResult.getErrorResultStatus(), errorResult.getErrorData());
        }
        return ResponseResultBean.success(body);
    }
}
