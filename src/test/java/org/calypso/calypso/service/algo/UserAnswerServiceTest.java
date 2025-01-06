package org.calypso.calypso.service.algo;

import org.calypso.calypso.dto.algo.UserAnswerDTO;
import org.calypso.calypso.mapper.algo.UserAnswerMapper;
import org.calypso.calypso.model.algo.UserAnswer;
import org.calypso.calypso.repository.algo.UserAnswerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserAnswerServiceTest {

    @Mock
    private UserAnswerRepository userAnswerRepository;

    @Mock
    private UserAnswerMapper userAnswerMapper;

    @InjectMocks
    private UserAnswerService userAnswerService;

    @Test
    void testGetAllUserAnswers() {
        // Arrange
        UserAnswer userAnswer1 = new UserAnswer();
        userAnswer1.setContent("Answer 1");
        UserAnswer userAnswer2 = new UserAnswer();
        userAnswer2.setContent("Answer 2");

        when(userAnswerRepository.findAll()).thenReturn(List.of(userAnswer1, userAnswer2));
        when(userAnswerMapper.convertToDTO(userAnswer1)).thenReturn(new UserAnswerDTO());
        when(userAnswerMapper.convertToDTO(userAnswer2)).thenReturn(new UserAnswerDTO());

        // Act
        List<UserAnswerDTO> userAnswers = userAnswerService.getAllUserAnswers();

        // Assert
        assertThat(userAnswers).hasSize(2);
    }

    @Test
    void testGetUserAnswerById() {
        // Arrange
        Long id = 1L;
        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setId(id);
        userAnswer.setContent("Test Answer");

        when(userAnswerRepository.findById(id)).thenReturn(Optional.of(userAnswer));
        when(userAnswerMapper.convertToDTO(userAnswer)).thenReturn(new UserAnswerDTO());

        // Act
        UserAnswerDTO userAnswerDTO = userAnswerService.getUserAnswerById(id);

        // Assert
        assertThat(userAnswerDTO).isNotNull();
    }

    @Test
    void testCreateUserAnswer() {
        // Arrange
        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setContent("New Answer");

        when(userAnswerRepository.save(userAnswer)).thenReturn(userAnswer);
        when(userAnswerMapper.convertToDTO(userAnswer)).thenReturn(new UserAnswerDTO());

        // Act
        UserAnswerDTO userAnswerDTO = userAnswerService.createUserAnswer(userAnswer);

        // Assert
        assertThat(userAnswerDTO).isNotNull();
    }

    @Test
    void testUpdateUserAnswer() {
        // Arrange
        Long id = 1L;
        UserAnswer existingUserAnswer = new UserAnswer();
        existingUserAnswer.setId(id);
        existingUserAnswer.setContent("Existing Answer");

        UserAnswer updatedUserAnswerDetails = new UserAnswer();
        updatedUserAnswerDetails.setContent("Updated Answer");

        when(userAnswerRepository.existsById(id)).thenReturn(true);
        doReturn(existingUserAnswer).when(userAnswerRepository).save(any(UserAnswer.class));
        when(userAnswerMapper.convertToDTO(existingUserAnswer)).thenReturn(new UserAnswerDTO());

        // Act
        UserAnswerDTO updatedUserAnswerDTO = userAnswerService.updateUserAnswer(id, updatedUserAnswerDetails);

        // Assert
        assertThat(updatedUserAnswerDTO).isNotNull();
    }

    @Test
    void testDeleteUserAnswer() {
        // Arrange
        Long id = 1L;
        when(userAnswerRepository.existsById(id)).thenReturn(true);

        // Act
        boolean isDeleted = userAnswerService.deleteUserAnswer(id);

        // Assert
        assertThat(isDeleted).isTrue();
        verify(userAnswerRepository, times(1)).deleteById(id);
    }
}