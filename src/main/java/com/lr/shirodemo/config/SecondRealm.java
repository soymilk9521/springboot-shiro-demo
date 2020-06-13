package com.lr.shirodemo.config;

import com.lr.shirodemo.entity.User;
import com.lr.shirodemo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *
 * </p>
 *
 * @author KR
 * @since 2020/05/06 11:25
 */
public class SecondRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("执行授权逻辑SecondRealm");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 添加角色
        info.addRole("admin");
        // 授权
        info.addStringPermission("user:add");
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行认证逻辑SecondRealm");
        // 1. 判断用户名
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 获取用户
        User user = userService.findByName(token.getUsername());
        String username = user != null ? user.getName(): "";
        if(!token.getUsername().equals(username)) {
            // 用户名不存在
            return null; // shiro底层会抛出UnKnownAccountException
        }
        // String password = "ce2f6417c7e1d32c1d81a797ee0b499f87c5de06";
        // user.setPassword(password);
        // 2. 判断密码
        // principal 认证的实体信息，也可以是username，也可以是数据表对于的用户的实体类
        // credentials 密码
        // salt 盐值 ByteSource.Util.bytes("shiro")
        // realmName当前realm对象的name调用getname即可
        return  new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes("admin"), getName());
    }
}
