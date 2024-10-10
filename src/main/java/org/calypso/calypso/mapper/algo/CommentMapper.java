package org.calypso.calypso.mapper.algo;

import org.calypso.calypso.dto.algo.CommentDTO;
import org.calypso.calypso.model.algo.Comment;
import org.calypso.calypso.model.algo.Algo;
import org.calypso.calypso.model.auth.User;
import org.calypso.calypso.repository.algo.AlgoRepository;
import org.calypso.calypso.repository.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    @Autowired
    private AlgoRepository algoRepository;

    @Autowired
    private UserRepository userRepository;

    public CommentDTO convertToDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setContent(comment.getContent());
        commentDTO.setRate(comment.getRate());
        commentDTO.setAlgoId(comment.getAlgo().getId());
        commentDTO.setUserId(comment.getUser().getId());
        return commentDTO;
    }

    public Comment convertToEntity(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setContent(commentDTO.getContent());
        comment.setRate(commentDTO.getRate());
        Algo algo = algoRepository.findById(commentDTO.getAlgoId()).orElseThrow(() -> new RuntimeException("Algo not found"));
        comment.setAlgo(algo);
        User user = userRepository.findById(commentDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        comment.setUser(user);
        return comment;
    }
}