package ru.nemolyakin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nemolyakin.model.Answer;
import ru.nemolyakin.model.Question;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnswerDto extends BaseDtoEntity {

    private String answer;
    private Question question;
    private Long userId;

    public AnswerDto(@NotNull Answer answer) {
        this.answer = answer.getAnswer();
    }

    public Answer toAnswer() {
        Answer answer = new Answer();

        answer.setId(this.id);
        answer.setAnswer(this.answer);
        answer.setQuestion(this.question);

        return answer;
    }
}
