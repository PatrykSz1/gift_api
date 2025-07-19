package com.patryk.gift_api.service;

import com.patryk.gift_api.mapper.KidMapper;
import com.patryk.gift_api.model.Kid;
import com.patryk.gift_api.model.KidView;
import com.patryk.gift_api.model.command.CreateKidCommand;
import com.patryk.gift_api.model.command.UpdateKidCommand;
import com.patryk.gift_api.model.dto.KidDto;
import com.patryk.gift_api.model.dto.SearchCriteria;
import com.patryk.gift_api.repository.KidRepository;
import com.patryk.gift_api.repository.KidViewRepository;
import com.patryk.gift_api.service.operations.KidOperations;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KidService {

    private final Map<String, KidOperations> kidOperationsMap;
    private final KidRepository kidRepository;
    private final KidViewRepository kidViewRepository;
    private final KidMapper kidMapper;
    private static final String KID_NOT_FOUND_MESSAGE = "Kid not found with ID: ";

    public KidDto addKid(CreateKidCommand createKidCommand) {
        String type = createKidCommand.getType().toUpperCase();

        KidOperations kidOperations = kidOperationsMap.get(type);
        if (kidOperations == null) {
            throw new IllegalArgumentException("Unknown kid type: " + type);
        }

        Kid kid = kidOperations.create(createKidCommand);
        kid = kidRepository.save(kid);

        return kidOperations.mapToDto(kid);
    }

    public Page<KidDto> search(Pageable pageable, List<SearchCriteria> params) {
        Specification<KidView> spec = Specification.where(null);
        for (SearchCriteria param : params) {
            spec = spec.and(new KidSpecification(param));
        }
        return kidViewRepository.findAll(spec, pageable)
                .map(kidMapper::mapToDto);
    }

    public KidDto getKidById(int kidId) {
        Kid kid = kidRepository.findById(kidId)
                .orElseThrow(() -> new EntityNotFoundException(KID_NOT_FOUND_MESSAGE + kidId));
        return kidMapper.mapToDto(kid);
    }

    @Transactional
    public KidDto updateKid(UpdateKidCommand command, int kidId) {
        Kid existingKid = kidRepository.findById(kidId)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono Kida o ID: " + kidId));


        Kid toSave = kidMapper.mapForUpdate(existingKid, command);
        kidRepository.save(toSave);

        return kidMapper.mapToDto(toSave);
    }


    public void deleteKid(int kidId) {
        if (!kidRepository.existsById(kidId)) {
            throw new IllegalArgumentException(KID_NOT_FOUND_MESSAGE + kidId);
        }
        kidRepository.deleteById(kidId);
    }
}