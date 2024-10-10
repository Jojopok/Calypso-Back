package org.calypso.calypso.repository.algo;

import org.calypso.calypso.model.algo.Bug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BugRepository extends JpaRepository<Bug, Long> {
}