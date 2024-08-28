package org.calypso.calypso.repository;

import jakarta.persistence.Id;
import org.calypso.calypso.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByFirstName(String firstName);
    List<User> findByLastName(String lastName);
    List<User> findByEmailContaining(String email);
    List<User> findAllByOrderByIdAsc();
}
