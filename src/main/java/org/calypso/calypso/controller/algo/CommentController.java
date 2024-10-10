package org.calypso.calypso.controller.algo;

import org.calypso.calypso.dto.algo.CommentDTO;
import org.calypso.calypso.mapper.algo.CommentMapper;
import org.calypso.calypso.model.algo.Comment;
import org.calypso.calypso.service.algo.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    public CommentController(CommentService commentService, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
    }

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        List<CommentDTO> comments = commentService.getAllComments();
        if (comments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id) {
        CommentDTO comment = commentService.getCommentById(id);
        if (comment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comment);
    }

    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO) {
        Comment comment = commentMapper.convertToEntity(commentDTO);
        CommentDTO createdCommentDTO = commentService.createComment(comment);
        if (createdCommentDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(createdCommentDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long id, @RequestBody CommentDTO commentDTO) {
        Comment comment = commentMapper.convertToEntity(commentDTO);
        CommentDTO updatedCommentDTO = commentService.updateComment(id, comment);
        if (updatedCommentDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedCommentDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        boolean isDeleted = commentService.deleteComment(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}