package org.calypso.calypso.controller.algo;

import org.calypso.calypso.dto.algo.BugDTO;
import org.calypso.calypso.mapper.algo.BugMapper;
import org.calypso.calypso.model.algo.Bug;
import org.calypso.calypso.service.algo.BugService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bugs")
public class BugController {

    private final BugService bugService;
    private final BugMapper bugMapper;

    public BugController(BugService bugService, BugMapper bugMapper) {
        this.bugService = bugService;
        this.bugMapper = bugMapper;
    }

    @GetMapping
    public ResponseEntity<List<BugDTO>> getAllBugs() {
        List<BugDTO> bugs = bugService.getAllBugs();
        if (bugs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bugs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BugDTO> getBugById(@PathVariable Long id) {
        BugDTO bug = bugService.getBugById(id);
        if (bug == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bug);
    }

    @PostMapping
    public ResponseEntity<BugDTO> createBug(@RequestBody BugDTO bugDTO) {
        Bug bug = bugMapper.convertToEntity(bugDTO);
        BugDTO createdBug = bugService.createBug(bug);
        if (createdBug == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(createdBug);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BugDTO> updateBug(@PathVariable Long id, @RequestBody BugDTO bugDTO) {
        Bug bug = bugMapper.convertToEntity(bugDTO);
        BugDTO updatedBug = bugService.updateBug(id, bug);
        if (updatedBug == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedBug);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBug(@PathVariable Long id) {
        boolean isDeleted = bugService.deleteBug(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}