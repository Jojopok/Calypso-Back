package org.calypso.calypso.mapper.algo;

import org.calypso.calypso.dto.algo.AlgoDTO;
import org.calypso.calypso.model.algo.Algo;
import org.calypso.calypso.model.algo.Difficulty;
import org.calypso.calypso.model.algo.Type;
import org.calypso.calypso.model.auth.User;
import org.calypso.calypso.repository.algo.DifficultyRepository;
import org.calypso.calypso.repository.algo.TypeRepository;
import org.calypso.calypso.repository.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AlgoMapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DifficultyRepository difficultyRepository;

    @Autowired
    private TypeRepository typeRepository;

    public Algo convertToEntity(AlgoDTO algoDTO) {
        Algo algo = new Algo();
        algo.setId(algoDTO.getId());
        algo.setTitle(algoDTO.getTitle());
        algo.setContent(algoDTO.getContent());
        algo.setAnswer(algoDTO.getAnswer());
        algo.setIsVisible(algoDTO.getIsVisible());
        algo.setCreatedAt(algoDTO.getCreatedAt());
        algo.setUpdatedAt(algoDTO.getUpdatedAt());

        // Récupérer l'utilisateur
        User user = userRepository.findById(algoDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        algo.setUser(user);

        // Récupérer la difficulté
        if (algoDTO.getDifficultyId() != null) {
            Difficulty difficulty = difficultyRepository.findById(algoDTO.getDifficultyId())
                    .orElseThrow(() -> new RuntimeException("Difficulty not found"));
            algo.setDifficulty(difficulty);
        }

        // Récupérer les types
        if (algoDTO.getTypeIds() != null && !algoDTO.getTypeIds().isEmpty()) {
            Set<Type> types = new HashSet<>(typeRepository.findAllById(algoDTO.getTypeIds()));
            algo.setTypes(types);
        }

        return algo;
    }

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

        // Associer la difficulté
        if (algo.getDifficulty() != null) {
            algoDTO.setDifficultyId(algo.getDifficulty().getId());
        }

        // Associer les types
        if (algo.getTypes() != null) {
            algoDTO.setTypeIds(algo.getTypes().stream().map(Type::getId).collect(Collectors.toSet()));
        }

        return algoDTO;
    }
}