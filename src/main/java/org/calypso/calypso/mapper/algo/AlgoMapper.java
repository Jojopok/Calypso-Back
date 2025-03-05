package org.calypso.calypso.mapper.algo;

import org.calypso.calypso.dto.algo.AlgoDTO;
import org.calypso.calypso.dto.algo.DifficultyDTO;
import org.calypso.calypso.dto.algo.TypeDTO;
import org.calypso.calypso.dto.algo.UserAnswerDTO;
import org.calypso.calypso.model.algo.Algo;
import org.calypso.calypso.model.algo.Difficulty;
import org.calypso.calypso.model.algo.Type;
import org.calypso.calypso.model.auth.User;
import org.calypso.calypso.repository.algo.DifficultyRepository;
import org.calypso.calypso.repository.algo.TypeRepository;
import org.calypso.calypso.repository.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AlgoMapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private DifficultyRepository difficultyRepository;

    @Autowired
    private UserAnswerMapper userAnswerMapper;

    public Algo convertToEntity(AlgoDTO algoDTO) {
        Algo algo = new Algo();
        algo.setId(algoDTO.getId());
        algo.setTitle(algoDTO.getTitle());
        algo.setContent(algoDTO.getContent());
        algo.setAnswer(algoDTO.getAnswer());
        algo.setIsVisible(algoDTO.getIsVisible());
        algo.setCreatedAt(algoDTO.getCreatedAt());
        algo.setUpdatedAt(algoDTO.getUpdatedAt());

        // Retrieve the user
        User user = userRepository.findById(algoDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        algo.setUser(user);

        // Retrieve the types
        if (algoDTO.getType() != null && !algoDTO.getType().isEmpty()) {
            Set<Type> types = algoDTO.getType().stream()
                    .map(typeDTO -> typeRepository.findById(typeDTO.getId())
                            .orElseThrow(() -> new RuntimeException("Type not found")))
                    .collect(Collectors.toSet());
            algo.setTypes(types);
        }

        // Retrieve the difficulty
        if (algoDTO.getDifficultyId() != null) {
            Difficulty difficulty = difficultyRepository.findById(algoDTO.getDifficultyId())
                    .orElseThrow(() -> new RuntimeException("Difficulty not found"));
            algo.setDifficulty(difficulty);
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

        // Associate the types
        if (algo.getTypes() != null) {
            Set<TypeDTO> typeDTOs = algo.getTypes().stream()
                    .map(this::convertTypeToDTO)
                    .collect(Collectors.toSet());
            algoDTO.setType(typeDTOs);
        }

        // Associate the user answers
        if (algo.getUserAnswers() != null) {
            Set<UserAnswerDTO> userAnswerDTOs = algo.getUserAnswers().stream()
                    .map(userAnswerMapper::convertToDTO)
                    .collect(Collectors.toSet());
            algoDTO.setUserAnswer(userAnswerDTOs);
        }

        // Associate the difficulty
        if (algo.getDifficulty() != null) {
            DifficultyDTO difficultyDTO = convertDifficultyToDTO(algo.getDifficulty());
            algoDTO.setDifficulty(Set.of(difficultyDTO));
        }

        return algoDTO;
    }

    private TypeDTO convertTypeToDTO(Type type) {
        TypeDTO typeDTO = new TypeDTO();
        typeDTO.setId(type.getId());
        typeDTO.setType(type.getType());
        typeDTO.setColor(type.getColor());
        typeDTO.setLogo(type.getLogo());
        return typeDTO;
    }

    private DifficultyDTO convertDifficultyToDTO(Difficulty difficulty) {
        DifficultyDTO difficultyDTO = new DifficultyDTO();
        difficultyDTO.setId(difficulty.getId());
        difficultyDTO.setDifficulty(difficulty.getDifficulty());
        return difficultyDTO;
    }
}