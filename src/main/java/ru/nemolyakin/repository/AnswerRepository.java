package ru.nemolyakin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nemolyakin.model.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
