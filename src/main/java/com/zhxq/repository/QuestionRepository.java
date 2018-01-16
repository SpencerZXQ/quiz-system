package com.zhxq.repository;

import com.zhxq.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
  List<Question> findByQuestionContaining(String question);
  Question findById(Long id);
}
