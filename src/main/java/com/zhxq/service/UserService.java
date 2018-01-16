package com.zhxq.service;

import com.zhxq.domain.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserService {
    User register(User user);
    User getUser(String username);
    User updateUser(User user);
    User findUserContaining(String userid);
    void deleteUser(String userid);
    User addUser(User user);
    List<User> getrank();
    User calculateGrade(User user);
}


