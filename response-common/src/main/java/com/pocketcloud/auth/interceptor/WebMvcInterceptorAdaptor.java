package com.pocketcloud.auth.interceptor;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 此类是一个使用统一结果响应的示例配置
 * 使用步骤：
 *   第一步：注册ResponseResultInterceptor拦截器
 *   第二步：controller方法加入@ResponseResult注解
 * 注意：
 *   @EnableWebMvc 此注解打开与不打开的影响目前还没有搞清楚，需要拦截静态资源的时候请注意一下，一般可以不开启。
 * @Author Zg.Li · 2019/12/25
 */
//@EnableWebMvc
//@Configuration
public class WebMvcInterceptorAdaptor implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加统一结果响应拦截器
        registry.addInterceptor(new ResponseResultInterceptor())
                // 添加拦截路径
                .addPathPatterns("/**");
    }
}
