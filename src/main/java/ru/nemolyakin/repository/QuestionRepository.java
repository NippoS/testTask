package ru.nemolyakin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nemolyakin.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
