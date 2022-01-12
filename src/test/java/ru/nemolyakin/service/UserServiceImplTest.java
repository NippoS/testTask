package ru.nemolyakin.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.nemolyakin.model.User;
import ru.nemolyakin.model.UserStatus;
import ru.nemolyakin.repository.RoleRepository;
import ru.nemolyakin.repository.UserRepository;
import ru.nemolyakin.service.impl.UserServiceImpl;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserServiceImpl userServiceUnderTests;

    private User getUserForTests() {
        User user = new User();

        user.setId(1L);
        user.setFirstName("TestFirstName");
        user.setLastName("TestLastName");
        user.setUsername("TestUsername");
        user.setPassword("TestPass");
        user.setRoles(null);
        user.setAnswers(null);
        user.setCompletedSurveys(null);
        user.setCreated(LocalDateTime.now());
        user.setUpdated(user.getCreated());
        user.setUserStatus(UserStatus.ACTIVE);

        return user;
    }

    @Test
    public void testGetByIdIsDone(){
        when(userRepository.findById(any())).thenReturn(Optional.of(getUserForTests()));
        User user = userServiceUnderTests.findById(1L);

        assertEquals(user.getLastName(), "TestLastName");

        verify(userRepository, times(1)).findById(any(Long.class));
    }

    @Test
    public void testGetByIdIsNotDone(){
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        User user = userServiceUnderTests.findById(1L);

        verify(userRepository, times(1)).findById(any(Long.class));
    }

    @Test
    public void testGetByUsernameIsDone(){
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(getUserForTests()));

        User user = userServiceUnderTests.findByUsername("TestUsername");

        assertEquals(user.getLastName(), "TestLastName");

        verify(userRepository, times(1)).findByUsername(any(String.class));
    }

    @Test
    public void testGetByUsernameIsNotFound() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userServiceUnderTests.findByUsername("Test"));
    }

    @Test
    void testSaveIsDone() {
        when(userRepository.save(any())).thenReturn(getUserForTests());

        User userNew = userServiceUnderTests.save(getUserForTests());

        assertEquals(userNew.getLastName(), "TestLastName");

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testSaveHaveException() {
        when(userRepository.save(any())).thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class, () -> userServiceUnderTests.save(getUserForTests()));
    }

    @Test
    void testUpdateIsDone() {
        User userFD = getUserForTests();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userFD));
        when(userRepository.save(any(User.class))).thenReturn(getUserForTests());

        User userToUpdate = getUserForTests();
        userToUpdate.setLastName("TestLastNameUpdated");

        userServiceUnderTests.update(userToUpdate);

        assertEquals(userToUpdate.getLastName(), "TestLastNameUpdated");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateWhenIdIsNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userServiceUnderTests.update(getUserForTests()));
    }

    @Test
    void testDeleteById() {
        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(getUserForTests()));

        userServiceUnderTests.deleteById(1L);

        verify(userRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testDeleteByIdWhenStudentDoesNotFound() {
        when(userRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> userServiceUnderTests.deleteById(1L));
    }
}
