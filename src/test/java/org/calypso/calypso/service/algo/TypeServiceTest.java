package org.calypso.calypso.service.algo;

import org.calypso.calypso.dto.algo.TypeDTO;
import org.calypso.calypso.mapper.algo.TypeMapper;
import org.calypso.calypso.model.algo.Type;
import org.calypso.calypso.repository.algo.TypeRepository;
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
class TypeServiceTest {

    @Mock
    private TypeRepository typeRepository;

    @Mock
    private TypeMapper typeMapper;

    @InjectMocks
    private TypeService typeService;

    @Test
    void testGetAllTypes() {
        // Arrange
        Type type1 = new Type();
        type1.setType("Type 1");
        Type type2 = new Type();
        type2.setType("Type 2");

        when(typeRepository.findAll()).thenReturn(List.of(type1, type2));
        when(typeMapper.convertToDTO(type1)).thenReturn(new TypeDTO());
        when(typeMapper.convertToDTO(type2)).thenReturn(new TypeDTO());

        // Act
        List<TypeDTO> types = typeService.getAllTypes();

        // Assert
        assertThat(types).hasSize(2);
    }

    @Test
    void testGetTypeById() {
        // Arrange
        Long id = 1L;
        Type type = new Type();
        type.setId(id);
        type.setType("Test Type");

        when(typeRepository.findById(id)).thenReturn(Optional.of(type));
        when(typeMapper.convertToDTO(type)).thenReturn(new TypeDTO());

        // Act
        TypeDTO typeDTO = typeService.getTypeById(id);

        // Assert
        assertThat(typeDTO).isNotNull();
    }

    @Test
    void testCreateType() {
        // Arrange
        Type type = new Type();
        type.setType("New Type");

        when(typeRepository.save(type)).thenReturn(type);
        when(typeMapper.convertToDTO(type)).thenReturn(new TypeDTO());

        // Act
        TypeDTO typeDTO = typeService.createType(type);

        // Assert
        assertThat(typeDTO).isNotNull();
    }

    @Test
    void testUpdateType() {
        // Arrange
        Long id = 1L;
        Type existingType = new Type();
        existingType.setId(id);
        existingType.setType("Existing Type");

        Type updatedTypeDetails = new Type();
        updatedTypeDetails.setType("Updated Type");

        when(typeRepository.findById(id)).thenReturn(Optional.of(existingType));
        when(typeRepository.save(existingType)).thenReturn(existingType);
        when(typeMapper.convertToDTO(existingType)).thenReturn(new TypeDTO());

        // Act
        TypeDTO updatedTypeDTO = typeService.updateType(id, updatedTypeDetails);

        // Assert
        assertThat(updatedTypeDTO).isNotNull();
    }

    @Test
    void testDeleteType() {
        // Arrange
        Long id = 1L;
        when(typeRepository.existsById(id)).thenReturn(true);

        // Act
        boolean isDeleted = typeService.deleteType(id);

        // Assert
        assertThat(isDeleted).isTrue();
        verify(typeRepository, times(1)).deleteById(id);
    }
}