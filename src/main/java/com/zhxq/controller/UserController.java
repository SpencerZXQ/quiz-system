package com.zhxq.controller;

import com.zhxq.domain.User;
import com.zhxq.repository.UserRepository;
import com.zhxq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class UserController {


    public UserService UserService;
    public UserRepository UserRepository;


    @Autowired
    public UserController(UserService UserService){
        this.UserService = UserService;
    }

    /**
     * 查询学生列表
     *
     * @return
     */
    @GetMapping(value = "/Userinfo")
    public List<User> UserList() {
        return UserRepository.findAll();
    }

    /**
     * 查询一个学生按照id
     * @return
     */
    @GetMapping(value = "/Userinfo/{id}")
    public User UserFindone(User User){
        return UserRepository.findById(User.getId());
    }

    /**
     * 查询一个学生按照姓名
     * @return
     */
    @GetMapping(value = "/Userinfo/{username}")
    public User findByusername(User User){
        return UserRepository.findByusername(User.getusername());
    }

    /**
     * 查询一个学生按照学号
     * @return
     */
    @GetMapping(value = "/Userinfo/{userid}")
    public User findByuserid(User User){
        return UserRepository.findByuserid(User.getuserid());
    }

    /**
     * 查询学生按照班级
     * @return
     */
    @GetMapping(value = "/Userinfo/class/{class}")
    public List<User> findByuserclass(User User){
        return UserRepository.findByuserclass(User.getuserclass());
    }

    /**
     * 查询学生按照成绩
     * @return
     */
    @GetMapping(value = "/Userinfo/grade/{grade}")
    public List<User> findBygrade(User User){
        return UserRepository.findBygrade(User.getgrade());
    }

    /**
     * 添加一个学生
     * @return
     */
    @PostMapping(value = "/Userinfo")
    public User UserAdd(User User, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return null;
        }
        User.setusername(User.getusername());
        User.setpassword(User.getpassword());
        User.setuserid(User.getuserid());
        User.setuserclass(User.getuserclass());
        return UserRepository.save(User);
    }




}


