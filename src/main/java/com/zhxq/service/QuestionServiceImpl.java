package com.zhxq.service;


import com.zhxq.domain.Answer;
import com.zhxq.domain.Choice;
import com.zhxq.domain.Question;
import com.zhxq.domain.User;
import com.zhxq.repository.AnswerRepository;
import com.zhxq.repository.QuestionRepository;
import com.zhxq.repository.UserRepository;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
  @Autowired
  private QuestionRepository questionRepository;
  private AnswerRepository answerRepository;
  private Logger logger = Logger.getLogger(getClass());

  public QuestionServiceImpl(QuestionRepository questionRepository,AnswerRepository answerRepository) {
    this.questionRepository = questionRepository;
    this.answerRepository = answerRepository;
  }

  @Override
  public Question addQuestion(Question question) {
    return questionRepository.saveAndFlush(question);
  }

  @Override
  public void removeQuestion(Long id) {
    questionRepository.delete(id);
  }

  @Override
  public List<Question> findQuestionContaining(String question) {
    return questionRepository.findByQuestionContaining(question);
  }

  @Override
  public Boolean importFile(InputStream file) throws IOException {
    Workbook workbook = new XSSFWorkbook(file);
    ////获取第一个Sheet表
    Sheet sheet = workbook.getSheetAt(0);
    // sheet.getFirstRowNum() + 1:获取第一个实际行的下标,去掉内容选项那一行
    for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
      Row row = sheet.getRow(i);
      if (row.getCell(i)!=null) {
        row.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
      }
      String questionString = row.getCell(0).getStringCellValue();
      String answerString = row.getCell(1).getStringCellValue();
      logger.info(questionString);
      logger.info(answerString);
      List<Choice> choices = new ArrayList<>();
      for (int j = 2; j < row.getLastCellNum(); j++) {
        String choice = row.getCell(j).getStringCellValue();
        choices.add(new Choice(choice));
      }
      Question question = new Question(questionString,answerString, choices);
      questionRepository.save(question);
    }
    questionRepository.flush();
    return true;
  }

  @Override
  //题目随机
  public List<Question> getQuestions() {
    List<Question> questions = questionRepository.findAll();
    for (Question question : questions) {
      Collections.shuffle(question.getChoices());
    }
    Collections.shuffle(questions);
    return questions;
  }

    @Override
    public Answer calculateTrueOrFalse(Long userId, Long questionId) {
        logger.info(userId);
        logger.info(questionId);
        Answer useranswer = answerRepository.findOneByUserIdAndQuestionId(userId, questionId);
        String correctanswer =  questionRepository.findById(questionId).getAnswer();
        if(useranswer.getAnswer() != null && useranswer.getAnswer() != "") {
          if (correctanswer.contains(useranswer.getAnswer())) {
            if (correctanswer.equals(useranswer.getAnswer())) {
              useranswer.setCorrect(true);
              useranswer.setCorrectnum(2);
            } else {
              useranswer.setCorrect(true);
              useranswer.setCorrectnum(1);
            }
          }
        } else {
            useranswer.setCorrect(false);
          }

        return answerRepository.saveAndFlush(useranswer);
    }

}
