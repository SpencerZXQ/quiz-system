package com.zhxq.repository;

import com.zhxq.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByusername(String username);
    User findByuserid(String userid);
    List<User> findByuserclass(Integer userclass);
    List<User> findAllByOrderByGradeDesc();
    User findById(long id);
    void deleteUserByUserid(String userid);
    User findByUseridContaining(String userid);
    List<User> findBygrade(Integer getgrade);
}
