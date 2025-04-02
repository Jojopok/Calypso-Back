package org.calypso.calypso.security;

import org.calypso.calypso.model.auth.User;
import org.calypso.calypso.repository.auth.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Chargement de l'utilisateur par son ID ou email
     */
    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        User user = null;

        // Si l'identifiant est un ID valide, on essaie de récupérer l'utilisateur par ID
        try {
            Long userId = Long.parseLong(identifier);  // Convertir l'ID passé en String vers Long
            user = userRepository.findById(userId)
                    .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'ID : " + identifier));
        } catch (NumberFormatException e) {
            // Si ce n'est pas un ID valide, on essaie avec l'email
            user = userRepository.findByEmail(identifier)
                    .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + identifier));
        }

        return user;
    }
}
