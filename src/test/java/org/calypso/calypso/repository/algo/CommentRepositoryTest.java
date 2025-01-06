package org.calypso.calypso.repository.algo;

import org.calypso.calypso.model.algo.Algo;
import org.calypso.calypso.model.algo.Comment;
import org.calypso.calypso.model.auth.User;
import org.calypso.calypso.repository.auth.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private AlgoRepository algoRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveComment() {
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

        Comment comment = new Comment();
        comment.setContent("Très bon algorithme, mais améliorable.");
        comment.setRate(4);
        comment.setAlgo(savedAlgo);
        comment.setUser(savedUser);

        // Act
        Comment savedComment = commentRepository.save(comment);

        // Assert
        assertThat(savedComment).isNotNull();
        assertThat(savedComment.getId()).isNotNull();
        assertThat(savedComment.getContent()).isEqualTo("Très bon algorithme, mais améliorable.");
        assertThat(savedComment.getRate()).isEqualTo(4);
        assertThat(savedComment.getAlgo()).isEqualTo(savedAlgo);
        assertThat(savedComment.getUser()).isEqualTo(savedUser);
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

        Comment comment = new Comment();
        comment.setContent("Problème rencontré lors de l'exécution.");
        comment.setRate(2);
        comment.setAlgo(savedAlgo);
        comment.setUser(savedUser);
        Comment savedComment = commentRepository.save(comment);

        // Act
        Optional<Comment> foundComment = commentRepository.findById(savedComment.getId());

        // Assert
        assertThat(foundComment).isPresent();
        assertThat(foundComment.get().getContent()).isEqualTo("Problème rencontré lors de l'exécution.");
        assertThat(foundComment.get().getRate()).isEqualTo(2);
        assertThat(foundComment.get().getAlgo()).isEqualTo(savedAlgo);
        assertThat(foundComment.get().getUser()).isEqualTo(savedUser);
    }

    @Test
    void testDeleteComment() {
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

        Comment comment = new Comment();
        comment.setContent("Commentaire à supprimer.");
        comment.setRate(3);
        comment.setAlgo(savedAlgo);
        comment.setUser(savedUser);
        Comment savedComment = commentRepository.save(comment);

        // Act
        commentRepository.deleteById(savedComment.getId());

        // Assert
        Optional<Comment> deletedComment = commentRepository.findById(savedComment.getId());
        assertThat(deletedComment).isNotPresent();
    }
}
