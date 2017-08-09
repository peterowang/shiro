package com.example.demo.config.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by BFD-593 on 2017/8/8.
 */
@Configuration
public class ShiroConfiguration {
    @Bean(name="shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager manager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(manager);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/**", "authc");
        //authc表示需要验证身份才能访问，还有一些比如anon表示不需要验证身份就能访问等。
        //关于为什么设置filterChainDefinitionMap.put("/favicon.ico", "anon");，请参考Shiro登录后下载favicon.ico问题

        shiroFilterFactoryBean.setLoginUrl("/login");//登录url
        shiroFilterFactoryBean.setSuccessUrl("/index");//登录成功后跳转的页面
//        shiroFilterFactoryBean.setUnauthorizedUrl("/403"); //这里设置403并不会起作用，参考http://www.jianshu.com/p/e03f5b54838c

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }


    /**
     * 不指定名字的话，自动创建一个方法名第一个字母小写的bean
     * @Bean(name = "securityManager")
     * @return
     */
    @Bean(name="securityManager")
    public SecurityManager securityManager(@Qualifier("authRealm") MyShiroRealm authRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(authRealm);
        return securityManager;
    }

    /**
     * Shiro Realm 继承自AuthorizingRealm的自定义Realm,即指定Shiro验证用户登录的类为自定义的
     *
     * @param
     * @return
     */
    @Bean(name="authRealm")
    public MyShiroRealm userRealm(@Qualifier("credentialsMatcher") CredentialsMatcher matcher) {
        MyShiroRealm userRealm = new MyShiroRealm();
        //告诉realm,使用credentialsMatcher加密算法类来验证密文
        userRealm.setCredentialsMatcher(matcher);
        return userRealm;
    }

    /**
     * 密码比较器
     * @return
     */
    @Bean(name="credentialsMatcher")
    public CredentialsMatcher credentialsMatcher() {
        return new CredentialsMatcher();
    }

}
