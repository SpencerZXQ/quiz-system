package com.zhxq.service;


import com.zhxq.domain.Answer;
import com.zhxq.domain.Question;
import com.zhxq.domain.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface QuestionService {
  Question addQuestion(Question question);
  void removeQuestion(Long id);
  List<Question> getQuestions();

  Answer calculateTrueOrFalse(Long UserId, Long questionId);

  List<Question> findQuestionContaining(String question);

  Boolean importFile(InputStream file) throws IOException;
}