package com.patryk.gift_api.service;

import com.patryk.gift_api.mapper.KidMapper;
import com.patryk.gift_api.model.Kid;
import com.patryk.gift_api.model.dto.KidDto;
import com.patryk.gift_api.model.dto.SearchCriteria;
import com.patryk.gift_api.repository.KidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KidSearchService {

    private final KidRepository kidRepository;
    private final KidMapper kidMapper;

    public Page <KidDto> search(Pageable pageable, List<SearchCriteria> params) {
        Specification<Kid> spec = Specification.where(null);

        for (SearchCriteria param : params) {
            spec = spec.and(new KidSpecification(param));
        }

        return kidRepository.findAll(spec,pageable)
                .map(kidMapper::mapFromEntityToDto);
    }

    private KidDto mapToDto(Kid kid) {
        KidDto kidDto = new KidDto();
        kidDto.setFirstName(kid.getFirstName());
        kidDto.setLastName(kid.getLastName());
        return kidDto;
    }

//    public List<KidDto> searchKids(SearchRequest searchRequest) {
//        return kidSearchRepository.searchKids((List<SearchRequest>) searchRequest).stream()
//                .map(this::mapToDto)
//                .toList();
//    }
//
//    private KidDto mapToDto(Kid kid) {
//        KidDto kidDto = new KidDto();
//        kidDto.setFirstName(kid.getFirstName());
//        kidDto.setLastName(kid.getLastName());
//        return kidDto;
//    }
}
