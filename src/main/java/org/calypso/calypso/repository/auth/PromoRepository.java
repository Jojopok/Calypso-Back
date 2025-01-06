package org.calypso.calypso.repository.auth;

import org.calypso.calypso.model.auth.Promo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromoRepository extends JpaRepository<Promo, Long> {
    Promo findByName(String nonexistentPromo);
}