package ru.nemolyakin.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.nemolyakin.model.*;
import ru.nemolyakin.repository.SurveyRepository;
import ru.nemolyakin.service.SurveyService;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class SurveyServiceImpl implements SurveyService {

    private final SurveyRepository surveyRepository;

    @Override
    public Survey save(Survey survey) {
        survey.setTimeUpdated(LocalDateTime.now());
        Survey surveyNew = surveyRepository.save(survey);

        log.info("IN SurveyServiceImpl save {}", surveyNew);

        return surveyNew;
    }

    @Override
    public List<Survey> findAll() {
        log.info("IN SurveyServiceImpl findAll");
        return surveyRepository.findAll();
    }

    public List<Survey> findAllActive() {
        List<Survey> surveys = surveyRepository.findAll().stream().filter(survey -> survey.getSurveyStatus().equals(SurveyStatus.OPEN)).collect(Collectors.toList());
        log.info("IN SurveyServiceImpl findAll");
        return surveys;
    }

    @Override
    public Survey findById(Long id) throws EntityNotFoundException {
        log.info("IN SurveyServiceImpl find by id {}", id);
        return surveyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Survey with id %d is not found", id)));
    }

    @Override
    public void deleteById(Long id) {
        if (!surveyRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("Survey with id %d is not found", id));
        }
        Survey surveyDelete = surveyRepository.getById(id);
        surveyDelete.setStatus(Status.DELETED);
        surveyDelete.setSurveyStatus(SurveyStatus.CLOSED);
        surveyDelete.setTimeUpdated(LocalDateTime.now());
        surveyRepository.save(surveyDelete);
        log.info("IN SurveyServiceImpl delete survey with id {}", id);
    }

    @Override
    public boolean isExists(Long id) {
        boolean result = surveyRepository.existsById(id);
        log.info("IN SurveyServiceImpl isExists - id {}, result {}", id, result);
        return result;
    }

    @Override
    public Survey update(Survey survey) {
        if (survey.getId() == null) {
            throw new NullPointerException("Id is required");
        }

        Survey surveyOld = surveyRepository.findById(survey.getId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Survey with that id %d is not found", survey.getId())));

        survey.setDateStart(surveyOld.getDateStart());
        survey.setTimeStart(surveyOld.getTimeStart());
        survey.setStatus(surveyOld.getStatus());
        survey.setSurveyStatus(surveyOld.getSurveyStatus());
        survey.setTimeUpdated(LocalDateTime.now());
        Survey surveyUpdate = surveyRepository.save(survey);

        log.info("IN SurveyServiceImpl update survey {} was updated to survey {}", surveyOld, surveyUpdate);

        return surveyUpdate;
    }

    @Override
    public List<Survey> findByUsers(User user) {
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        List<Survey> surveys = surveyRepository.findByUsers(user);

        return surveys;
    }
}
