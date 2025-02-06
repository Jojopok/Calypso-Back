package org.calypso.calypso.mapper.algo;

import org.calypso.calypso.dto.algo.TypeDTO;
import org.calypso.calypso.model.algo.Type;
import org.springframework.stereotype.Component;

@Component
public class TypeMapper {

    public TypeDTO convertToDTO(Type type) {
        TypeDTO typeDTO = new TypeDTO();
        typeDTO.setId(type.getId());
        typeDTO.setType(type.getType());
        typeDTO.setColor(type.getColor());
        typeDTO.setLogo(type.getLogo());
        return typeDTO;
    }

    public Type convertToEntity(TypeDTO typeDTO) {
        Type type = new Type();
        type.setId(typeDTO.getId());
        type.setType(typeDTO.getType());
        type.setColor(typeDTO.getColor());
        type.setLogo(typeDTO.getLogo());
        return type;
    }
}