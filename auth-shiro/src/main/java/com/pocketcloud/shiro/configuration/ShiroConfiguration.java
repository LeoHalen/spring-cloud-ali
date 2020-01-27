package com.pocketcloud.shiro.configuration;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import com.pocketcloud.shiro.auth.JwtFilter;
import com.pocketcloud.shiro.auth.JwtRealm;
import com.pocketcloud.response.common.common.bean.JwtProperties;

import javax.servlet.Filter;
import java.util.List;
import java.util.Map;

/**
 * @Author Zg.Li · 2019/12/23
 */
@Slf4j
public class ShiroConfiguration {

    @Bean
    @ConditionalOnMissingBean(JwtProperties.class)
    @ConfigurationProperties(prefix = "jwt")
    public JwtProperties jwtProperties() {
        return new JwtProperties();
    }

    @Bean
    public JwtRealm jwtRealm() {
        return new JwtRealm();
    }

    /**
     * 安全管理器
     */
    @Bean
    public SecurityManager securityManager(@Qualifier("jwtRealm") JwtRealm jwtRealm) {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        // 使用自定义的realm
        securityManager.setRealm(jwtRealm);
        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);

        return securityManager;
    }

    /**
     * shiro基础配置
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactory(SecurityManager securityManager, JwtProperties jwtProperties) {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager);
        // 登录页地址
        filterFactoryBean.setLoginUrl("/login");
        // 登录成功后转到首页地址
        filterFactoryBean.setSuccessUrl("/index");
        // 自定义过滤器集
        Map<String, Filter> filterMap = Maps.newLinkedHashMap();
        // 添加自定义过滤器
        filterMap.put("jwt", new JwtFilter(jwtProperties.getAUTHORIZATION_HEADER_KEY()));
        filterFactoryBean.setFilters(filterMap);

        log.info("Custom url filtering rules: [{}]", jwtProperties.getFilterRules());
        Map<String, String> urlFilterRuleMap = Maps.newLinkedHashMap();
        // url 过滤规则集
        List<String> filterRules = jwtProperties.getFilterRules();

        /*Map<String, String> urlFilterRuleMap = filterRules.stream().map(rule -> rule.split("="))
                .collect(Collectors.toLinkedHashMap(ruleK -> ruleK[0], ruleV -> ruleV[1], (k1, k2) -> k1));*/
        filterRules.forEach(rule -> {
            String[] ruleKV = rule.split("=");
            urlFilterRuleMap.put(ruleKV[0], ruleKV[1]);
        });
        urlFilterRuleMap.put("/**", "jwt");
        filterFactoryBean.setFilterChainDefinitionMap(urlFilterRuleMap);

        return filterFactoryBean;
    }

    /**
     * 以下代码是开启shiro-aop注解支持
     * 使用代理方式所以需要开启支持
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        // https://zhuanlan.zhihu.com/p/29161098
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
