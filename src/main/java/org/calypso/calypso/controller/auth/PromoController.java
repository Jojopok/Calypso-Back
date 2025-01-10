package org.calypso.calypso.controller.auth;

import org.calypso.calypso.dto.auth.PromoDTO;
import org.calypso.calypso.dto.auth.UserDTO;
import org.calypso.calypso.mapper.auth.*;
import org.calypso.calypso.model.auth.*;
import org.calypso.calypso.repository.auth.UserRepository;
import org.calypso.calypso.service.auth.PromoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/promos")
public class PromoController {

    private final PromoService promoService;
    private final PromoMapper promoMapper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public PromoController(PromoService promoService, PromoMapper promoMapper, UserMapper userMapper, UserRepository userRepository) {
        this.promoService = promoService;
        this.promoMapper = promoMapper;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    private PromoDTO convertToDTO(Promo promo) {
        PromoDTO promoDTO = new PromoDTO();
        promoDTO.setId(promo.getId());
        promoDTO.setName(promo.getName());
        promoDTO.setType(promo.getType());
        promoDTO.setCity(promo.getCity());
        promoDTO.setBeginAt(promo.getBeginAt());
        promoDTO.setIsVisible(promo.getIsVisible());
        return promoDTO;
    }

    @GetMapping
    public ResponseEntity<List<PromoDTO>> getAllPromos() {
        List<Promo> promos = promoService.getAllPromos();
        if (promos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<PromoDTO> promoDTOs = promos.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(promoDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromoDTO> getPromoById(@PathVariable Long id) {
        Promo promo = promoService.getPromoById(id);
        if (promo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToDTO(promo));
    }

    @PostMapping
    public ResponseEntity<PromoDTO> createPromo(@RequestBody PromoDTO promoDTO) {
        Promo promo = new Promo();
        promo.setName(promoDTO.getName());
        promo.setType(promoDTO.getType());
        promo.setCity(promoDTO.getCity());
        promo.setBeginAt(promoDTO.getBeginAt());
        promo.setIsVisible(promoDTO.getIsVisible());
        PromoDTO createdPromoDTO = convertToDTO(promoService.createPromo(promo));
        return ResponseEntity.ok(createdPromoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PromoDTO> updatePromo(@PathVariable Long id, @RequestBody PromoDTO promoDTO) {
        Promo promo = new Promo();
        promo.setName(promoDTO.getName());
        promo.setType(promoDTO.getType());
        promo.setCity(promoDTO.getCity());
        promo.setBeginAt(promoDTO.getBeginAt());
        promo.setIsVisible(promoDTO.getIsVisible());
        PromoDTO updatedPromoDTO = convertToDTO(promoService.updatePromo(id, promo));
        if (updatedPromoDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedPromoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePromo(@PathVariable Long id) {
        boolean isDeleted = promoService.deletePromo(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{promoId}/members")
    public List<UserDTO> getPromoMembers(@PathVariable Long promoId) {
        List<User> users = userRepository.findByPromos(promoId);
        return (userMapper.toUserDTOList(users));
    }
}