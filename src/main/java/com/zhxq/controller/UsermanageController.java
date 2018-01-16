package com.zhxq.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhxq.domain.User;
import com.zhxq.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.validation.Valid;

@Controller
@RequestMapping()
public class UsermanageController {
    private UserService userService;
    private Logger logger = Logger.getLogger(getClass());

    @Autowired
    public UsermanageController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(value = "/usermanage", method = RequestMethod.GET)
    public String manage(Model model) {
        model.addAttribute("usersearch", new User());
        model.addAttribute("useradd", new User());
        return "usermanage";
    }

   //搜索的是学生的学号
    @RequestMapping(value = "/searchuser", method = RequestMethod.POST)
    public String searchUser(@RequestParam("userid") String userid, Model model) {
        User searchuser  = userService.findUserContaining(userid);
        logger.info(searchuser);
        model.addAttribute("usersearch", searchuser);
        model.addAttribute("useradd", new User());
        return "usermanage";
    }

    @RequestMapping(value = "/deleteuser", method = RequestMethod.POST)
    public String deleteUser(@RequestParam("userid") String userid, Model model) {
        logger.info(userid);
        userService.deleteUser(userid);
        model.addAttribute("useradd", new User());
        model.addAttribute("usersearch", new User());
        return "usermanage";
    }

    @RequestMapping(value = "/adduser", method = RequestMethod.POST)
    public String addUser(@Valid User useradd, Errors errors, Model model) throws ServletException {
        if (errors.hasErrors()) {
            return "usermanage";
        }
        userService.register(useradd);
        model.addAttribute("useradd", new User());
        model.addAttribute("usersearch", new User());
        return "usermanage";
    }









}
