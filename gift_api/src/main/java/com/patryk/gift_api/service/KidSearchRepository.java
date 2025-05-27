//package com.patryk.gift_api.service;

//
//@Repository
//public class KidSearchRepository {
//
//

//    @PersistenceContext
//    private EntityManager entityManager;
//
//    public List<Kid> searchKids(SearchRequest searchRequest) {
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Kid> query = cb.createQuery(Kid.class);
//        Root<Kid> root = query.from(Kid.class);
//
//        List<Predicate> predicates = new ArrayList<>();
//
//        if (searchRequest.getFirstName() != null && !searchRequest.getFirstName().isEmpty()) {
//            predicates.add(cb.like(cb.lower(root.get("firstName")), "%" + searchRequest.getFirstName().toLowerCase() + "%"));
//        }
//        if (searchRequest.getLastName() != null && !searchRequest.getLastName().isEmpty()) {
//            predicates.add(cb.like(cb.lower(root.get("lastName")), "%" + searchRequest.getLastName().toLowerCase() + "%"));
//        }
//        if (searchRequest.getBirthDate() != null) {
//            predicates.add(cb.equal(root.get("birthDate"), searchRequest.getBirthDate()));
//        }
//        if (searchRequest.getMinGifts() != null) {
//            predicates.add(cb.greaterThanOrEqualTo(cb.size(root.get("gifts")), searchRequest.getMinGifts()));
//        }
//        if (searchRequest.getMaxGifts() != null) {
//            predicates.add(cb.lessThanOrEqualTo(cb.size(root.get("gifts")), searchRequest.getMaxGifts()));
//        }
//
//        query.where(cb.and(predicates.toArray(new Predicate[0])));
//
//        return entityManager.createQuery(query).getResultList();
//    }

