package org.calypso.calypso.service.algo;

import org.calypso.calypso.dto.algo.DifficultyDTO;
import org.calypso.calypso.mapper.algo.DifficultyMapper;
import org.calypso.calypso.model.algo.Difficulty;
import org.calypso.calypso.repository.algo.DifficultyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DifficultyService {

    private final DifficultyRepository difficultyRepository;
    private final DifficultyMapper difficultyMapper;

    public DifficultyService(DifficultyRepository difficultyRepository, DifficultyMapper difficultyMapper) {
        this.difficultyRepository = difficultyRepository;
        this.difficultyMapper = difficultyMapper;
    }

    public List<DifficultyDTO> getAllDifficulties() {
        List<Difficulty> difficulties = difficultyRepository.findAll();
        return difficulties.stream().map(difficultyMapper::convertToDTO).collect(Collectors.toList());
    }

    public DifficultyDTO getDifficultyById(Long id) {
        Difficulty difficulty = difficultyRepository.findById(id).orElse(null);
        if (difficulty == null) {
            return null;
        }
        return difficultyMapper.convertToDTO(difficulty);
    }

    public DifficultyDTO createDifficulty(Difficulty difficulty) {
        Difficulty savedDifficulty = difficultyRepository.save(difficulty);
        return difficultyMapper.convertToDTO(savedDifficulty);
    }

    public DifficultyDTO updateDifficulty(Long id, Difficulty difficultyDetails) {
        Difficulty difficulty = difficultyRepository.findById(id).orElse(null);
        if (difficulty == null) {
            return null;
        }
        difficulty.setDifficulty(difficultyDetails.getDifficulty());
        Difficulty updatedDifficulty = difficultyRepository.save(difficulty);
        return difficultyMapper.convertToDTO(updatedDifficulty);
    }

    public boolean deleteDifficulty(Long id) {
        Difficulty difficulty = difficultyRepository.findById(id).orElse(null);
        if (difficulty == null) {
            return false;
        }
        difficultyRepository.delete(difficulty);
        return true;
    }
}