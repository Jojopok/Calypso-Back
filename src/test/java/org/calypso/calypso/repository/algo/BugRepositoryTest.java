package org.calypso.calypso.repository.algo;

import org.calypso.calypso.model.algo.Algo;
import org.calypso.calypso.model.algo.Bug;
import org.calypso.calypso.model.auth.User;
import org.calypso.calypso.repository.auth.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BugRepositoryTest {

    @Autowired
    private BugRepository bugRepository;

    @Autowired
    private AlgoRepository algoRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveBug() {
        // Arrange
        User user = new User();
        user.setFirstName("Alice");
        user.setLastName("Smith");
        user.setEmail("alice.smith@example.com");
        user.setPassword("password123");
        user.setOdysseyLink("http://example.com/odyssey");
        User savedUser = userRepository.save(user);

        Algo algo = new Algo();
        algo.setTitle("Algorithme de tri");
        algo.setContent("Description de l'algorithme de tri");
        algo.setAnswer("Le tri par fusion est un exemple.");
        algo.setIsVisible(true);
        algo.setCreatedAt(new Date());
        algo.setUpdatedAt(new Date());
        algo.setUser(savedUser);
        Algo savedAlgo = algoRepository.save(algo);

        Bug bug = new Bug();
        bug.setContent("Erreur dans le tri par fusion");
        bug.setIsResolved(false);
        bug.setAlgo(savedAlgo);
        bug.setUser(savedUser);

        // Act
        Bug savedBug = bugRepository.save(bug);

        // Assert
        assertThat(savedBug).isNotNull();
        assertThat(savedBug.getId()).isNotNull();
        assertThat(savedBug.getContent()).isEqualTo("Erreur dans le tri par fusion");
        assertThat(savedBug.getIsResolved()).isFalse();
        assertThat(savedBug.getAlgo()).isEqualTo(savedAlgo);
        assertThat(savedBug.getUser()).isEqualTo(savedUser);
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
        algo.setContent("Description de l'algorithme de recherche");
        algo.setAnswer("Complexité logarithmique");
        algo.setIsVisible(true);
        algo.setCreatedAt(new Date());
        algo.setUpdatedAt(new Date());
        algo.setUser(savedUser);
        Algo savedAlgo = algoRepository.save(algo);

        Bug bug = new Bug();
        bug.setContent("Problème dans l'implémentation de la recherche");
        bug.setIsResolved(true);
        bug.setAlgo(savedAlgo);
        bug.setUser(savedUser);
        Bug savedBug = bugRepository.save(bug);

        // Act
        Optional<Bug> foundBug = bugRepository.findById(savedBug.getId());

        // Assert
        assertThat(foundBug).isPresent();
        assertThat(foundBug.get().getContent()).isEqualTo("Problème dans l'implémentation de la recherche");
        assertThat(foundBug.get().getIsResolved()).isTrue();
        assertThat(foundBug.get().getAlgo()).isEqualTo(savedAlgo);
        assertThat(foundBug.get().getUser()).isEqualTo(savedUser);
    }

    @Test
    void testDeleteBug() {
        // Arrange
        User user = new User();
        user.setFirstName("Carol");
        user.setLastName("White");
        user.setEmail("carol.white@example.com");
        user.setPassword("mypassword");
        user.setOdysseyLink("http://example.com/odyssey");
        User savedUser = userRepository.save(user);

        Algo algo = new Algo();
        algo.setTitle("Algorithme à supprimer");
        algo.setContent("Description pour test");
        algo.setAnswer("Réponse pour test");
        algo.setIsVisible(false);
        algo.setCreatedAt(new Date());
        algo.setUpdatedAt(new Date());
        algo.setUser(savedUser);
        Algo savedAlgo = algoRepository.save(algo);

        Bug bug = new Bug();
        bug.setContent("Bug à supprimer");
        bug.setIsResolved(false);
        bug.setAlgo(savedAlgo);
        bug.setUser(savedUser);
        Bug savedBug = bugRepository.save(bug);

        // Act
        bugRepository.deleteById(savedBug.getId());

        // Assert
        Optional<Bug> deletedBug = bugRepository.findById(savedBug.getId());
        assertThat(deletedBug).isNotPresent();
    }
}
