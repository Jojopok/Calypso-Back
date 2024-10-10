package org.calypso.calypso.service.algo;

import org.calypso.calypso.dto.algo.CommentDTO;
import org.calypso.calypso.mapper.algo.CommentMapper;
import org.calypso.calypso.model.algo.Comment;
import org.calypso.calypso.repository.algo.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentMapper commentMapper;

    public List<CommentDTO> getAllComments() {
        return commentRepository.findAll().stream()
                .map(commentMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public CommentDTO getCommentById(Long id) {
        return commentRepository.findById(id)
                .map(commentMapper::convertToDTO)
                .orElse(null);
    }

    public CommentDTO createComment(Comment comment) {
        Comment savedComment = commentRepository.save(comment);
        return commentMapper.convertToDTO(savedComment);
    }

    public CommentDTO updateComment(Long id, Comment comment) {
        if (!commentRepository.existsById(id)) {
            return null;
        }
        comment.setId(id);
        Comment updatedComment = commentRepository.save(comment);
        return commentMapper.convertToDTO(updatedComment);
    }

    public boolean deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            return false;
        }
        commentRepository.deleteById(id);
        return true;
    }
}