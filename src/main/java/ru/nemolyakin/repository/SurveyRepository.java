package ru.nemolyakin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nemolyakin.model.Survey;
import ru.nemolyakin.model.User;

import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
    List<Survey> findByUsers(User user);
}
