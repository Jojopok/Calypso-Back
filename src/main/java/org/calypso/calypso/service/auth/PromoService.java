package org.calypso.calypso.service.auth;

import org.calypso.calypso.model.auth.Promo;
import org.calypso.calypso.model.auth.User;
import org.calypso.calypso.repository.auth.PromoRepository;
import org.calypso.calypso.repository.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromoService {

    private final PromoRepository promoRepository;

    @Autowired
    private UserRepository userRepository;

    public PromoService(PromoRepository promoRepository) {
        this.promoRepository = promoRepository;
    }

    public List<Promo> getAllPromos() {
        return promoRepository.findAll();
    }

    public Promo getPromoById(Long id) {
        return promoRepository.findById(id).orElse(null);
    }

    public Promo createPromo(Promo promo) {
        return promoRepository.save(promo);
    }

    public Promo updatePromo(Long id, Promo promo) {
        if (!promoRepository.existsById(id)) {
            return null;
        }
        promo.setId(id);
        return promoRepository.save(promo);
    }

    public boolean deletePromo(Long id) {
        if (!promoRepository.existsById(id)) {
            return false;
        }
        promoRepository.deleteById(id);
        return true;
    }
}