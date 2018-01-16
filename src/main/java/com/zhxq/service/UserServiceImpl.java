package com.zhxq.service;

import com.zhxq.domain.Answer;
import com.zhxq.domain.User;
import com.zhxq.repository.AnswerRepository;
import com.zhxq.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService  {

    @Autowired
    private UserRepository userRepository;
    private AnswerRepository answerRepository;
    public UserServiceImpl(UserRepository userRepository, AnswerRepository answerRepository){
        this.userRepository = userRepository;
        this.answerRepository = answerRepository;
    }

    @Override
    public User register(User User){
        String encodedPass = new BCryptPasswordEncoder().encode(User.getpassword());
        User.setpassword(encodedPass);
        return userRepository.save(User);
    }

    @Override
    public User getUser(String username){
        return userRepository.findByusername(username);

    }

    @Override
   public User updateUser(User User){
        return userRepository.saveAndFlush(User);
    }

    @Override
    public User findUserContaining(String userid){
        return userRepository.findByUseridContaining(userid);
    }

    @Override
    public void deleteUser(String  userid){
        userRepository.deleteUserByUserid(userid);
    }

    @Override
    public User addUser(User User){
        return userRepository.saveAndFlush(User);
}

    @Override
    public List<User> getrank(){
        List<User> users = userRepository.findAllByOrderByGradeDesc();
        users.remove(users.size()-1);
        int plus = 0;
        int samecount = 0;
      for (int i = 0;i<users.size()-1;i=i+1) {
            User user1 = users.get(i);
            User user2 = users.get(i+1);
            plus = user1.getgrade() == user2.getgrade() ? 0 : 1 ;
            if(plus == 0){
                samecount = samecount+1;
                users.get(i+1).setRank(user1.getRank());
            }else {
                users.get(i+1).setRank(user1.getRank()+samecount+plus);
                samecount = 0;
            }

     }
        return users;
    }

    @Override
    public User calculateGrade(User user) {
        List<Answer> answers =  answerRepository.findByUserId(user.getId());
        user.setgrade(0);
        for (int i=0; i < answers.size(); i++){
            if (answers.get(i).getCorrect()){
                user.setgrade(user.getgrade() + answers.get(i).getCorrectnum()*10);
            }
        }
        return userRepository.saveAndFlush(user);
    }
}
