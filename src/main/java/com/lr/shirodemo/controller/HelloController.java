package com.lr.shirodemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *
 * </p>
 *
 * @author KR
 * @since 2020/05/05 9:52
 */
@Controller
public class HelloController {

    @GetMapping(value = "hello")
    @ResponseBody
    public String hello(){
        return "hello shiro";
    }

    @GetMapping("home")
    public String thymeleaf(Model model) {
        model.addAttribute("name", "Curry");
        return "home";
    }
}
