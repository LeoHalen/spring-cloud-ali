package site.zhigang.pocketcloud.auth.common.bean;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * Create by Zg.Li · 2019/12/23
 */
@Data
public class JwtProperties {
    private String userSecret;
    /**
     * token在http header中的key
     */
    private String AUTHORIZATION_HEADER_KEY = "Authorization";
    /**
     * token过期时间
     */
    private int authExpire;
    /**
     * token redis key
     */
    private String AUTH_REDIS_KEY = "pocket-token";
    /**
     * 缓存有效时间
     */
    private int cacheValidTime = 10;
    /**
     * 过滤规则集
     */
    private List<String> filterRules = Lists.newArrayList();
}
