package org.calypso.calypso.mapper.algo;

import org.calypso.calypso.dto.algo.BugDTO;
import org.calypso.calypso.model.algo.Bug;
import org.calypso.calypso.model.algo.Algo;
import org.calypso.calypso.model.auth.User;
import org.calypso.calypso.repository.algo.AlgoRepository;
import org.calypso.calypso.repository.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BugMapper {

    @Autowired
    private AlgoRepository algoRepository;

    @Autowired
    private UserRepository userRepository;

    public BugDTO convertToDTO(Bug bug) {
        BugDTO bugDTO = new BugDTO();
        bugDTO.setId(bug.getId());
        bugDTO.setContent(bug.getContent());
        bugDTO.setIsResolved(bug.getIsResolved());
        bugDTO.setAlgoId(bug.getAlgo().getId());
        bugDTO.setUserId(bug.getUser().getId());
        return bugDTO;
    }

    public Bug convertToEntity(BugDTO bugDTO) {
        Bug bug = new Bug();
        bug.setId(bugDTO.getId());
        bug.setContent(bugDTO.getContent());
        bug.setIsResolved(bugDTO.getIsResolved());
        Algo algo = algoRepository.findById(bugDTO.getAlgoId()).orElseThrow(() -> new RuntimeException("Algo not found"));
        bug.setAlgo(algo);
        User user = userRepository.findById(bugDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        bug.setUser(user);
        return bug;
    }
}