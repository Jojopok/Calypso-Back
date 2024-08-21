package org.calypso.calypso.mapper;

import org.calypso.calypso.dto.UserDTO;
import org.calypso.calypso.model.User;
import org.calypso.calypso.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final UserRepository userRepository;

    public UserMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setAvatar(user.getAvatar());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setOdysseyLink(user.getOdysseyLink());
        return userDTO;
    }
}
