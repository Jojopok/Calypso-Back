package org.calypso.calypso.service.algo;

import org.calypso.calypso.dto.algo.AlgoDTO;
import org.calypso.calypso.mapper.algo.AlgoMapper;
import org.calypso.calypso.model.algo.Algo;
import org.calypso.calypso.repository.algo.AlgoRepository;
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
class AlgoServiceTest {

    @Mock
    private AlgoRepository algoRepository;

    @Mock
    private AlgoMapper algoMapper;

    @InjectMocks
    private AlgoService algoService;

    @Test
    void testGetAllAlgos() {
        // Arrange
        Algo algo1 = new Algo();
        algo1.setTitle("Algo 1");
        Algo algo2 = new Algo();
        algo2.setTitle("Algo 2");

        when(algoRepository.findAll()).thenReturn(List.of(algo1, algo2));
        when(algoMapper.convertToDTO(algo1)).thenReturn(new AlgoDTO());
        when(algoMapper.convertToDTO(algo2)).thenReturn(new AlgoDTO());

        // Act
        List<AlgoDTO> algos = algoService.getAllAlgos();

        // Assert
        assertThat(algos).hasSize(2);
    }

    @Test
    void testGetAlgoById() {
        // Arrange
        Long id = 1L;
        Algo algo = new Algo();
        algo.setId(id);
        algo.setTitle("Test Algo");

        when(algoRepository.findById(id)).thenReturn(Optional.of(algo));
        when(algoMapper.convertToDTO(algo)).thenReturn(new AlgoDTO());

        // Act
        AlgoDTO algoDTO = algoService.getAlgoById(id);

        // Assert
        assertThat(algoDTO).isNotNull();
    }

    @Test
    void testCreateAlgo() {
        // Arrange
        Algo algo = new Algo();
        algo.setTitle("New Algo");

        when(algoRepository.save(algo)).thenReturn(algo);
        when(algoMapper.convertToDTO(algo)).thenReturn(new AlgoDTO());

        // Act
        AlgoDTO algoDTO = algoService.createAlgo(algo);

        // Assert
        assertThat(algoDTO).isNotNull();
    }

    @Test
    void testUpdateAlgo() {
        // Arrange
        Long id = 1L;
        Algo existingAlgo = new Algo();
        existingAlgo.setId(id);
        existingAlgo.setTitle("Existing Algo");

        Algo updatedAlgoDetails = new Algo();
        updatedAlgoDetails.setTitle("Updated Algo");

        when(algoRepository.findById(id)).thenReturn(Optional.of(existingAlgo));
        when(algoRepository.save(existingAlgo)).thenReturn(existingAlgo);
        when(algoMapper.convertToDTO(existingAlgo)).thenReturn(new AlgoDTO());

        // Act
        AlgoDTO updatedAlgoDTO = algoService.updateAlgo(id, updatedAlgoDetails);

        // Assert
        assertThat(updatedAlgoDTO).isNotNull();
    }

    @Test
    void testDeleteAlgo() {
        // Arrange
        Long id = 1L;
        Algo algo = new Algo();
        algo.setId(id);

        when(algoRepository.findById(id)).thenReturn(Optional.of(algo));

        // Act
        boolean isDeleted = algoService.deleteAlgo(id);

        // Assert
        assertThat(isDeleted).isTrue();
        verify(algoRepository, times(1)).delete(algo);
    }
}