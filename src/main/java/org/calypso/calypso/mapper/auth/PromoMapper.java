package org.calypso.calypso.mapper.auth;

import org.calypso.calypso.dto.auth.PromoDTO;
import org.calypso.calypso.dto.auth.UserDTO;
import org.calypso.calypso.model.auth.Promo;
import org.calypso.calypso.model.auth.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PromoMapper {

    public Promo toEntity(PromoDTO dto) {
        Promo promo = new Promo();
        promo.setName(dto.getName());
        promo.setType(dto.getType());
        promo.setCity(dto.getCity());
        promo.setBeginAt(dto.getBeginAt());
        promo.setIsVisible(dto.getIsVisible());
        return promo;
    }

    public PromoDTO toDto(Promo promo) {
        PromoDTO dto = new PromoDTO();
        dto.setId(promo.getId());
        dto.setName(promo.getName());
        dto.setType(promo.getType());
        dto.setCity(promo.getCity());
        dto.setBeginAt(promo.getBeginAt());
        dto.setIsVisible(promo.getIsVisible());
        return dto;
    }
}