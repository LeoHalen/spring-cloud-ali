package site.zhigang.pocketcloud.interceptor;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import site.zhigang.pocketcloud.annotation.ResponseResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Author Zg.Li · 2019/12/25
 */
public class ResponseResultInterceptor implements HandlerInterceptor {

    // 使用统一结果响应注解标记
    private static final String RESPONSE_RESULT_SIGN = "RESPONSE-RESULT-SIGN";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            final HandlerMethod handlerMethod = (HandlerMethod) handler;
            final Class<?> beanType = handlerMethod.getBeanType();
            final Method method = handlerMethod.getMethod();
            if (beanType.isAnnotationPresent(ResponseResult.class)) {
                request.setAttribute(RESPONSE_RESULT_SIGN, beanType.getAnnotation(ResponseResult.class));
            } else if (method.isAnnotationPresent(ResponseResult.class)) {
                request.setAttribute(RESPONSE_RESULT_SIGN, method.getAnnotation(ResponseResult.class));
            }
        }
        return true;
    }
}
