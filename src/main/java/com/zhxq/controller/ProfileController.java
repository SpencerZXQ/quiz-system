package com.zhxq.controller;

import com.zhxq.domain.User;
import com.zhxq.service.UserService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private  UserService userService;
    private Logger logger = Logger.getLogger(getClass());

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String profile(Model model, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        logger.info(username);
        User user = userService.getUser(username);
        model.addAttribute("user", user);
        session.setAttribute("user", user);
        logger.info(user);
        return "profile";
    }
    @RequestMapping(method = RequestMethod.POST)
    public String updateProfile(User user, Model model, HttpSession session) {
        if (!validate(user.getuserclass())) {
            model.addAttribute("status", Boolean.FALSE);
            user.setusername(((User) session.getAttribute("user")).getusername());
            return "profile";
        }
        logger.info("update " + user);
        User originUser = (User) session.getAttribute("user");
        originUser.setuserclass(user.getuserclass());

        userService.updateUser(originUser);
        model.addAttribute("status", Boolean.TRUE);
        user.setusername(((User) session.getAttribute("user")).getusername());
        return "profile";
    }

    private boolean validate(Integer userclass) {
        if (userclass == null){
            logger.info("Class " + userclass + " Wrong");
            return false;
        }
        return true;
    }
}


