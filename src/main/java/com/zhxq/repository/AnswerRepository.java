package com.zhxq.repository;


import com.zhxq.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
  Answer findOneByUserIdAndQuestionId(Long userId, Long questionId);
  List<Answer> findByUserId(Long userId);
}
