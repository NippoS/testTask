package ru.nemolyakin.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nemolyakin.model.Answer;
import ru.nemolyakin.repository.AnswerRepository;
import ru.nemolyakin.service.impl.AnswerServiceImpl;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AnswerServiceImplTest {

    @Mock
    AnswerRepository answerRepository;

    @InjectMocks
    AnswerServiceImpl answerServiceUnderTests;

    private Answer getAnswerForTests() {
        Answer answer = new Answer();

        answer.setId(1L);
        answer.setAnswer("TestText");
        answer.setQuestion(null);
        answer.setUser(null);

        return answer;
    }


    @Test
    void testSaveIsDone() {
        when(answerRepository.save(any())).thenReturn(getAnswerForTests());
        Answer answer = answerServiceUnderTests.save(getAnswerForTests());

        assertEquals(answer.getAnswer(), "TestText");

        verify(answerRepository, times(1)).save(any(Answer.class));
    }

    @Test
    void testSaveHaveException() {
        when(answerRepository.save(any())).thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class, () -> answerServiceUnderTests.save(getAnswerForTests()));
    }

    @Test
    void testUpdateIsDone() {
        Answer answerFD = getAnswerForTests();

        when(answerRepository.findById(anyLong())).thenReturn(Optional.of(answerFD));
        when(answerRepository.save(any(Answer.class))).thenReturn(getAnswerForTests());

        Answer answerToUpdate = getAnswerForTests();
        answerToUpdate.setAnswer("TestTextUpdate");

        answerServiceUnderTests.update(answerToUpdate);

        assertEquals(answerToUpdate.getAnswer(), "TestTextUpdate");
        verify(answerRepository, times(1)).save(answerToUpdate);
    }

    @Test
    void testUpdateWhenIdIsNotFound() {
        when(answerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> answerServiceUnderTests.update(getAnswerForTests()));
    }

    @Test
    void testDeleteById() {
        when(answerRepository.findById(anyLong())).thenReturn(Optional.of(getAnswerForTests()));

        answerServiceUnderTests.delete(1L);

        verify(answerRepository, times(1)).findById(anyLong());
        verify(answerRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testDeleteByIdWhenStudentDoesNotFound() {
        when(answerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> answerServiceUnderTests.delete(1L));
    }
}
