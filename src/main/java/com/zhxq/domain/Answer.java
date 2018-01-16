package com.zhxq.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

@Entity
@Table(name = "answer")
public class Answer {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private Long userId;
  private Long questionId;
  @Column(length = 1023)
  private String answer;
  private Boolean correct;
  private Integer correctnum = 0;

  public Answer(Long userId, Long questionId, String answer) {
    this.userId = userId;
    this.questionId = questionId;
    this.answer = answer;
  }

  public Answer() {
  }

  public Integer getCorrectnum() {
    return correctnum;
  }

  public void setCorrectnum(Integer correctnum) {
    this.correctnum = correctnum;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getQuestionId() {
    return questionId;
  }

  public void setQuestionId(Long questionId) {
    this.questionId = questionId;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  public Boolean getCorrect() {
    return correct;
  }

  public void setCorrect(Boolean correct) {
    this.correct = correct;
  }
}
