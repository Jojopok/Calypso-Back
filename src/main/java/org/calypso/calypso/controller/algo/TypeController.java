package org.calypso.calypso.controller.algo;

import org.calypso.calypso.dto.algo.TypeDTO;
import org.calypso.calypso.model.algo.Type;
import org.calypso.calypso.service.algo.TypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/types")
public class TypeController {

    private final TypeService typeService;

    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping
    public ResponseEntity<List<TypeDTO>> getAllTypes() {
        List<TypeDTO> types = typeService.getAllTypes();
        if (types.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(types);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeDTO> getTypeById(@PathVariable Long id) {
        TypeDTO typeDTO = typeService.getTypeById(id);
        if (typeDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(typeDTO);
    }

    @PostMapping
    public ResponseEntity<TypeDTO> createType(@RequestBody TypeDTO typeDTO) {
        Type type = new Type();
        type.setType(typeDTO.getType());
        TypeDTO createdTypeDTO = typeService.createType(type);
        if (createdTypeDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(createdTypeDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypeDTO> updateType(@PathVariable Long id, @RequestBody TypeDTO typeDTO) {
        Type type = new Type();
        type.setType(typeDTO.getType());
        TypeDTO updatedTypeDTO = typeService.updateType(id, type);
        if (updatedTypeDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedTypeDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteType(@PathVariable Long id) {
        boolean isDeleted = typeService.deleteType(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}