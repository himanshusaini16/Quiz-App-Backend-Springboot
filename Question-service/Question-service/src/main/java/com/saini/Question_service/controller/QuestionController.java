package com.saini.Question_service.controller;


import com.saini.Question_service.model.Question;
import com.saini.Question_service.model.QuestionWrapper;
import com.saini.Question_service.model.Response;
import com.saini.Question_service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("questions")
//@CrossOrigin(origins = "http://localhost:5173")
public class QuestionController {



    @Autowired
    private QuestionService questionsService;

    @Autowired
    Environment environment;



    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionsService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {
        return questionsService.getQuestionsByCategory(category);
    }

    @PostMapping("addQuestion")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        return questionsService.addQuestion(question);
    }

    @PutMapping("updateQuestion")
    public ResponseEntity<String> updateQuestion(@RequestBody Question question) {
        return questionsService.updateQuestion(question);
    }

    @DeleteMapping("deleteQuestion/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Integer id) {
        return questionsService.deleteQuestion(id);
    }

    @GetMapping("genrateQuiz")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category, @RequestParam Integer numQ) {
        return questionsService.getQuestionsForQuiz(category, numQ);
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromIds(@RequestBody List<Integer> ids) {
        System.out.println(environment.getProperty("local.server.port"));

        return questionsService.getQuestionsFromIds(ids);
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses) {
        return questionsService.getScore(responses);
    }

    @GetMapping("getQuestion/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Integer id) {
        return questionsService.getQuestionById(id);
    }

}
