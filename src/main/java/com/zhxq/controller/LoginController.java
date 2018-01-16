package com.zhxq.controller;


import com.zhxq.domain.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpSession;
@Controller
@RequestMapping("/login")
public class LoginController {
    protected final Log logger = LogFactory.getLog(getClass());
    @GetMapping
    public String login(Model model, HttpSession session) {
        if (session.getAttribute("lastusername") != null) {
            logger.info(session.getAttribute("lastusername"));
            logger.info(session.getAttribute("password"));
            model.addAttribute("user", new User((String) session.getAttribute("lastusername"), null));
        }else
            model.addAttribute("user", new User());
        return "login";
    }
}


