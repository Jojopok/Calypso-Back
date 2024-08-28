package org.calypso.calypso.controller.algo;

import org.calypso.calypso.dto.algo.AlgoDTO;
import org.calypso.calypso.model.algo.Algo;
import org.calypso.calypso.repository.algo.AlgoRepository;
import org.calypso.calypso.service.algo.AlgoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/algos")
public class AlgoController {
    private final AlgoRepository algoRepository;
    private final AlgoService algoService;

    public AlgoController(AlgoRepository algoRepository, AlgoService algoService) {
        this.algoRepository = algoRepository;
        this.algoService = algoService;
    }

    private AlgoDTO convertToDTO(Algo algo) {
        AlgoDTO algoDTO = new AlgoDTO();
        algoDTO.setId(algo.getId());
        algoDTO.setTitle(algo.getTitle());
        algoDTO.setContent(algo.getContent());
        algoDTO.setAnswer(algo.getAnswer());
        algoDTO.setIsVisible(algo.getIsVisible());
        algoDTO.setCreatedAt(algo.getCreatedAt());
        algoDTO.setUpdatedAt(algo.getUpdatedAt());
        return algoDTO;
    }

    @GetMapping
    public ResponseEntity<List<AlgoDTO>> getAllAlgos() {
        List<Algo> algos = algoRepository.findAll();
        if (algos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<AlgoDTO> algoDTOs = algos.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(algoDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlgoDTO> getAlgoById(@PathVariable Long id) {
        Algo algo = algoRepository.findById(id).orElse(null);
        if (algo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToDTO(algo));
    }

    @PostMapping
    public ResponseEntity<AlgoDTO> createAlgo(@RequestBody AlgoDTO algoDTO) {
        Algo algo = new Algo();
        algo.setTitle(algoDTO.getTitle());
        algo.setContent(algoDTO.getContent());
        algo.setAnswer(algoDTO.getAnswer());
        algo.setIsVisible(algoDTO.getIsVisible());
        algo.setCreatedAt(algoDTO.getCreatedAt());
        algo.setUpdatedAt(algoDTO.getUpdatedAt());
        AlgoDTO createdAlgoDTO = algoService.createAlgo(algo);
        if (createdAlgoDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(createdAlgoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlgoDTO> updateAlgo(@PathVariable Long id, @RequestBody AlgoDTO algoDTO) {
        Algo algo = new Algo();
        algo.setTitle(algoDTO.getTitle());
        algo.setContent(algoDTO.getContent());
        algo.setAnswer(algoDTO.getAnswer());
        algo.setIsVisible(algoDTO.getIsVisible());
        algo.setCreatedAt(algoDTO.getCreatedAt());
        algo.setUpdatedAt(algoDTO.getUpdatedAt());
        AlgoDTO updatedAlgoDTO = algoService.updateAlgo(id, algo);
        if (updatedAlgoDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedAlgoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlgo(@PathVariable Long id) {
        boolean isDeleted = algoService.deleteAlgo(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}