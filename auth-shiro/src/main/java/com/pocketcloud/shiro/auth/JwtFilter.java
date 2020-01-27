package com.pocketcloud.shiro.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import com.pocketcloud.response.common.common.constants.RestCodeConstant;
import com.pocketcloud.response.common.common.response.BaseResponse;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;

/**
 * @Author Zg.Li · 2019/12/11
 */
@Slf4j
public class JwtFilter extends AccessControlFilter {

    private final String authorizationKey;

    public JwtFilter(String authorizationKey) {
        this.authorizationKey = authorizationKey;
    }

    @Override
    protected void cleanup(ServletRequest request, ServletResponse response, Exception existing) throws ServletException, IOException {
        super.cleanup(request, response, existing);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest req, ServletResponse res, Object mappedValue) throws Exception {
        String requestURI = WebUtils.toHttp(req).getRequestURI();
        log.debug("Access allowed URI:[{}]", requestURI);
        // 认证过的请求、登录请求、options请求允许通过拦截
        return this.getSubject(req, res).isAuthenticated()
                || this.isLoginRequest(req, res) || "options".equalsIgnoreCase(WebUtils.toHttp(req).getMethod());
    }

    @Override
    protected boolean onAccessDenied(ServletRequest req, ServletResponse res) throws Exception {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(req);
        try {
            String authorization = httpServletRequest.getHeader(authorizationKey);
            if (authorization == null) {
                authorization = httpServletRequest.getParameter(authorizationKey);
            }
            if (Strings.isNullOrEmpty(authorization)) {
                Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
                Map<String, String> headerMap = Maps.newHashMap();
                while(headerNames.hasMoreElements()) {
                    String headerName = headerNames.nextElement();
                    headerMap.put(headerName, httpServletRequest.getHeader(headerName));
                }
                log.warn("Token cannot be obtained! Authorization: [{}], header: [{}]", authorizationKey, headerMap);
                throw new AuthenticationException("Token cannot be obtained!");
            }
            // 先简单校验一下token 结构的合法性
            // TODO
            log.info("JWT Filter gets the token is [{}].", authorization);
            JwtToken jwtToken = new JwtToken(authorization);
            getSubject(req, res).login(jwtToken);
        } catch (AuthenticationException e) {
            log.info("Failed user login!");
            onLoginFailed(res, e);
            return false;
        }
        return true;
    }

    /**
     * token 校验失败返回 401 状态码
     * @param response
     * @param e
     * @throws IOException
     */
    private void onLoginFailed(ServletResponse response, Exception e) throws IOException {
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        BaseResponse resResult = new BaseResponse(RestCodeConstant.TOKEN_ERROR_CODE, e.getMessage());
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter responseWriter = httpServletResponse.getWriter();
        mapper.writeValue(responseWriter, resResult);
        responseWriter.flush();
        responseWriter.close();
    }
}
