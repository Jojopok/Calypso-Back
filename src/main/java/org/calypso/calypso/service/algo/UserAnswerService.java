package org.calypso.calypso.service.algo;

import org.calypso.calypso.dto.algo.UserAnswerDTO;
import org.calypso.calypso.mapper.algo.UserAnswerMapper;
import org.calypso.calypso.model.algo.UserAnswer;
import org.calypso.calypso.repository.algo.UserAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserAnswerService {

    @Autowired
    private UserAnswerRepository userAnswerRepository;

    @Autowired
    private UserAnswerMapper userAnswerMapper;

    public List<UserAnswerDTO> getAllUserAnswers() {
        return userAnswerRepository.findAll().stream()
                .map(userAnswerMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserAnswerDTO getUserAnswerById(Long id) {
        return userAnswerRepository.findById(id)
                .map(userAnswerMapper::convertToDTO)
                .orElse(null);
    }

    public UserAnswerDTO createUserAnswer(UserAnswer userAnswer) {
        UserAnswer savedUserAnswer = userAnswerRepository.save(userAnswer);
        return userAnswerMapper.convertToDTO(savedUserAnswer);
    }

    public UserAnswerDTO updateUserAnswer(Long id, UserAnswer userAnswer) {
        if (!userAnswerRepository.existsById(id)) {
            return null;
        }
        userAnswer.setId(id);
        UserAnswer updatedUserAnswer = userAnswerRepository.save(userAnswer);
        return userAnswerMapper.convertToDTO(updatedUserAnswer);
    }

    public boolean deleteUserAnswer(Long id) {
        if (!userAnswerRepository.existsById(id)) {
            return false;
        }
        userAnswerRepository.deleteById(id);
        return true;
    }
}