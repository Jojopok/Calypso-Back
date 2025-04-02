package org.calypso.calypso.repository.algo;

import org.calypso.calypso.model.algo.Difficulty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DifficultyRepositoryTest {

    @Autowired
    private DifficultyRepository difficultyRepository;

    @Test
    void testSaveDifficulty() {
        // Arrange
        Difficulty difficulty = new Difficulty();
        difficulty.setDifficulty("Facile");

        // Act
        Difficulty savedDifficulty = difficultyRepository.save(difficulty);

        // Assert
        assertThat(savedDifficulty).isNotNull();
        assertThat(savedDifficulty.getId()).isNotNull();
        assertThat(savedDifficulty.getDifficulty()).isEqualTo("Facile");
    }

    @Test
    void testFindById() {
        // Arrange
        Difficulty difficulty = new Difficulty();
        difficulty.setDifficulty("Moyen");
        Difficulty savedDifficulty = difficultyRepository.save(difficulty);

        // Act
        Optional<Difficulty> foundDifficulty = difficultyRepository.findById(savedDifficulty.getId());

        // Assert
        assertThat(foundDifficulty).isPresent();
        assertThat(foundDifficulty.get().getDifficulty()).isEqualTo("Moyen");
    }

    @Test
    void testDeleteDifficulty() {
        // Arrange
        Difficulty difficulty = new Difficulty();
        difficulty.setDifficulty("Difficile");
        Difficulty savedDifficulty = difficultyRepository.save(difficulty);

        // Act
        difficultyRepository.deleteById(savedDifficulty.getId());

        // Assert
        Optional<Difficulty> deletedDifficulty = difficultyRepository.findById(savedDifficulty.getId());
        assertThat(deletedDifficulty).isNotPresent();
    }
}