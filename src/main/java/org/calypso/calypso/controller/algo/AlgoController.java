package org.calypso.calypso.controller.algo;

import org.calypso.calypso.dto.algo.AlgoDTO;
import org.calypso.calypso.mapper.algo.AlgoMapper;
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
    private final AlgoMapper algoMapper;

    public AlgoController(AlgoRepository algoRepository, AlgoService algoService, AlgoMapper algoMapper) {
        this.algoRepository = algoRepository;
        this.algoService = algoService;
        this.algoMapper = algoMapper;
    }

    @GetMapping
    public ResponseEntity<List<AlgoDTO>> getAllAlgos() {
        List<Algo> algos = algoRepository.findAll();
        if (algos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<AlgoDTO> algoDTOs = algos.stream().map(algoMapper::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(algoDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlgoDTO> getAlgoById(@PathVariable Long id) {
        Algo algo = algoRepository.findById(id).orElse(null);
        if (algo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(algoMapper.convertToDTO(algo));
    }

    @PostMapping
    public ResponseEntity<AlgoDTO> createAlgo(@RequestBody AlgoDTO algoDTO) {
        Algo algo = algoMapper.convertToEntity(algoDTO);
        AlgoDTO createdAlgoDTO = algoService.createAlgo(algo);
        if (createdAlgoDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(createdAlgoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlgoDTO> updateAlgo(@PathVariable Long id, @RequestBody AlgoDTO algoDTO) {
        Algo algo = algoMapper.convertToEntity(algoDTO);
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