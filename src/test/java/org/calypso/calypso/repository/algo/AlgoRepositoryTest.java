package org.calypso.calypso.repository.algo;

import org.calypso.calypso.model.algo.Algo;
import org.calypso.calypso.model.algo.Type;
import org.calypso.calypso.model.auth.User;
import org.calypso.calypso.repository.auth.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AlgoRepositoryTest {

    @Autowired
    private AlgoRepository algoRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveAlgo() {
        // Arrange
        User user = new User();
        user.setFirstName("Alice");
        user.setLastName("Smith");
        user.setEmail("alice.smith@example.com");
        user.setPassword("password123");
        user.setOdysseyLink("http://example.com/odyssey"); // Initialisation obligatoire
        user.setAvatar("http://example.com/avatar.png"); // Optionnel

        // Sauvegarde de l'utilisateur
        User savedUser = userRepository.save(user);

        Algo algo = new Algo();
        algo.setTitle("Algorithme de tri");
        algo.setContent("Contenu à propos des algorithmes de tri");
        algo.setAnswer("Le tri par fusion est un exemple.");
        algo.setIsVisible(true);
        algo.setCreatedAt(new Date());
        algo.setUpdatedAt(new Date());
        algo.setUser(savedUser); // Associer l'utilisateur sauvegardé

        // Act
        Algo savedAlgo = algoRepository.save(algo);

        // Assert
        assertThat(savedAlgo).isNotNull();
        assertThat(savedAlgo.getId()).isNotNull();
        assertThat(savedAlgo.getTitle()).isEqualTo("Algorithme de tri");
        assertThat(savedAlgo.getContent()).isEqualTo("Contenu à propos des algorithmes de tri");
        assertThat(savedAlgo.getAnswer()).isEqualTo("Le tri par fusion est un exemple.");
        assertThat(savedAlgo.getIsVisible()).isTrue();
    }

    @Test
    void testFindById() {
        // Arrange
        User user = new User();
        user.setFirstName("Bob");
        user.setLastName("Doe");
        user.setEmail("bob.doe@example.com");
        user.setPassword("securepassword");
        user.setOdysseyLink("http://example.com/odyssey");
        User savedUser = userRepository.save(user);

        Algo algo = new Algo();
        algo.setTitle("Algorithme de recherche");
        algo.setContent("La recherche binaire est un exemple.");
        algo.setAnswer("Complexité logarithmique");
        algo.setIsVisible(false);
        algo.setCreatedAt(new Date());
        algo.setUpdatedAt(new Date());
        algo.setUser(savedUser); // Associer l'utilisateur sauvegardé
        Algo savedAlgo = algoRepository.save(algo);

        // Act
        Optional<Algo> foundAlgo = algoRepository.findById(savedAlgo.getId());

        // Assert
        assertThat(foundAlgo).isPresent();
        assertThat(foundAlgo.get().getTitle()).isEqualTo("Algorithme de recherche");
        assertThat(foundAlgo.get().getContent()).isEqualTo("La recherche binaire est un exemple.");
        assertThat(foundAlgo.get().getAnswer()).isEqualTo("Complexité logarithmique");
        assertThat(foundAlgo.get().getIsVisible()).isFalse();
    }

    @Test
    void testSaveWithTypes() {
        // Arrange
        User user = new User();
        user.setFirstName("Carol");
        user.setLastName("White");
        user.setEmail("carol.white@example.com");
        user.setPassword("mypassword");
        user.setOdysseyLink("http://example.com/odyssey");
        User savedUser = userRepository.save(user);

        Algo algo = new Algo();
        algo.setTitle("Algorithme avec types");
        algo.setContent("Démonstration de la relation Many-to-Many");
        algo.setIsVisible(true);
        algo.setCreatedAt(new Date());
        algo.setUpdatedAt(new Date());
        algo.setUser(savedUser); // Associer l'utilisateur sauvegardé
        Set<Type> types = new HashSet<>();
        // Ajouter des entités Type au set (ajoutez des données réelles selon votre configuration)
        algo.setTypes(types);

        // Act
        Algo savedAlgo = algoRepository.save(algo);

        // Assert
        assertThat(savedAlgo.getTypes()).isNotNull();
        assertThat(savedAlgo.getTypes()).isEqualTo(types);
    }

    @Test
    void testDeleteAlgo() {
        // Arrange
        User user = new User();
        user.setFirstName("Derek");
        user.setLastName("Green");
        user.setEmail("derek.green@example.com");
        user.setPassword("password123");
        user.setOdysseyLink("http://example.com/odyssey");
        User savedUser = userRepository.save(user);

        Algo algo = new Algo();
        algo.setTitle("Algorithme à supprimer");
        algo.setContent("Contenu temporaire");
        algo.setIsVisible(false);
        algo.setUser(savedUser); // Associer l'utilisateur sauvegardé
        Algo savedAlgo = algoRepository.save(algo);

        // Act
        algoRepository.deleteById(savedAlgo.getId());

        // Assert
        Optional<Algo> deletedAlgo = algoRepository.findById(savedAlgo.getId());
        assertThat(deletedAlgo).isNotPresent();
    }
}
