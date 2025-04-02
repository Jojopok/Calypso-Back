package org.calypso.calypso.service.algo;

import org.calypso.calypso.dto.algo.BugDTO;
import org.calypso.calypso.mapper.algo.BugMapper;
import org.calypso.calypso.model.algo.Bug;
import org.calypso.calypso.repository.algo.BugRepository;
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
class BugServiceTest {

    @Mock
    private BugRepository bugRepository;

    @Mock
    private BugMapper bugMapper;

    @InjectMocks
    private BugService bugService;

    @Test
    void testGetAllBugs() {
        // Arrange
        Bug bug1 = new Bug();
        bug1.setContent("Bug 1");
        Bug bug2 = new Bug();
        bug2.setContent("Bug 2");

        when(bugRepository.findAll()).thenReturn(List.of(bug1, bug2));
        when(bugMapper.convertToDTO(bug1)).thenReturn(new BugDTO());
        when(bugMapper.convertToDTO(bug2)).thenReturn(new BugDTO());

        // Act
        List<BugDTO> bugs = bugService.getAllBugs();

        // Assert
        assertThat(bugs).hasSize(2);
    }

    @Test
    void testGetBugById() {
        // Arrange
        Long id = 1L;
        Bug bug = new Bug();
        bug.setId(id);
        bug.setContent("Test Bug");

        when(bugRepository.findById(id)).thenReturn(Optional.of(bug));
        when(bugMapper.convertToDTO(bug)).thenReturn(new BugDTO());

        // Act
        BugDTO bugDTO = bugService.getBugById(id);

        // Assert
        assertThat(bugDTO).isNotNull();
    }

    @Test
    void testCreateBug() {
        // Arrange
        Bug bug = new Bug();
        bug.setContent("New Bug");

        when(bugRepository.save(bug)).thenReturn(bug);
        when(bugMapper.convertToDTO(bug)).thenReturn(new BugDTO());

        // Act
        BugDTO bugDTO = bugService.createBug(bug);

        // Assert
        assertThat(bugDTO).isNotNull();
    }

    @Test
    void testUpdateBug() {
        // Arrange
        Long id = 1L;
        Bug existingBug = new Bug();
        existingBug.setId(id);
        existingBug.setContent("Existing Bug");

        Bug updatedBugDetails = new Bug();
        updatedBugDetails.setContent("Updated Bug");

        when(bugRepository.findById(id)).thenReturn(Optional.of(existingBug));
        when(bugRepository.save(existingBug)).thenReturn(existingBug);
        when(bugMapper.convertToDTO(existingBug)).thenReturn(new BugDTO());

        // Act
        BugDTO updatedBugDTO = bugService.updateBug(id, updatedBugDetails);

        // Assert
        assertThat(updatedBugDTO).isNotNull();
    }

    @Test
    void testDeleteBug() {
        // Arrange
        Long id = 1L;
        when(bugRepository.existsById(id)).thenReturn(true);

        // Act
        boolean isDeleted = bugService.deleteBug(id);

        // Assert
        assertThat(isDeleted).isTrue();
        verify(bugRepository, times(1)).deleteById(id);
    }
}