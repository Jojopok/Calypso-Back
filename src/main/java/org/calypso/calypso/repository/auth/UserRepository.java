package org.calypso.calypso.repository.auth;

import org.calypso.calypso.model.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findByFirstName(String firstName);
    List<User> findByLastName(String lastName);
    List<User> findByEmailContaining(String email);
    List<User> findAllByOrderByIdAsc();
    Optional<User> findById(Long id);
}
