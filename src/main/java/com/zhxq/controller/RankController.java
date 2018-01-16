package com.zhxq.controller;

import com.zhxq.domain.Question;
import com.zhxq.domain.User;
import com.zhxq.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping()
public class RankController {
    private UserService userService;
    private Logger logger = Logger.getLogger(getClass());

    @Autowired
    public RankController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(value = "/rank", method = RequestMethod.GET)
    public String getUserRank( Model model) {
        List<User> users = userService.getrank();
        model.addAttribute("user", users);
        return "rank";
    }
}
