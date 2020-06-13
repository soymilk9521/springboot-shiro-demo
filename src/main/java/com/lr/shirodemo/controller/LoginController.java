package com.lr.shirodemo.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * <p>
 *
 * </p>
 *
 * @author KR
 * @since 2020/05/05 10:55
 */
@Controller
public class LoginController {

    /**
     * 跳转到登录页面
     * @return
     */
    @GetMapping("login")
    public String index() {
        return "login";
    }

    /**
     * 登录处理
     * @param model
     * @param username
     * @param password
     * @return
     */
    @PostMapping("login")
    public String login(Model model, String username, String password) {
        // 1. 获取Subject
        Subject subject = SecurityUtils.getSubject();
        // 2. 封装用户登录信息
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // 3. 执行登录方法
        try {
            subject.login(token);
            return "redirect:/home";
        }catch (UnknownAccountException e) {
            // UnknownAccountException: 用户名不存在，登录失败
            model.addAttribute("message", "用户名不存在");
            return "login";
        }catch (IncorrectCredentialsException e) {
            // IncorrectCredentialsException: 密码错误，登录失败
            model.addAttribute("message", "密码错误");
            return "login";
        }catch (AuthenticationException e) {
            model.addAttribute("message", "用户名或密码错误");
            return "login";
        }
    }

    /**
     * 验证未授权时跳转到该页面
     * @return
     */
    @GetMapping("unauthorized")
    public String unauthorized() {

        return "unauthorized";
    }
}
