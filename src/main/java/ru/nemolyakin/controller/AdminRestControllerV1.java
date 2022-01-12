package ru.nemolyakin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import ru.nemolyakin.dto.QuestionDto;
import ru.nemolyakin.dto.SurveyDto;
import ru.nemolyakin.dto.UserDto;
import ru.nemolyakin.model.Question;
import ru.nemolyakin.model.Survey;
import ru.nemolyakin.model.User;
import ru.nemolyakin.service.QuestionService;
import ru.nemolyakin.service.SurveyService;
import ru.nemolyakin.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/admin")
@Api(value = "Admin controller")
public class AdminRestControllerV1 {

    private final UserService userService;
    private final QuestionService questionService;
    private final SurveyService surveyService;

    @GetMapping("/surveys/{id}")
    @ApiOperation(value = "show survey by id", response = SurveyDto.class)
    public ResponseEntity<SurveyDto> getSurveyById(@NonNull @PathVariable("id") Long id) {
        Survey survey = surveyService.findById(id);
        return new ResponseEntity<>(new SurveyDto(survey), HttpStatus.OK);
    }

    @GetMapping("/questions/{id}")
    @ApiOperation(value = "show question by id", response = QuestionDto.class)
    public ResponseEntity<QuestionDto> getQuestionById(@NonNull @PathVariable("id") Long id) {
        Question question = questionService.findById(id);
        return new ResponseEntity<>(new QuestionDto(question), HttpStatus.OK);
    }

    @GetMapping("/surveys")
    @ApiOperation(value = "show all surveys", response = Iterable.class)
    public ResponseEntity<List<SurveyDto>> getAllSurveys() {
        List<Survey> surveys = surveyService.findAll();

        if (CollectionUtils.isEmpty(surveys)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<SurveyDto> surveyDtos = surveys.stream().map(SurveyDto::new).collect(Collectors.toList());

        return new ResponseEntity<>(surveyDtos, HttpStatus.OK);
    }

    @GetMapping("/questions")
    @ApiOperation(value = "show all questions", response = Iterable.class)
    public ResponseEntity<List<QuestionDto>> getAllQuestions() {
        List<Question> questions = questionService.findAll();

        if (CollectionUtils.isEmpty(questions)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<QuestionDto> questionDtos = questions.stream().map(QuestionDto::new).collect(Collectors.toList());

        return new ResponseEntity<>(questionDtos, HttpStatus.OK);
    }

    @GetMapping("/users")
    @ApiOperation(value = "show all users", response = Iterable.class)
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userService.findAll();

        if (CollectionUtils.isEmpty(users)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<UserDto> userDtos = users.stream().map(UserDto::new).collect(Collectors.toList());

        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @PostMapping("/surveys")
    @ApiOperation(value = "create survey", response = SurveyDto.class)
    public ResponseEntity<SurveyDto> saveSurvey(@NonNull @RequestBody @Valid SurveyDto surveyDto) {
        Survey survey = surveyDto.toSurvey();
        surveyService.save(survey);
        return new ResponseEntity<>(new SurveyDto(survey), HttpStatus.CREATED);
    }

    @PostMapping("/questions")
    @ApiOperation(value = "create question", response = QuestionDto.class)
    public ResponseEntity<QuestionDto> saveQuestion(@NonNull @RequestBody @Valid QuestionDto questionDto) {
        Question question = questionDto.toQuestion();
        questionService.save(question);
        return new ResponseEntity<>(new QuestionDto(question), HttpStatus.CREATED);
    }

    @PutMapping("/surveys")
    @ApiOperation(value = "update survey", response = SurveyDto.class)
    public ResponseEntity<SurveyDto> updateSurvey(@NonNull @RequestBody @Valid SurveyDto surveyDto) {
        Survey survey = surveyDto.toSurvey();
        Survey surveyUpdate = surveyService.update(survey);
        return new ResponseEntity<>(new SurveyDto(surveyUpdate), HttpStatus.OK);
    }

    @PutMapping("/questions")
    @ApiOperation(value = "update question", response = QuestionDto.class)
    public ResponseEntity<QuestionDto> updateQuestion(@NonNull @RequestBody @Valid QuestionDto questionDto) {
        Question question = questionDto.toQuestion();
        Question questionUpdate = questionService.update(question);
        return new ResponseEntity<>(new QuestionDto(questionUpdate), HttpStatus.OK);
    }

    @DeleteMapping("/surveys/{id}")
    @ApiOperation(value = "delete survey by id")
    public ResponseEntity<SurveyDto> deleteSurveyById(@NonNull @PathVariable("id") Long id) {
        surveyService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/questions/{id}")
    @ApiOperation(value = "delete question by id")
    public ResponseEntity<QuestionDto> deleteQuestionById(@NonNull @PathVariable("id") Long id) {
        questionService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
