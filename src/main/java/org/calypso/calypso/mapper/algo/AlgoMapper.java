package org.calypso.calypso.mapper.algo;

import org.calypso.calypso.dto.algo.AlgoDTO;
import org.calypso.calypso.model.algo.Algo;
import org.calypso.calypso.model.auth.User;
import org.calypso.calypso.repository.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AlgoMapper {

    @Autowired
    private UserRepository userRepository;

    public AlgoDTO convertToDTO(Algo algo) {
        AlgoDTO algoDTO = new AlgoDTO();
        algoDTO.setId(algo.getId());
        algoDTO.setTitle(algo.getTitle());
        algoDTO.setContent(algo.getContent());
        algoDTO.setAnswer(algo.getAnswer());
        algoDTO.setIsVisible(algo.getIsVisible());
        algoDTO.setCreatedAt(algo.getCreatedAt());
        algoDTO.setUpdatedAt(algo.getUpdatedAt());
        algoDTO.setUserId(algo.getUser().getId());
        return algoDTO;
    }

    public Algo convertToEntity(AlgoDTO algoDTO) {
        Algo algo = new Algo();
        algo.setId(algoDTO.getId());
        algo.setTitle(algoDTO.getTitle());
        algo.setContent(algoDTO.getContent());
        algo.setAnswer(algoDTO.getAnswer());
        algo.setIsVisible(algoDTO.getIsVisible());
        algo.setCreatedAt(algoDTO.getCreatedAt());
        algo.setUpdatedAt(algoDTO.getUpdatedAt());
        User user = userRepository.findById(algoDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        algo.setUser(user);
        return algo;
    }
}