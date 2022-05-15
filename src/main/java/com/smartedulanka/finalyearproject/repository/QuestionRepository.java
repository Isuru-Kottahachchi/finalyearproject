package com.smartedulanka.finalyearproject.repository;

import com.smartedulanka.finalyearproject.datalayer.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question,Long> {
}
