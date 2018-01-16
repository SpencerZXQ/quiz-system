package com.zhxq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Objects;
@Controller
@RequestMapping("/key")
public class KeyController {
    private final String KEY = "2018";

    @GetMapping
    public String getKey() {
        return "key";
    }

    @PostMapping
    public String submitKey(@RequestParam String key, HttpSession session) {
        if (Objects.equals(KEY, key)) {
            session.setAttribute("key", Boolean.TRUE);
            Long date = System.currentTimeMillis();
            System.out.println(date);
//            5分钟
            Long finishdate = date + 5*60*1000;
            System.out.println(finishdate);

            //redirect是服务端根据逻辑,发送一个状态码,告诉浏览器重新去请求那个地址.所以地址栏显示的是新的URL.
            return "redirect:/exam";
        } else {
            return "redirect:/key?error";
        }
    }




}


