package com.patryk.gift_api.repository;

import com.patryk.gift_api.model.Gift;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GiftRepository extends JpaRepository<Gift, Integer> {
    List<Gift> findByKidId(int kidId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Gift> findByIdAndKidId(int giftId, int kidId);
}