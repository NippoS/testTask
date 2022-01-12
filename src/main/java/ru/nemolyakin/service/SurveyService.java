package ru.nemolyakin.service;

import ru.nemolyakin.model.Survey;
import ru.nemolyakin.model.User;

import java.util.List;

public interface SurveyService extends GenericService <Survey, Long> {

    List<Survey> findAllActive ();

    List<Survey> findByUsers (User user);
}
