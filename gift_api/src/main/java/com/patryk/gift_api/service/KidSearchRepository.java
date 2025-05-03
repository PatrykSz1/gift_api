package com.patryk.gift_api.service;

import com.patryk.gift_api.model.Kid;
import com.patryk.gift_api.model.dto.SearchRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class KidSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Kid> searchKids(SearchRequest searchRequest) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Kid> query = cb.createQuery(Kid.class);
        Root<Kid> root = query.from(Kid.class);

        List<Predicate> predicates = new ArrayList<>();

        if (searchRequest.getFirstName() != null && !searchRequest.getFirstName().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("firstName")), "%" + searchRequest.getFirstName().toLowerCase() + "%"));
        }
        if (searchRequest.getLastName() != null && !searchRequest.getLastName().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("lastName")), "%" + searchRequest.getLastName().toLowerCase() + "%"));
        }
        if (searchRequest.getBirthDate() != null) {
            predicates.add(cb.equal(root.get("birthDate"), searchRequest.getBirthDate()));
        }
        if (searchRequest.getMinGifts() != null) {
            predicates.add(cb.greaterThanOrEqualTo(cb.size(root.get("gifts")), searchRequest.getMinGifts()));
        }
        if (searchRequest.getMaxGifts() != null) {
            predicates.add(cb.lessThanOrEqualTo(cb.size(root.get("gifts")), searchRequest.getMaxGifts()));
        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query).getResultList();
    }
}
// TODO specyficatiion
// https://www.baeldung.com/rest-api-search-language-spring-data-specifications#filter-using-specification