package com.zhxq.controller;

import com.zhxq.domain.Question;
import com.zhxq.domain.User;
import com.zhxq.service.AnswerService;
import com.zhxq.service.QuestionService;
import com.zhxq.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.CountDownLatch;

@Controller
public class ExamController {
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;
    private Logger logger = Logger.getLogger(getClass());

    @Autowired
    public ExamController(QuestionService questionService, AnswerService answerService, UserService userService) {
        this.questionService = questionService;
        this.answerService = answerService;
        this.userService = userService;
    }

    @RequestMapping(value = "/exam", method = RequestMethod.GET)
    public String selectQuestion(HttpSession session) throws InterruptedException{


        User user = (User) session.getAttribute("user");
        if (session.getAttribute("questions") == null) {
            logger.info("session adasdasdsadasd" + user);
            session.setAttribute("questions", questionService.getQuestions());
            session.setAttribute("finished", answerService.findAnswerByUserId(user.getId()));
        }
        return "redirect:/exam/0";
    }

    @RequestMapping(value = "/exam/{questionId}", method = RequestMethod.GET)
    public String showQuestion(@PathVariable Integer questionId, Model model, HttpSession session) {
        if (session.getAttribute("key") == null) {
            return "redirect:/key";
        }
        Object questions = session.getAttribute("questions");
        if (questions == null) {
            return "redirect:/exam";
        }
        List questionList = (List) questions;
        model.addAttribute("question", questionList.get(questionId));
        model.addAttribute("questionCount", questionList.size());
        model.addAttribute("questionId", questionId);

        return "exam";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/exam/{questionId}", method = RequestMethod.POST)
        public String submitQuestion(@PathVariable Long questionId,
                                 @RequestParam("answer") String answer,
                                 @RequestParam("submit") String direct,
                                 HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Question> questions = (List<Question>) session.getAttribute("questions");
        Map<Long, String> finished = (Map<Long, String>) session.getAttribute("finished");
        answerService.saveAnswer(user.getId(), questions.get(questionId.intValue()).getId(), answer);
        questionService.calculateTrueOrFalse(user.getId(),questions.get(questionId.intValue()).getId());
        finished.put(questions.get(questionId.intValue()).getId(), answer);
        if (Objects.equals(direct, "next")) {
            return "redirect:/exam/" + (questionId + 1);
        } else if (Objects.equals(direct, "prev")) {
            return "redirect:/exam/" + (questionId - 1);
        } else {

            userService.calculateGrade(user);
            user.setAuthority("ROLE_USERDONE");
            userService.updateUser(user);
            return "redirect:/login";
        }
    }

//


}


