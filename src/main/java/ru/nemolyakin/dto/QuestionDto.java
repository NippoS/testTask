package ru.nemolyakin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nemolyakin.model.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionDto extends BaseDtoEntity{

    private String text;
    private int countAnswer;
    private List<AnswerDto> answers = new ArrayList<>();
    private LocalDateTime timeUpdated;
    private QuestionType questionType;
    private Status status;

    public QuestionDto(@NotNull Question question) {
        this.id = question.getId();
        this.text = question.getText();
        this.countAnswer = question.getCountAnswer();
        this.timeUpdated = question.getTimeUpdated();
        this.questionType = question.getQuestionType();
        this.status = question.getStatus();
    }

    public QuestionDto(@NotNull Question question, User user) {
        this.id = question.getId();
        this.text = question.getText();
        this.countAnswer = question.getCountAnswer();
        this.answers = user.getAnswers().stream()
                .filter(answer -> answer.getQuestion().equals(question))
                .map(AnswerDto::new)
                .collect(Collectors.toList());
        this.timeUpdated = question.getTimeUpdated();
        this.questionType = question.getQuestionType();
        this.status = question.getStatus();
    }

    public Question toQuestion() {
        Question question = new Question();

        question.setId(this.id);
        question.setText(this.text);
        question.setCountAnswer(this.countAnswer);
        question.setTimeUpdated(this.timeUpdated);
        question.setQuestionType(this.questionType);
        question.setStatus(this.status);

        return question;
    }
}
