package com.zhxq.controller;

import com.zhxq.domain.User;
import com.zhxq.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/register")

public class RegisterController {
    private UserService UserService;
    private Logger logger = Logger.getLogger(getClass());

    @Autowired
    public RegisterController(UserService UserService) {
        this.UserService = UserService;
    }

    @GetMapping
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping
    public String processRegister(@Valid User user, Errors errors, HttpServletRequest request) throws ServletException {
        if (errors.hasErrors()) {
            return "register";
        }
        UserService.register(user);
//        logger.info(user);
//        logger.info(user.getusername());

        request.login(user.getusername(), user.getpassword());
        return "redirect:/profile";
    }




}


