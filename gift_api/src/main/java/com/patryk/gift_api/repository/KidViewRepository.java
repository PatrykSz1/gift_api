package com.patryk.gift_api.repository;

import com.patryk.gift_api.model.KidView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KidViewRepository extends JpaRepository<KidView, Integer> {

    Page<KidView> findAll(Specification<KidView> spec, Pageable pageable);
}
