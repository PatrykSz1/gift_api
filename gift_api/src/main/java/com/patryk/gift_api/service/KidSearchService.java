package com.patryk.gift_api.service;

import com.patryk.gift_api.model.Kid;
import com.patryk.gift_api.model.dto.KidDto;
import com.patryk.gift_api.model.dto.SearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KidSearchService {

    private final KidSearchRepository kidSearchRepository;

    public List<KidDto> searchKids(SearchRequest searchRequest) {
        return kidSearchRepository.searchKids(searchRequest).stream()
                .map(this::mapToDto)
                .toList();
    }

    private KidDto mapToDto(Kid kid) {
        KidDto kidDto = new KidDto();
        kidDto.setFirstName(kid.getFirstName());
        kidDto.setLastName(kid.getLastName());
        return kidDto;
    }
}
