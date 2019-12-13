package site.zhigang.auth.shiro.bean;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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
        String authorizaiton = httpServletRequest.getHeader(authorizationKey);
        if (authorizaiton == null) {
            authorizaiton = httpServletRequest.getParameter(authorizationKey);
        }
        if (Strings.isNullOrEmpty(authorizaiton)) {

        }
        return false;
    }
}
