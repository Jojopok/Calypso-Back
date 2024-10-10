package org.calypso.calypso.controller.algo;

import org.calypso.calypso.dto.algo.DifficultyDTO;
import org.calypso.calypso.model.algo.Difficulty;
import org.calypso.calypso.service.algo.DifficultyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/difficulties")
public class DifficultyController {

    private final DifficultyService difficultyService;

    public DifficultyController(DifficultyService difficultyService) {
        this.difficultyService = difficultyService;
    }

    @GetMapping
    public ResponseEntity<List<DifficultyDTO>> getAllDifficulties() {
        List<DifficultyDTO> difficulties = difficultyService.getAllDifficulties();
        if (difficulties.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(difficulties);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DifficultyDTO> getDifficultyById(@PathVariable Long id) {
        DifficultyDTO difficultyDTO = difficultyService.getDifficultyById(id);
        if (difficultyDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(difficultyDTO);
    }

    @PostMapping
    public ResponseEntity<DifficultyDTO> createDifficulty(@RequestBody DifficultyDTO difficultyDTO) {
        Difficulty difficulty = new Difficulty();
        difficulty.setDifficulty(difficultyDTO.getDifficulty());
        DifficultyDTO createdDifficultyDTO = difficultyService.createDifficulty(difficulty);
        if (createdDifficultyDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(createdDifficultyDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DifficultyDTO> updateDifficulty(@PathVariable Long id, @RequestBody DifficultyDTO difficultyDTO) {
        Difficulty difficulty = new Difficulty();
        difficulty.setDifficulty(difficultyDTO.getDifficulty());
        DifficultyDTO updatedDifficultyDTO = difficultyService.updateDifficulty(id, difficulty);
        if (updatedDifficultyDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedDifficultyDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDifficulty(@PathVariable Long id) {
        boolean isDeleted = difficultyService.deleteDifficulty(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}