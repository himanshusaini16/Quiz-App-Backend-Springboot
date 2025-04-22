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
        // Check if quiz exists
        Optional<Quiz> quizOptional = quizDao.findById(quizId);
        if (!quizOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 if quiz not found
        }

        Quiz quiz = quizOptional.get();
        List<Integer> questions = quiz.getQuestions();
        System.out.println("Quiz ID: " + quizId);
        System.out.println("Question IDs to fetch: " + questions);

        try {
            // Make Feign call to fetch questions
            ResponseEntity<List<QuestionWrapper>> questionsForUser = quizInterface.getQuestionsFromIds(questions);
            return questionsForUser;
        } catch (Exception e) {
            e.printStackTrace(); // print full error in logs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 500 if something breaks
        }
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
