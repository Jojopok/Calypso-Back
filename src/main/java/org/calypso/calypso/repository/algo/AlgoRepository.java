package org.calypso.calypso.repository.algo;

import org.calypso.calypso.model.algo.Algo;
import org.calypso.calypso.model.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlgoRepository  extends JpaRepository<Algo, Long> {
}
