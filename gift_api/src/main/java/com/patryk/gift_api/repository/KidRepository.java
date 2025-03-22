package com.patryk.gift_api.repository;

import com.patryk.gift_api.model.Kid;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface KidRepository extends JpaRepository<Kid, Long> {
    @Query("select distinct c from Kid c " +
            "join fetch c.gifts g " +
            "where g.price > :price")
    List<Kid> findKidsWithGiftsWhichPriceOver100(@Param("price") BigDecimal price);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Kid> findWithLockingById(int id);

//    List<Kid> findKidsByGiftPrice
//    @Query("SELECT k FROM Kid k JOIN k.gifts g WHERE g.price > :price")
//    Stream<Kid> findKidsWithGiftsWhichPriceOver100(@Param("price") BigDecimal price);
//
//    Page<Kid> findByGiftsPriceGreaterThan(BigDecimal price, Pageable pageable);
}