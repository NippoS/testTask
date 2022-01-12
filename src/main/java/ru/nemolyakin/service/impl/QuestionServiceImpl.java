package ru.nemolyakin.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nemolyakin.model.Question;
import ru.nemolyakin.model.Status;
import ru.nemolyakin.repository.QuestionRepository;
import ru.nemolyakin.service.QuestionService;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    @Override
    public Question save(Question question) {
        question.setTimeUpdated(LocalDateTime.now());
        Question questionNew = questionRepository.save(question);

        log.info("IN QuestionServiceImpl save {}", questionNew);

        return questionNew;
    }

    @Override
    public List<Question> findAll() {
        log.info("IN QuestionServiceImpl findAll");
        return questionRepository.findAll();
    }

    @Override
    public Question findById(Long id) throws EntityNotFoundException {
        log.info("IN QuestionServiceImpl find by id {}", id);
        return questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Question with id %d is not found", id)));
    }

    @Override
    public void deleteById(Long id) {
        if (!questionRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("Question with id %d is not found", id));
        }
        Question questionDelete = questionRepository.getById(id);
        questionDelete.setStatus(Status.DELETED);
        questionDelete.setTimeUpdated(LocalDateTime.now());
        questionRepository.save(questionDelete);
        log.info("IN QuestionServiceImpl delete question with id {}", id);
    }

    @Override
    public boolean isExists(Long id) {
        boolean result = questionRepository.existsById(id);
        log.info("IN QuestionServiceImpl isExists - id {}, result {}", id, result);
        return result;
    }

    @Override
    public Question update(Question question) {
        if (question.getId() == null) {
            throw new NullPointerException("Id is required");
        }

        Question questionOld = questionRepository.findById(question.getId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Question with that id %d is not found", question.getId())));

        question.setStatus(questionOld.getStatus());
        question.setTimeUpdated(LocalDateTime.now());
        Question questionUpdate = questionRepository.save(question);

        log.info("IN QuestionServiceImpl update question {} was updated to question {}", questionOld, questionUpdate);

        return questionUpdate;
    }
}
