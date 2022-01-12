package ru.nemolyakin.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nemolyakin.model.Question;
import ru.nemolyakin.model.QuestionType;
import ru.nemolyakin.model.Status;
import ru.nemolyakin.repository.QuestionRepository;
import ru.nemolyakin.service.impl.QuestionServiceImpl;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceImplTest {

    @Mock
    QuestionRepository questionRepository;

    @InjectMocks
    QuestionServiceImpl questionServiceUnderTests;

    private Question getQuestionForTests() {
        Question question = new Question();

        question.setId(1L);
        question.setText("TestText");
        question.setQuestionType(QuestionType.TEXT);
        question.setCountAnswer(1);
        question.setSurveys(null);
        question.setAnswers(null);
        question.setTimeUpdated(LocalDateTime.now());
        question.setStatus(Status.ACTIVE);

        return question;
    }

    @Test
    public void testGetByIdIsDone(){
        when(questionRepository.findById(any())).thenReturn(Optional.of(getQuestionForTests()));
        Question question = questionServiceUnderTests.findById(1L);

        assertEquals(question.getText(), "TestText");

        verify(questionRepository, times(1)).findById(any(Long.class));
    }

    @Test
    public void testGetByIdIsNotDone(){
        when(questionRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> questionServiceUnderTests.findById(1L));
    }

    @Test
    void testSaveIsDone() {
        when(questionRepository.save(any())).thenReturn(getQuestionForTests());
        Question question = questionServiceUnderTests.save(getQuestionForTests());

        assertEquals(question.getText(), "TestText");

        verify(questionRepository, times(1)).save(any(Question.class));
    }

    @Test
    void testSaveHaveException() {
        when(questionRepository.save(any())).thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class, () -> questionServiceUnderTests.save(getQuestionForTests()));
    }

    @Test
    void testUpdateIsDone() {
        Question questionFD = getQuestionForTests();

        when(questionRepository.findById(anyLong())).thenReturn(Optional.of(questionFD));
        when(questionRepository.save(any(Question.class))).thenReturn(getQuestionForTests());

        Question questionToUpdate = getQuestionForTests();
        questionToUpdate.setText("TestTextUpdate");

        questionServiceUnderTests.update(questionToUpdate);

        assertEquals(questionToUpdate.getText(), "TestTextUpdate");
        verify(questionRepository, times(1)).save(questionToUpdate);
    }

    @Test
    void testUpdateWhenIdIsNotFound() {
        when(questionRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> questionServiceUnderTests.update(getQuestionForTests()));
    }

    @Test
    void testDeleteById() {
        when(questionRepository.existsById(anyLong())).thenReturn(true);
        when(questionRepository.getById(anyLong())).thenReturn(getQuestionForTests());

        questionServiceUnderTests.deleteById(1L);

        verify(questionRepository, times(1)).getById(anyLong());
        verify(questionRepository, times(1)).save(any(Question.class));
    }

    @Test
    void testDeleteByIdWhenStudentDoesNotFound() {
        when(questionRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> questionServiceUnderTests.deleteById(1L));
    }
}
