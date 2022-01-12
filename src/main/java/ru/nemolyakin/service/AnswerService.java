package ru.nemolyakin.service;

import ru.nemolyakin.model.Answer;
import ru.nemolyakin.model.User;

import java.util.List;

public interface AnswerService{

    Answer save(Answer answer);

    void delete(Long id);

   Answer update(Answer answer);
}
