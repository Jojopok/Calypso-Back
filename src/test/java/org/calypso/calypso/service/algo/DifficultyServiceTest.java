package org.calypso.calypso.service.algo;

import org.calypso.calypso.dto.algo.DifficultyDTO;
import org.calypso.calypso.mapper.algo.DifficultyMapper;
import org.calypso.calypso.model.algo.Difficulty;
import org.calypso.calypso.repository.algo.DifficultyRepository;
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
class DifficultyServiceTest {

    @Mock
    private DifficultyRepository difficultyRepository;

    @Mock
    private DifficultyMapper difficultyMapper;

    @InjectMocks
    private DifficultyService difficultyService;

    @Test
    void testGetAllDifficulties() {
        // Arrange
        Difficulty difficulty1 = new Difficulty();
        difficulty1.setDifficulty("Easy");
        Difficulty difficulty2 = new Difficulty();
        difficulty2.setDifficulty("Hard");

        when(difficultyRepository.findAll()).thenReturn(List.of(difficulty1, difficulty2));
        when(difficultyMapper.convertToDTO(difficulty1)).thenReturn(new DifficultyDTO());
        when(difficultyMapper.convertToDTO(difficulty2)).thenReturn(new DifficultyDTO());

        // Act
        List<DifficultyDTO> difficulties = difficultyService.getAllDifficulties();

        // Assert
        assertThat(difficulties).hasSize(2);
    }

    @Test
    void testGetDifficultyById() {
        // Arrange
        Long id = 1L;
        Difficulty difficulty = new Difficulty();
        difficulty.setId(id);
        difficulty.setDifficulty("Medium");

        when(difficultyRepository.findById(id)).thenReturn(Optional.of(difficulty));
        when(difficultyMapper.convertToDTO(difficulty)).thenReturn(new DifficultyDTO());

        // Act
        DifficultyDTO difficultyDTO = difficultyService.getDifficultyById(id);

        // Assert
        assertThat(difficultyDTO).isNotNull();
    }

    @Test
    void testCreateDifficulty() {
        // Arrange
        Difficulty difficulty = new Difficulty();
        difficulty.setDifficulty("New Difficulty");

        when(difficultyRepository.save(difficulty)).thenReturn(difficulty);
        when(difficultyMapper.convertToDTO(difficulty)).thenReturn(new DifficultyDTO());

        // Act
        DifficultyDTO difficultyDTO = difficultyService.createDifficulty(difficulty);

        // Assert
        assertThat(difficultyDTO).isNotNull();
    }

    @Test
    void testUpdateDifficulty() {
        // Arrange
        Long id = 1L;
        Difficulty existingDifficulty = new Difficulty();
        existingDifficulty.setId(id);
        existingDifficulty.setDifficulty("Existing Difficulty");

        Difficulty updatedDifficultyDetails = new Difficulty();
        updatedDifficultyDetails.setDifficulty("Updated Difficulty");

        when(difficultyRepository.findById(id)).thenReturn(Optional.of(existingDifficulty));
        when(difficultyRepository.save(existingDifficulty)).thenReturn(existingDifficulty);
        when(difficultyMapper.convertToDTO(existingDifficulty)).thenReturn(new DifficultyDTO());

        // Act
        DifficultyDTO updatedDifficultyDTO = difficultyService.updateDifficulty(id, updatedDifficultyDetails);

        // Assert
        assertThat(updatedDifficultyDTO).isNotNull();
    }

    @Test
    void testDeleteDifficulty() {
        // Arrange
        Long id = 1L;
        when(difficultyRepository.existsById(id)).thenReturn(true);

        // Act
        boolean isDeleted = difficultyService.deleteDifficulty(id);

        // Assert
        assertThat(isDeleted).isTrue();
        verify(difficultyRepository, times(1)).deleteById(id);
    }
}