package ru.nemolyakin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import ru.nemolyakin.dto.SurveyDto;
import ru.nemolyakin.model.Survey;
import ru.nemolyakin.model.User;
import ru.nemolyakin.service.SurveyService;
import ru.nemolyakin.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/surveys")
@Api(value = "Survey controller")
public class SurveyRestControllerV1 {

    private final SurveyService surveyService;
    private final UserService userService;

    @GetMapping("/surveys")
    @ApiOperation(value = "get all open surveys", response = Iterable.class)
    public ResponseEntity<List<SurveyDto>> getAllSurveys() {
        List<Survey> surveys = surveyService.findAllActive();

        if (CollectionUtils.isEmpty(surveys)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<SurveyDto> surveyDtos = surveys.stream().map(SurveyDto::new).collect(Collectors.toList());

        return new ResponseEntity<>(surveyDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}/surveys")
    @ApiOperation(value = "get all surveys by user id", response = Iterable.class)
    public ResponseEntity<List<SurveyDto>> getAllSurveysByUserId(@NonNull @PathVariable("id") Long id) {

        User user = userService.findById(id);
        List<Survey> surveys = user.getCompletedSurveys();

        if (CollectionUtils.isEmpty(surveys)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<SurveyDto> surveyDtos = surveys.stream().map(survey -> new SurveyDto(survey, user)).collect(Collectors.toList());

        return new ResponseEntity<>(surveyDtos, HttpStatus.OK);
    }
}
