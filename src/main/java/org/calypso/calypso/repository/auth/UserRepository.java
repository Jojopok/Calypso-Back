package org.calypso.calypso.repository.auth;

import io.micrometer.common.lang.NonNull;
import org.calypso.calypso.model.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    @NonNull
    Optional<User> findById(@NonNull Long id);
    boolean existsByEmail(String email);
    List<User> findByFirstName(String firstName);
    List<User> findByLastName(String lastName);
    List<User> findByEmailContaining(String email);
    List<User> findAllByOrderByIdAsc();

    @Query("SELECT u FROM User u JOIN u.promos p WHERE p.id = :promoId")
    List<User> findByPromos(Long promoId);
}
