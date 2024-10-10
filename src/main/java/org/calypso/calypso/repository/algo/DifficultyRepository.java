package org.calypso.calypso.repository.algo;

import org.calypso.calypso.model.algo.Difficulty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DifficultyRepository extends JpaRepository<Difficulty, Long> {
}