package org.calypso.calypso.service.algo;

import org.calypso.calypso.dto.algo.AlgoDTO;
import org.calypso.calypso.mapper.algo.AlgoMapper;
import org.calypso.calypso.model.algo.Algo;
import org.calypso.calypso.repository.algo.AlgoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlgoService {

    private final AlgoRepository algoRepository;
    private final AlgoMapper algoMapper;

    public AlgoService(AlgoRepository algoRepository, AlgoMapper algoMapper) {
        this.algoRepository = algoRepository;
        this.algoMapper = algoMapper;
    }

    public List<AlgoDTO> getAllAlgos() {
        List<Algo> algos = algoRepository.findAll();
        return algos.stream().map(algoMapper::convertToDTO).collect(Collectors.toList());
    }

    public AlgoDTO getAlgoById(Long id) {
        Algo algo = algoRepository.findById(id).orElse(null);
        if (algo == null) {
            return null;
        }
        return algoMapper.convertToDTO(algo);
    }

    public AlgoDTO createAlgo(Algo algo) {
        Algo savedAlgo = algoRepository.save(algo);
        return algoMapper.convertToDTO(savedAlgo);
    }

    public AlgoDTO updateAlgo(Long id, Algo algoDetails) {
        Algo algo = algoRepository.findById(id).orElse(null);
        if (algo == null) {
            return null;
        }
        algo.setTitle(algoDetails.getTitle());
        algo.setContent(algoDetails.getContent());
        algo.setAnswer(algoDetails.getAnswer());
        algo.setIsVisible(algoDetails.getIsVisible());
        algo.setCreatedAt(algoDetails.getCreatedAt());
        algo.setUpdatedAt(algoDetails.getUpdatedAt());
        Algo updatedAlgo = algoRepository.save(algo);
        return algoMapper.convertToDTO(updatedAlgo);
    }

    public boolean deleteAlgo(Long id) {
        Algo algo = algoRepository.findById(id).orElse(null);
        if (algo == null) {
            return false;
        }
        algoRepository.delete(algo);
        return true;
    }
}