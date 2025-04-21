package com.saini.Quiz_service.service;


import com.saini.Quiz_service.dao.QuizDao;
import com.saini.Quiz_service.feign.QuizInterface;
import com.saini.Quiz_service.model.QuestionWrapper;
import com.saini.Quiz_service.model.Quiz;
import com.saini.Quiz_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuizService {
    @Autowired
    private QuizDao quizDao;

//    @Autowired
//    private QuestionDao questionDao;

    @Autowired
    QuizInterface quizInterface;


    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

//       List<Integer> questions = // call the generate url -Rest template
//
//        Quiz quiz = new Quiz();
//        quiz.setTitle(title);
//        quiz.setQuestions(questionList);
//        quizDao.save(quiz);

        List<Integer> questionList = quizInterface.getQuestionsForQuiz(category, numQ).getBody();

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questionList);
        quizDao.save(quiz);




        return  new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuiz(Integer quizId) {
        Optional<Quiz> quiz = quizDao.findById(quizId);
        List<Integer> questions = quiz.get().getQuestions();


        ResponseEntity<List<QuestionWrapper>> questionsForUser = quizInterface.getQuestionsFromIds(questions);




        return  questionsForUser;
    }

    public ResponseEntity<Integer> calculateScore(Integer quizId, List<Response> responses) {

   ResponseEntity<Integer> score= quizInterface.getScore(responses);
   return score;
    }


    public ResponseEntity<Quiz> getDetails(Integer quizId) {
        Optional<Quiz> quiz = quizDao.findById(quizId);
        return quiz.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
