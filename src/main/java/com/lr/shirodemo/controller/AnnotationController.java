package com.lr.shirodemo.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *
 * </p>
 *
 * @author KR
 * @since 2020/05/06 18:00
 */
@Controller
@RequestMapping("/permis")
public class AnnotationController {

    /**
     * 权限注解：
     * @RequiresAuthentication: 表示当前Subject已经通过login进行身份验证
     * @RequiresUser: 表示当前Subject已经验证或通过记住登录
     * @RequiresGuest: 游客身份
     * @RequiresRoles: 表示当前Subject需要相关角色权限
     * @RequiresPermisssions: 表示当前Subject需要相关权限
     */
    /**
     * 具有用户添加权限才可以访问
     * @return
     */
    @RequiresPermissions({"user:add"})
    @GetMapping("/add")
    public String add(HttpSession session) {
        session.setAttribute("name", "Curry");
        return "user/add";
    }

    /**
     * 具有用户更新权限才可以访问
     * @return
     */
    @RequiresPermissions({"user:update"})
    @GetMapping("/update")
    public String update() {
        return "user/update";
    }
}
