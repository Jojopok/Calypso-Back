package org.calypso.calypso.mapper.auth;

import org.calypso.calypso.dto.auth.UserDTO;
import org.calypso.calypso.dto.auth.UserRegistrationDTO;
import org.calypso.calypso.model.auth.User;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public User toEntity(UserRegistrationDTO dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPassword(dto.getPassword());
        user.setOdysseyLink(dto.getOdysseyLink());
        return user;
    }

    public UserRegistrationDTO toDto(User user) {
        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPassword(user.getPassword());
        dto.setOdysseyLink(user.getOdysseyLink());
        return dto;
    }

    public UserDTO toUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setAvatar(user.getAvatar());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setOdysseyLink(user.getOdysseyLink());

        // Convertir les rôles en strings
        dto.setRoles(user.getRoles().stream()
                .map(role -> role.getRole())  // Extraire le nom du rôle
                .collect(Collectors.toSet()));

        // Convertir les promos en objets Promo complets
        dto.setPromos(new HashSet<>(user.getPromos()));  // Ajouter directement les objets Promo

        return dto;
    }

    // Méthode pour convertir une liste d'utilisateurs en liste de UserDTO
    public List<UserDTO> toUserDTOList(List<User> users) {
        // Utiliser un Stream pour convertir chaque utilisateur en UserDTO
        return users.stream()
                .map(this::toUserDTO)  // Appel de la méthode toUserDTO pour chaque utilisateur
                .collect(Collectors.toList());  // Collecter le résultat dans une liste
    }

}