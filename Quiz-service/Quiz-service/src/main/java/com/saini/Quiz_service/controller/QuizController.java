package com.saini.Quiz_service.controller;


import com.saini.Quiz_service.model.QuestionWrapper;
import com.saini.Quiz_service.model.Quiz;
import com.saini.Quiz_service.model.QuizDto;
import com.saini.Quiz_service.model.Response;
import com.saini.Quiz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto) {
        return quizService.createQuiz(quizDto.getCategory(), quizDto.getNumberOfQuestions(), quizDto.getTitle());
    }

    @GetMapping("getQuiz/{quizId}")
    public ResponseEntity<List<QuestionWrapper>> getQuiz(@PathVariable Integer quizId) {
        return quizService.getQuiz(quizId);
    }

    @PostMapping("submitQuiz/{quizId}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer quizId, @RequestBody List<Response> responses) {
        return quizService.calculateScore(quizId, responses);
    }

    @GetMapping("getQuizDetails/{quizId}")
    public ResponseEntity<Quiz> getQuizDetails(@PathVariable Integer quizId) {
        return quizService.getDetails(quizId);
    }

}
