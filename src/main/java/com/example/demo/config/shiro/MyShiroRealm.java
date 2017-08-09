package com.example.demo.config.shiro;

import com.example.demo.model.UserInfo;
import com.example.demo.service.UserInfoService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by BFD-593 on 2017/8/8.
 */
public class MyShiroRealm extends AuthorizingRealm{
    private static final Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);
    @Autowired
    private UserInfoService userInfoService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        logger.info("开始身份验证");
        String username = (String) token.getPrincipal();
        UserInfo userInfo = userInfoService.findByUsername(username);
        if(userInfo==null) {
            return null;
        }
        SimpleAuthenticationInfo auth = new SimpleAuthenticationInfo(
                userInfo,
                userInfo.getPassword(), //密码
                getName()  //realm name
        );
        return auth;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("开始权限配置");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        UserInfo userInfo = (UserInfo)principals.getPrimaryPrincipal();
       /* for (SysRole role: userInfo.getRoleList()) {
            authorizationInfo.addRole(role.getRole());
            for (SysPermission p: role.getPermissions()) {
                authorizationInfo.addStringPermission(p.getPermission());
            }
        }*/
        return authorizationInfo;
    }
}
