package com.lz.springbootjwt.shiro;

import com.lz.springbootjwt.jwt.filter.JwtFilter;
import com.lz.springbootjwt.jwt.interception.JWTInterceptor;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置类
 * @author lzj
 * @create 2019-07-29 14:10
 */
@Configuration
public class ShiroConfig {

    /**
     * 创建shiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(defaultWebSecurityManager());

        Map<String,String> filterMap = new LinkedHashMap<>();
        filterMap.put("/user/login", "anon");
        filterMap.put("/user/login.do", "anon");
        //filterMap.put("/user/findUser.do", "perms[user:find]");

        HashMap<String, Filter> hashMap = new HashMap<>();
        hashMap.put("jwtFilter", new JwtFilter());

        filterMap.put("/**/*.do", "jwtFilter");

        factoryBean.setFilters(hashMap);
        factoryBean.setLoginUrl("/unauth");
        factoryBean.setFilterChainDefinitionMap(filterMap);
        return factoryBean;
    }

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myRealm());
        securityManager.setCacheManager(ehCacheManager());
        //关闭shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator);

        return securityManager;
    }

    /**
     * 缓存管理器
     * @return
     */
    @Bean
    public EhCacheManager ehCacheManager(){
        EhCacheManager ehCacheManager = new EhCacheManager();
        return ehCacheManager;
    }

    /**
     * 开启shiro注解
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 开启aop支持
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(defaultWebSecurityManager());
        return advisor;
    }
    @Bean
    public MyRealm myRealm(){
        MyRealm myRealm = new MyRealm();
        return myRealm;
    }
}
