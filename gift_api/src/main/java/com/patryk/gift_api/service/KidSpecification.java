package com.patryk.gift_api.service;

import com.patryk.gift_api.model.Kid;
import com.patryk.gift_api.model.dto.SearchCriteria;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
@AllArgsConstructor
public class KidSpecification implements Specification<Kid> {

    private final SearchCriteria searchCriteria;

    @Override
    public Predicate toPredicate(Root<Kid> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Path<?> path = root.get(searchCriteria.getKey());
        Object value = searchCriteria.getValue();

        return switch (searchCriteria.getOperation()) {
            case ":" -> builder.like(builder.lower(path.as(String.class)), "%" + value.toString().toLowerCase() + "%");
            case "=" -> builder.equal(path, value);
            case ">" -> builder.greaterThan(path.as(Comparable.class), (Comparable) value);
            case "<" -> builder.lessThan(path.as(Comparable.class), (Comparable) value);
            case ">=" -> builder.greaterThanOrEqualTo(path.as(Comparable.class), (Comparable) value);
            case "<=" -> builder.lessThanOrEqualTo(path.as(Comparable.class), (Comparable) value);
            default -> throw new IllegalArgumentException("Unsupported operation: " + searchCriteria.getOperation());
        };
    }
}