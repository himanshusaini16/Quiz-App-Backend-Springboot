package com.saini.Question_service.service;


import com.saini.Question_service.dao.QuestionDao;
import com.saini.Question_service.model.Question;
import com.saini.Question_service.model.QuestionWrapper;
import com.saini.Question_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    private QuestionDao questionDao;

    // In your QuestionService
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> questions = questionDao.findAllByOrderByIdAsc();  // Get sorted questions
        return ResponseEntity.ok(questions);
    }


    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category),HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return  new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {// Ensure ID is null so that PostgreSQL auto-generates it
        questionDao.save(question);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    public ResponseEntity<String> updateQuestion(Question question) {
        if (question.getId() == null) {
            return new ResponseEntity<>("id not found",HttpStatus.NOT_FOUND);
        }

        Optional<Question> existingQuestion = questionDao.findById(question.getId());

        if (existingQuestion.isPresent()) {
            questionDao.save(question);
            return new ResponseEntity<>("updated",HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("id not found",HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> deleteQuestion(Integer id) {
        Optional<Question> existingQuestion = questionDao.findById(id);

        if (existingQuestion.isPresent()) {
            questionDao.deleteById(id);
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
        } else {
            return new ResponseEntity<>("id not found",HttpStatus.NOT_FOUND);

        }
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category, Integer numQ) {
        List<Integer> questions = questionDao.findRandomQuestionsByCategory(category, numQ);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromIds(List<Integer> ids) {
        List<QuestionWrapper> questionWrappers = new ArrayList<>();

       List<Question> questionList = new ArrayList<>();

       for(Integer id: ids){
           questionList.add(questionDao.findById(id).get());
       }

       for(Question question :questionList){
           QuestionWrapper wrapper = new QuestionWrapper();
           wrapper.setId(question.getId());
           wrapper.setQuestionTitle(question.getQuestionTitle());
           wrapper.setOption1(question.getOption1());
           wrapper.setOption2(question.getOption2());
           wrapper.setOption3(question.getOption3());
           wrapper.setOption4(question.getOption4());

           questionWrappers.add(wrapper);
       }

        return new ResponseEntity<>(questionWrappers, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int score = 0;

        for (Response response : responses) {
            Optional<Question> questionOptional = questionDao.findById(response.getId());
            if (questionOptional.isPresent()) {
                Question question = questionOptional.get();
                String correctAnswer = question.getRightAnswer().trim().toLowerCase();
                if (correctAnswer.equals(response.getResponse().trim().toLowerCase())) {
                    score++;
                }
            }
        }

        return new ResponseEntity<>(score, HttpStatus.OK);
    }

    public ResponseEntity<Question> getQuestionById(Integer id) {
        Optional<Question> question = questionDao.findById(id);
        return question.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
