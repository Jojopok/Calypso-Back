package org.calypso.calypso.repository.algo;

import org.calypso.calypso.model.algo.Type;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TypeRepositoryTest {

    @Autowired
    private TypeRepository typeRepository;

    @Test
    void testSaveType() {
        // Arrange
        Type type = new Type();
        type.setType("Algorithm");

        // Act
        Type savedType = typeRepository.save(type);

        // Assert
        assertThat(savedType).isNotNull();
        assertThat(savedType.getId()).isNotNull();
        assertThat(savedType.getType()).isEqualTo("Algorithm");
    }

    @Test
    void testFindById() {
        // Arrange
        Type type = new Type();
        type.setType("Data Structure");
        Type savedType = typeRepository.save(type);

        // Act
        Optional<Type> foundType = typeRepository.findById(savedType.getId());

        // Assert
        assertThat(foundType).isPresent();
        assertThat(foundType.get().getType()).isEqualTo("Data Structure");
    }

    @Test
    void testDeleteType() {
        // Arrange
        Type type = new Type();
        type.setType("Database");
        Type savedType = typeRepository.save(type);

        // Act
        typeRepository.deleteById(savedType.getId());

        // Assert
        Optional<Type> deletedType = typeRepository.findById(savedType.getId());
        assertThat(deletedType).isNotPresent();
    }
}