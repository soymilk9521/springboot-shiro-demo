package com.lr.shirodemo.controller;

import com.lr.shirodemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *
 * </p>
 *
 * @author KR
 * @since 2020/05/05 10:35
 */
@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(value = "add")
    public String add (HttpSession session) {
        session.setAttribute("name", "Curry");
        // 测试session
        String value = userService.getSessionValue("name");
        System.out.println(value);
        return "/user/add";
    }

    @GetMapping(value = "update")
    public String preUpdate() {
        return "user/update";
    }

    @PostMapping(value = "update")
    public String update() {
        return "user/update";
    }
}
