package org.calypso.calypso.service.algo;

import org.calypso.calypso.dto.algo.CommentDTO;
import org.calypso.calypso.mapper.algo.CommentMapper;
import org.calypso.calypso.model.algo.Comment;
import org.calypso.calypso.repository.algo.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private CommentService commentService;

    @Test
    void testGetAllComments() {
        // Arrange
        Comment comment1 = new Comment();
        comment1.setContent("Comment 1");
        Comment comment2 = new Comment();
        comment2.setContent("Comment 2");

        when(commentRepository.findAll()).thenReturn(List.of(comment1, comment2));
        when(commentMapper.convertToDTO(comment1)).thenReturn(new CommentDTO());
        when(commentMapper.convertToDTO(comment2)).thenReturn(new CommentDTO());

        // Act
        List<CommentDTO> comments = commentService.getAllComments();

        // Assert
        assertThat(comments).hasSize(2);
    }

    @Test
    void testGetCommentById() {
        // Arrange
        Long id = 1L;
        Comment comment = new Comment();
        comment.setId(id);
        comment.setContent("Test Comment");

        when(commentRepository.findById(id)).thenReturn(Optional.of(comment));
        when(commentMapper.convertToDTO(comment)).thenReturn(new CommentDTO());

        // Act
        CommentDTO commentDTO = commentService.getCommentById(id);

        // Assert
        assertThat(commentDTO).isNotNull();
    }

    @Test
    void testCreateComment() {
        // Arrange
        Comment comment = new Comment();
        comment.setContent("New Comment");

        when(commentRepository.save(comment)).thenReturn(comment);
        when(commentMapper.convertToDTO(comment)).thenReturn(new CommentDTO());

        // Act
        CommentDTO commentDTO = commentService.createComment(comment);

        // Assert
        assertThat(commentDTO).isNotNull();
    }

    @Test
    void testUpdateComment() {
        // Arrange
        Long id = 1L;
        Comment existingComment = new Comment();
        existingComment.setId(id);
        existingComment.setContent("Existing Comment");

        Comment updatedCommentDetails = new Comment();
        updatedCommentDetails.setContent("Updated Comment");

        when(commentRepository.existsById(id)).thenReturn(true);
        doReturn(existingComment).when(commentRepository).save(any(Comment.class));
        when(commentMapper.convertToDTO(existingComment)).thenReturn(new CommentDTO());

        // Act
        CommentDTO updatedCommentDTO = commentService.updateComment(id, updatedCommentDetails);

        // Assert
        assertThat(updatedCommentDTO).isNotNull();
    }

    @Test
    void testDeleteComment() {
        // Arrange
        Long id = 1L;
        when(commentRepository.existsById(id)).thenReturn(true);

        // Act
        boolean isDeleted = commentService.deleteComment(id);

        // Assert
        assertThat(isDeleted).isTrue();
        verify(commentRepository, times(1)).deleteById(id);
    }
}