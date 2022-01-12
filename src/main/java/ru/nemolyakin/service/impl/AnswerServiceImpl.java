package ru.nemolyakin.service.impl;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nemolyakin.model.Answer;
import ru.nemolyakin.model.Question;
import ru.nemolyakin.repository.AnswerRepository;
import ru.nemolyakin.repository.QuestionRepository;
import ru.nemolyakin.service.AnswerService;

import javax.persistence.EntityNotFoundException;

@Slf4j
@Service
@AllArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    @SneakyThrows
    @Override
    public Answer save(Answer answer) {
        Answer answerNew = answerRepository.save(answer);

        log.info("IN AnswerServiceImpl save {}", answerNew);

        return answerNew;
    }

    @Override
    public void delete(Long id) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Answer with id %d is not found", id)));

        answerRepository.deleteById(id);

        log.info("IN AnswerServiceImpl delete answer with id {}", id);
    }

    @Override
    public Answer update(Answer answer) {
        Answer answerUpdate = answerRepository.findById(answer.getId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Answer with id %d is not found", answer.getId())));
        answerRepository.save(answer);

        log.info("IN AnswerServiceImpl update answer update to {}", answerUpdate);

        return answerUpdate;
    }
}
