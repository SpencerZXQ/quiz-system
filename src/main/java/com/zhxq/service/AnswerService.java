package com.zhxq.service;


import com.zhxq.domain.Answer;

import java.util.Map;

public interface AnswerService {
  Answer saveAnswer(Long userId, Long questionId, String answer);

  Map<Long, String> findAnswerByUserId(Long UserId);
}
