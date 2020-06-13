package com.lr.shirodemo.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AllSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author KR
 * @since 2020/05/05 10:09
 */
@SpringBootConfiguration
public class ShiroConfig {
    /**
     * 1. 创建ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        // 未认证请求跳转至登录页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 设置未授权提示页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");
        // 添加Shiro内置过滤器
        // Shiro内置过滤器，可以实现权限相关的拦截器
        //  常用的过滤器：
        //      anon: 无需认证登录都可以访问
        //      authc: 必须认证才可以访问
        //      user: 如果使用rememberMe的功能可以直接访问
        //      perms: 必须具有相关权限才可以访问
        //      roles: 必须具有相关角色权限才可以访问
        Map<String, String> filterMap = new LinkedHashMap<>();
        // 所有请求必须认证才可以访问
        // login请求，无需认证可以直接访问
        filterMap.put("/login", "anon");
        filterMap.put("/user/*", "roles[admin]");
        // 授权
        filterMap.put("/user/add", "perms[user:add]");
        filterMap.put("/user/update", "perms[user:update]");
        filterMap.put("/*", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        return shiroFilterFactoryBean;
    }

    /**
     * 2. 创建DefaultWebSecurityManager
     *    安全管理器关联Realm
     */
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关联Realm
        // securityManager.setRealm(userRealm); // 单个Realm验证配置
        securityManager.setAuthenticator(modularRealmAuthenticator()); // 复数Realm验证配置
        List<Realm> realms = new ArrayList<>();
        realms.add(userRealm());
        realms.add(secondRealm());
        securityManager.setRealms(realms);
        return securityManager;
    }

    /**
     * 3. 创建Realm
     *    Realm是Shiro连接数据库桥梁
     */
    @Bean
    public UserRealm userRealm() {
        // 加密策略
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("MD5");  // MD5加密方式
        matcher.setHashIterations(1024);      // 加密次数
        UserRealm realm = new UserRealm();
        realm.setCredentialsMatcher(matcher);
        return realm;
    }

    /**
     * 创建第二个Realm
     *    SHA1加密方式
     *    Realm是Shiro连接数据库桥梁
     */
    @Bean
    public SecondRealm secondRealm() {
        // 加密策略
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("SHA1"); // SHA1加密方式
        matcher.setHashIterations(1024);      // 加密次数
        SecondRealm realm = new SecondRealm();
        realm.setCredentialsMatcher(matcher);
        return realm;
    }

    /**
     * 复数realm验证配置
     * @return
     */
    @Bean
    public ModularRealmAuthenticator modularRealmAuthenticator() {
        ModularRealmAuthenticator modularRealmAuthenticator = new ModularRealmAuthenticator();
        // List<Realm> list = new ArrayList<>();
        // list.add(userRealm());
        // list.add(secondRealm());
        // modularRealmAuthenticator.setRealms(list); // securityManager 中设置Realm
        // 认证策略：
        // FirstSuccessfulStrategy： 只要有一个认证成功即可，返回第一个认证成功的认证信息
        // AtLeastOneSucessfulStrategy: 只要有一个认证成功即可，返回认证成功的认证信息(默认)
        // AllSuccessfulStrategy： 必须所有realm延迟成功， 如果有一个认证失败则认证失败
        // modularRealmAuthenticator.setAuthenticationStrategy(new AllSuccessfulStrategy());
        return modularRealmAuthenticator;
    }

    /**
     * 4. 配置Shiro的thymeleaf支持
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return  new ShiroDialect();
    }
}
