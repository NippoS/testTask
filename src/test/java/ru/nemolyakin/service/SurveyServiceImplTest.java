package ru.nemolyakin.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nemolyakin.model.Status;
import ru.nemolyakin.model.Survey;
import ru.nemolyakin.repository.SurveyRepository;
import ru.nemolyakin.service.impl.SurveyServiceImpl;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SurveyServiceImplTest {

    @Mock
    SurveyRepository surveyRepository;

    @InjectMocks
    SurveyServiceImpl surveyServiceUnderTests;

    private Survey getSurveyForTests() {
        Survey survey = new Survey();

        survey.setId(1L);
        survey.setName("TestName");
        survey.setDateStart(LocalDate.now());
        survey.setDateEnd(survey.getDateStart().plusDays(1));
        survey.setTimeStart(LocalTime.now());
        survey.setTimeEnd(survey.getTimeStart());
        survey.setQuestions(null);
        survey.setUsers(null);
        survey.setTimeUpdated(LocalDateTime.now());
        survey.setStatus(Status.ACTIVE);

        return survey;
    }

    @Test
    public void testGetByIdIsDone(){
        when(surveyRepository.findById(any())).thenReturn(Optional.of(getSurveyForTests()));
        Survey survey = surveyServiceUnderTests.findById(1L);

        assertEquals(survey.getName(), "TestName");

        verify(surveyRepository, times(1)).findById(any(Long.class));
    }

    @Test
    public void testGetByIdIsNotDone(){
        when(surveyRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> surveyServiceUnderTests.findById(1L));
    }

    @Test
    void testSaveIsDone() {
        when(surveyRepository.save(any())).thenReturn(getSurveyForTests());
        Survey survey = surveyServiceUnderTests.save(getSurveyForTests());

        assertEquals(survey.getName(), "TestName");

        verify(surveyRepository, times(1)).save(any(Survey.class));
    }

    @Test
    void testSaveHaveException() {
        when(surveyRepository.save(any())).thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class, () -> surveyServiceUnderTests.save(getSurveyForTests()));
    }

    @Test
    void testUpdateIsDone() {
        Survey surveyFD = getSurveyForTests();

        when(surveyRepository.findById(anyLong())).thenReturn(Optional.of(surveyFD));
        when(surveyRepository.save(any(Survey.class))).thenReturn(getSurveyForTests());

        Survey surveyToUpdate = getSurveyForTests();
        surveyToUpdate.setName("TestNameUpdate");

        surveyServiceUnderTests.update(surveyToUpdate);

        assertEquals(surveyToUpdate.getName(), "TestNameUpdate");
        verify(surveyRepository, times(1)).save(surveyToUpdate);
    }

    @Test
    void testUpdateWhenIdIsNotFound() {
        when(surveyRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> surveyServiceUnderTests.update(getSurveyForTests()));
    }

    @Test
    void testDeleteById() {
        when(surveyRepository.existsById(anyLong())).thenReturn(true);
        when(surveyRepository.getById(anyLong())).thenReturn(getSurveyForTests());

        surveyServiceUnderTests.deleteById(1L);

        verify(surveyRepository, times(1)).getById(anyLong());
        verify(surveyRepository, times(1)).save(any(Survey.class));
    }

    @Test
    void testDeleteByIdWhenStudentDoesNotFound() {
        when(surveyRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> surveyServiceUnderTests.deleteById(1L));
    }
}
