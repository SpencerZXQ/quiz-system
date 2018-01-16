package com.zhxq.service;


import com.zhxq.domain.Answer;
import com.zhxq.repository.AnswerRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnswerServiceImpl implements AnswerService {

  @Autowired
  private AnswerRepository answerRepository;
  private Logger logger = Logger.getLogger(getClass());

  public AnswerServiceImpl(AnswerRepository answerRepository) {
    this.answerRepository = answerRepository;
  }

  @Override
  public Answer saveAnswer(Long UserId, Long questionId, String answer) {
      logger.info(UserId);
      logger.info(questionId);
    Answer answerSaved = answerRepository.findOneByUserIdAndQuestionId(UserId, questionId);
    logger.info(answerSaved);
    if (answerSaved != null) {
      answerSaved.setAnswer(answer);
      answerRepository.saveAndFlush(answerSaved);
      return answerSaved;
    } else {
      Answer answerInput = new Answer(UserId, questionId, answer);
      return answerRepository.saveAndFlush(answerInput);
    }
  }

  @Override
  public Map<Long, String> findAnswerByUserId(Long userId) {
    List<Answer> answers = answerRepository.findByUserId(userId);
    Map<Long, String> finished = new HashMap<>();
    for (Answer answer : answers) {
      finished.put(answer.getQuestionId(), answer.getAnswer());
    }
    return finished;
  }
}
