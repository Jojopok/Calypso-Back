package org.calypso.calypso.service.algo;

import org.calypso.calypso.dto.algo.BugDTO;
import org.calypso.calypso.mapper.algo.BugMapper;
import org.calypso.calypso.model.algo.Bug;
import org.calypso.calypso.repository.algo.BugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BugService {

    @Autowired
    private BugRepository bugRepository;

    @Autowired
    private BugMapper bugMapper;

    public List<BugDTO> getAllBugs() {
        return bugRepository.findAll().stream()
                .map(bugMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public BugDTO getBugById(Long id) {
        return bugRepository.findById(id)
                .map(bugMapper::convertToDTO)
                .orElse(null);
    }

    public BugDTO createBug(Bug bug) {
        Bug savedBug = bugRepository.save(bug);
        return bugMapper.convertToDTO(savedBug);
    }

    public BugDTO updateBug(Long id, Bug bug) {
        if (!bugRepository.existsById(id)) {
            return null;
        }
        bug.setId(id);
        Bug updatedBug = bugRepository.save(bug);
        return bugMapper.convertToDTO(updatedBug);
    }

    public boolean deleteBug(Long id) {
        if (!bugRepository.existsById(id)) {
            return false;
        }
        bugRepository.deleteById(id);
        return true;
    }
}