package org.calypso.calypso.controller.algo;

import org.calypso.calypso.dto.algo.UserAnswerDTO;
import org.calypso.calypso.mapper.algo.UserAnswerMapper;
import org.calypso.calypso.model.algo.UserAnswer;
import org.calypso.calypso.service.algo.UserAnswerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-answers")
public class UserAnswerController {

    private final UserAnswerService userAnswerService;
    private final UserAnswerMapper userAnswerMapper;

    public UserAnswerController(UserAnswerService userAnswerService, UserAnswerMapper userAnswerMapper) {
        this.userAnswerService = userAnswerService;
        this.userAnswerMapper = userAnswerMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserAnswerDTO>> getAllUserAnswers() {
        List<UserAnswerDTO> userAnswers = userAnswerService.getAllUserAnswers();
        if (userAnswers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(userAnswers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAnswerDTO> getUserAnswerById(@PathVariable Long id) {
        UserAnswerDTO userAnswer = userAnswerService.getUserAnswerById(id);
        if (userAnswer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userAnswer);
    }

    @PostMapping
    public ResponseEntity<UserAnswerDTO> createUserAnswer(@RequestBody UserAnswerDTO userAnswerDTO) {
        UserAnswer userAnswer = userAnswerMapper.convertToEntity(userAnswerDTO);
        UserAnswerDTO createdUserAnswerDTO = userAnswerService.createUserAnswer(userAnswer);
        if (createdUserAnswerDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(createdUserAnswerDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserAnswerDTO> updateUserAnswer(@PathVariable Long id, @RequestBody UserAnswerDTO userAnswerDTO) {
        UserAnswer userAnswer = userAnswerMapper.convertToEntity(userAnswerDTO);
        UserAnswerDTO updatedUserAnswerDTO = userAnswerService.updateUserAnswer(id, userAnswer);
        if (updatedUserAnswerDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUserAnswerDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserAnswer(@PathVariable Long id) {
        boolean isDeleted = userAnswerService.deleteUserAnswer(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}