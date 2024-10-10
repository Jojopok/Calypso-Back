package org.calypso.calypso.mapper.algo;

import org.calypso.calypso.dto.algo.DifficultyDTO;
import org.calypso.calypso.model.algo.Difficulty;
import org.springframework.stereotype.Component;

@Component
public class DifficultyMapper {

    public DifficultyDTO convertToDTO(Difficulty difficulty) {
        DifficultyDTO difficultyDTO = new DifficultyDTO();
        difficultyDTO.setId(difficulty.getId());
        difficultyDTO.setDifficulty(difficulty.getDifficulty());
        return difficultyDTO;
    }

    public Difficulty convertToEntity(DifficultyDTO difficultyDTO) {
        Difficulty difficulty = new Difficulty();
        difficulty.setId(difficultyDTO.getId());
        difficulty.setDifficulty(difficultyDTO.getDifficulty());
        return difficulty;
    }
}