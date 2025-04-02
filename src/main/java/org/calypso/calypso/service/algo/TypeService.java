package org.calypso.calypso.service.algo;

import org.calypso.calypso.dto.algo.TypeDTO;
import org.calypso.calypso.mapper.algo.TypeMapper;
import org.calypso.calypso.model.algo.Type;
import org.calypso.calypso.repository.algo.TypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TypeService {

    private final TypeRepository typeRepository;
    private final TypeMapper typeMapper;

    public TypeService(TypeRepository typeRepository, TypeMapper typeMapper) {
        this.typeRepository = typeRepository;
        this.typeMapper = typeMapper;
    }

    public List<TypeDTO> getAllTypes() {
        List<Type> types = typeRepository.findAll();
        return types.stream().map(typeMapper::convertToDTO).collect(Collectors.toList());
    }

    public TypeDTO getTypeById(Long id) {
        Type type = typeRepository.findById(id).orElse(null);
        if (type == null) {
            return null;
        }
        return typeMapper.convertToDTO(type);
    }

    public TypeDTO createType(Type type) {
        Type savedType = typeRepository.save(type);
        return typeMapper.convertToDTO(savedType);
    }

    public TypeDTO updateType(Long id, Type typeDetails) {
        Type type = typeRepository.findById(id).orElse(null);
        if (type == null) {
            return null;
        }
        type.setType(typeDetails.getType());
        type.setColor(typeDetails.getColor());
        type.setLogo(typeDetails.getLogo());
        Type updatedType = typeRepository.save(type);
        return typeMapper.convertToDTO(updatedType);
    }

    public boolean deleteType(Long id) {
        if (typeRepository.existsById(id)) {
            typeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}