package com.saini.Question_service.dao;


import com.saini.Question_service.model.Question;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question,Integer> {
    @Query("SELECT q FROM Question q WHERE LOWER(q.category) = LOWER(:category)")
    List<Question> findByCategory(@Param("category") String category);

    @Query(value = "SELECT q.id FROM Question q WHERE q.category = :category ORDER BY RANDOM() LIMIT :numQ", nativeQuery = true)
    List<Integer> findRandomQuestionsByCategory(String category, int numQ);

    List<Question> findAllByOrderByIdAsc();
}
