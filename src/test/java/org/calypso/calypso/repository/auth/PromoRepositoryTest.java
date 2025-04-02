package org.calypso.calypso.repository.auth;

import org.calypso.calypso.model.auth.Promo;
import org.calypso.calypso.model.auth.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PromoRepositoryTest {

    @Autowired
    private PromoRepository promoRepository;

    @Test
    void testSavePromo() {
        // Arrange
        Promo promo = new Promo();
        promo.setName("Promo CDA");
        promo.setType("Online");
        promo.setCity("Lyon");
        promo.setBeginAt(2024);
        promo.setIsVisible(true);

        // Act
        Promo savedPromo = promoRepository.save(promo);

        // Assert
        assertThat(savedPromo).isNotNull();
        assertThat(savedPromo.getId()).isNotNull();
        assertThat(savedPromo.getName()).isEqualTo("Promo CDA");
        assertThat(savedPromo.getType()).isEqualTo("Online");
        assertThat(savedPromo.getCity()).isEqualTo("Lyon");
        assertThat(savedPromo.getBeginAt()).isEqualTo(2024);
        assertThat(savedPromo.getIsVisible()).isTrue();
    }

    @Test
    void testFindById() {
        // Arrange
        Promo promo = new Promo();
        promo.setName("Promo DWWM");
        promo.setType("Offline");
        promo.setCity("Bordeaux");
        promo.setBeginAt(2024);
        promo.setIsVisible(false);
        Promo savedPromo = promoRepository.save(promo);

        // Act
        Optional<Promo> foundPromo = promoRepository.findById(savedPromo.getId());

        // Assert
        assertThat(foundPromo).isPresent();
        assertThat(foundPromo.get().getName()).isEqualTo("Promo DWWM");
        assertThat(foundPromo.get().getType()).isEqualTo("Offline");
        assertThat(foundPromo.get().getCity()).isEqualTo("Bordeaux");
        assertThat(foundPromo.get().getBeginAt()).isEqualTo(2024);
        assertThat(foundPromo.get().getIsVisible()).isFalse();
    }

    @Test
    void testFindByName() {
        // Arrange
        Promo promo = new Promo();
        promo.setName("Promo JS");
        promo.setType("Hybrid");
        promo.setCity("Paris");
        promo.setBeginAt(2025);
        promo.setIsVisible(true);
        promoRepository.save(promo);

        // Act
        Promo foundPromo = promoRepository.findByName("Promo JS");

        // Assert
        assertThat(foundPromo).isNotNull();
        assertThat(foundPromo.getName()).isEqualTo("Promo JS");
        assertThat(foundPromo.getType()).isEqualTo("Hybrid");
        assertThat(foundPromo.getCity()).isEqualTo("Paris");
        assertThat(foundPromo.getBeginAt()).isEqualTo(2025);
        assertThat(foundPromo.getIsVisible()).isTrue();
    }

    @Test
    void testManyToManyUsers() {
        // Arrange
        User user1 = new User();
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setEmail("john.doe@example.com");
        user1.setPassword("password");

        User user2 = new User();
        user2.setFirstName("Jane");
        user2.setLastName("Smith");
        user2.setEmail("jane.smith@example.com");
        user2.setPassword("password123");

        Promo promo = new Promo();
        promo.setName("Promo Test");
        promo.setType("Hybrid");
        promo.setCity("Toulouse");
        promo.setBeginAt(2024);
        promo.setIsVisible(true);
        promo.setUsers(new HashSet<>(Set.of(user1, user2)));

        // Act
        Promo savedPromo = promoRepository.save(promo);

        // Assert
        assertThat(savedPromo.getUsers()).isNotNull();
        assertThat(savedPromo.getUsers()).hasSize(2);
    }

    @Test
    void testFindByNameNotFound() {
        // Act
        Promo foundPromo = promoRepository.findByName("Nonexistent Promo");

        // Assert
        assertThat(foundPromo).isNull();
    }
}
