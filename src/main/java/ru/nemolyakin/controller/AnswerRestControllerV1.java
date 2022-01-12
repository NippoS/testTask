package ru.nemolyakin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import ru.nemolyakin.dto.AnswerDto;
import ru.nemolyakin.model.Answer;
import ru.nemolyakin.model.Question;
import ru.nemolyakin.service.AnswerService;
import ru.nemolyakin.service.QuestionService;
import ru.nemolyakin.service.UserService;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/surveys/answers")
@Api(value = "Answer controller")
public class AnswerRestControllerV1 {

    private final QuestionService questionService;
    private final UserService userService;
    private final AnswerService answerService;

    @PostMapping("/answers")
    @ApiOperation(value = "save answer", response = AnswerDto.class)
    public ResponseEntity<AnswerDto> saveAnswer(@NonNull @RequestBody @Valid AnswerDto answerDto) {
        Question question = questionService.findById(answerDto.getQuestion().getId());
        if (question.getAnswers().size() == question.getCountAnswer()) {
            throw new IllegalArgumentException("Max answer for this question");
        }

        Answer answer = answerDto.toAnswer();
        answer.setUser(userService.findById(answerDto.getUserId()));
        answerService.save(answer);

        return new ResponseEntity<>(new AnswerDto(answer), HttpStatus.OK);
    }

    @PutMapping("/answers")
    @ApiOperation(value = "update answer", response = AnswerDto.class)
    public ResponseEntity<AnswerDto> updateAnswer(@NonNull @RequestBody @Valid AnswerDto answerDto) {
        Answer answer = answerDto.toAnswer();
        answer.setUser(userService.findById(answerDto.getUserId()));
        answerService.update(answer);

        return new ResponseEntity<>(new AnswerDto(answer), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete answer")
    public ResponseEntity<AnswerDto> deleteAnswer(@NonNull @PathVariable("id") Long id) {
        answerService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
