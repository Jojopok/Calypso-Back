package org.calypso.calypso.repository.algo;

import org.calypso.calypso.model.algo.UserAnswer;
import org.calypso.calypso.model.algo.Algo;
import org.calypso.calypso.model.auth.User;
import org.calypso.calypso.repository.auth.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserAnswerRepositoryTest {

    @Autowired
    private UserAnswerRepository userAnswerRepository;

    @Autowired
    private AlgoRepository algoRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveUserAnswer() {
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
        algo.setContent("Contenu à propos des algorithmes de tri");
        algo.setAnswer("Le tri par fusion est un exemple.");
        algo.setIsVisible(true);
        algo.setCreatedAt(new Date());
        algo.setUpdatedAt(new Date());
        algo.setUser(savedUser);
        Algo savedAlgo = algoRepository.save(algo);

        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setContent("Réponse utilisateur");
        userAnswer.setIsRight(true);
        userAnswer.setAlgo(savedAlgo);
        userAnswer.setUser(savedUser);

        // Act
        UserAnswer savedUserAnswer = userAnswerRepository.save(userAnswer);

        // Assert
        assertThat(savedUserAnswer).isNotNull();
        assertThat(savedUserAnswer.getId()).isNotNull();
        assertThat(savedUserAnswer.getContent()).isEqualTo("Réponse utilisateur");
        assertThat(savedUserAnswer.getIsRight()).isTrue();
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
        algo.setUser(savedUser);
        Algo savedAlgo = algoRepository.save(algo);

        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setContent("Réponse utilisateur");
        userAnswer.setIsRight(false);
        userAnswer.setAlgo(savedAlgo);
        userAnswer.setUser(savedUser);
        UserAnswer savedUserAnswer = userAnswerRepository.save(userAnswer);

        // Act
        Optional<UserAnswer> foundUserAnswer = userAnswerRepository.findById(savedUserAnswer.getId());

        // Assert
        assertThat(foundUserAnswer).isPresent();
        assertThat(foundUserAnswer.get().getContent()).isEqualTo("Réponse utilisateur");
        assertThat(foundUserAnswer.get().getIsRight()).isFalse();
    }

    @Test
    void testDeleteUserAnswer() {
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
        algo.setContent("Contenu temporaire");
        algo.setIsVisible(false);
        algo.setUser(savedUser);
        Algo savedAlgo = algoRepository.save(algo);

        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setContent("Réponse utilisateur");
        userAnswer.setIsRight(false);
        userAnswer.setAlgo(savedAlgo);
        userAnswer.setUser(savedUser);
        UserAnswer savedUserAnswer = userAnswerRepository.save(userAnswer);

        // Act
        userAnswerRepository.deleteById(savedUserAnswer.getId());

        // Assert
        Optional<UserAnswer> deletedUserAnswer = userAnswerRepository.findById(savedUserAnswer.getId());
        assertThat(deletedUserAnswer).isNotPresent();
    }
}