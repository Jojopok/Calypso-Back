package org.calypso.calypso.mapper.algo;

import org.calypso.calypso.dto.algo.UserAnswerDTO;
import org.calypso.calypso.model.algo.UserAnswer;
import org.calypso.calypso.model.algo.Algo;
import org.calypso.calypso.model.auth.User;
import org.calypso.calypso.repository.algo.AlgoRepository;
import org.calypso.calypso.repository.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserAnswerMapper {

    @Autowired
    private AlgoRepository algoRepository;

    @Autowired
    private UserRepository userRepository;

    public UserAnswerDTO convertToDTO(UserAnswer userAnswer) {
        UserAnswerDTO userAnswerDTO = new UserAnswerDTO();
        userAnswerDTO.setId(userAnswer.getId());
        userAnswerDTO.setContent(userAnswer.getContent());
        userAnswerDTO.setIsRight(userAnswer.getIsRight());
        userAnswerDTO.setAlgoId(userAnswer.getAlgo().getId());
        userAnswerDTO.setUserId(userAnswer.getUser().getId());
        return userAnswerDTO;
    }

    public UserAnswer convertToEntity(UserAnswerDTO userAnswerDTO) {
        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setId(userAnswerDTO.getId());
        userAnswer.setContent(userAnswerDTO.getContent());
        userAnswer.setIsRight(userAnswerDTO.getIsRight());
        Algo algo = algoRepository.findById(userAnswerDTO.getAlgoId()).orElseThrow(() -> new RuntimeException("Algo not found"));
        userAnswer.setAlgo(algo);
        User user = userRepository.findById(userAnswerDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        userAnswer.setUser(user);
        return userAnswer;
    }
}