package com.pocketcloud.shiro.auth;

import lombok.Getter;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Author Zg.Li · 2019/12/11
 */
public class JwtToken implements AuthenticationToken {
    private static final long serialVersionUID = 1250166508152483573L;

    @Getter
    private final String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
