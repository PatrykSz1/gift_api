package com.patryk.gift_api.service;

import com.patryk.gift_api.mapper.KidMapper;
import com.patryk.gift_api.model.Kid;
import com.patryk.gift_api.model.command.CreateKidCommand;
import com.patryk.gift_api.model.dto.KidDto;
import com.patryk.gift_api.repository.KidRepository;
import com.patryk.gift_api.service.operations.KidOperations;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KidService {

    private final Map<String, KidOperations> kidOperationsMap;
    private final KidRepository kidRepository;
    private final KidMapper kidMapper;

    public KidDto addKid(CreateKidCommand createKidCommand) {
        String type = createKidCommand.getParams().get("type");

        KidOperations kidOperations = kidOperationsMap.get(type);
        if (kidOperations == null) {
            throw new IllegalArgumentException("Unknown kid type: " + type);
        }

        Kid kid = kidOperations.create(createKidCommand);
        kid = kidRepository.save(kid);

        return kidOperations.mapToDto(kid);
    }

    public List<KidDto> getAllKids() {
        return kidRepository.findAll().stream()
                .map(kidMapper::mapFromEntityToDto)
                .collect(Collectors.toList());
    }

    public KidDto getKidById(int kidId) {
        Kid kid = kidRepository.findById((long) kidId)
                .orElseThrow(() -> new RuntimeException("Kid not found with ID: " + kidId));
        return kidMapper.mapFromEntityToDto(kid);
    }

    public KidDto updateKid(int kidId, CreateKidCommand createKidCommand) {
        Kid kid = kidRepository.findById((long) kidId)
                .orElseThrow(() -> new RuntimeException("Kid not found with ID: " + kidId));

        Kid updatedKid = KidMapper.mapFromCommand(createKidCommand);

        updatedKid.setId(kid.getId());
        updatedKid = kidRepository.save(updatedKid);

        return kidMapper.mapFromEntityToDto(updatedKid);
    }

    public void deleteKid(int kidId) {
        if (!kidRepository.existsById((long) kidId)) {
            throw new RuntimeException("Kid not found with ID: " + kidId);
        }
        kidRepository.deleteById((long) kidId);
    }
}