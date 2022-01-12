package ru.nemolyakin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nemolyakin.model.Status;
import ru.nemolyakin.model.Survey;
import ru.nemolyakin.model.SurveyStatus;
import ru.nemolyakin.model.User;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SurveyDto extends BaseDtoEntity {

    private String name;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private LocalDateTime timeUpdated;
    private List<QuestionDto> questions = new ArrayList<>();
    private SurveyStatus statusSurvey;
    private Status status;

    public SurveyDto(@NotNull Survey survey) {
        this.id = survey.getId();
        this.name = survey.getName();
        this.dateStart = survey.getDateStart();
        this.dateEnd = survey.getDateEnd();
        this.timeStart = survey.getTimeStart();
        this.timeEnd = survey.getTimeEnd();
        this.timeUpdated = survey.getTimeUpdated();
        this.questions = survey.getQuestions().stream().map(QuestionDto::new).collect(Collectors.toList());
        this.statusSurvey = survey.getSurveyStatus();
        this.status = survey.getStatus();
    }

    public SurveyDto(@NotNull Survey survey, User user) {
        this.id = survey.getId();
        this.name = survey.getName();
        this.dateStart = survey.getDateStart();
        this.dateEnd = survey.getDateEnd();
        this.timeStart = survey.getTimeStart();
        this.timeEnd = survey.getTimeEnd();
        this.timeUpdated = survey.getTimeUpdated();
        this.questions = survey.getQuestions().stream().map(question -> new QuestionDto(question, user)).collect(Collectors.toList());
        this.statusSurvey = survey.getSurveyStatus();
        this.status = survey.getStatus();
    }

    public Survey toSurvey() {
        Survey survey = new Survey();

        survey.setId(this.id);
        survey.setName(this.name);
        survey.setDateStart(this.dateStart);
        survey.setDateEnd(this.dateEnd);
        survey.setTimeStart(this.timeStart);
        survey.setTimeEnd(this.timeEnd);
        survey.setTimeUpdated(this.timeUpdated);
        survey.setQuestions(this.questions.stream().map(QuestionDto::toQuestion).collect(Collectors.toList()));
        survey.setSurveyStatus(this.statusSurvey);
        survey.setStatus(this.status);

        return survey;
    }
}
