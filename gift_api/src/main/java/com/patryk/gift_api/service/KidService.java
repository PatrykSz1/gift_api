package com.patryk.gift_api.service;

import com.patryk.gift_api.mapper.KidMapper;
import com.patryk.gift_api.model.Kid;
import com.patryk.gift_api.model.command.CreateKidCommand;
import com.patryk.gift_api.model.command.UpdateKidCommand;
import com.patryk.gift_api.model.dto.KidDto;
import com.patryk.gift_api.repository.KidRepository;
import com.patryk.gift_api.service.operations.KidOperations;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KidService {

    private final Map<String, KidOperations> kidOperationsMap;
    private final KidRepository kidRepository;
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

    public List<KidDto> getAllKids() {
        return kidRepository.findAll().stream()
                .map(kidMapper::mapFromEntityToDto)
                .toList();
    }
    public KidDto getKidById(int kidId) {
        Kid kid = kidRepository.findById(kidId)
                .orElseThrow(() -> new EntityNotFoundException(KID_NOT_FOUND_MESSAGE + kidId));
        return kidMapper.mapFromEntityToDto(kid);
    }

    @Transactional
    public KidDto updateKid(UpdateKidCommand command, int kidId) {
        Kid existingKid = kidRepository.findById(kidId)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono Kida o ID: " + kidId));

        if (existingKid.getVersion() != command.getVersion()) {
            throw new OptimisticLockException("Obiekt został zmieniony przez innego użytkownika.");
        }

        existingKid.setFirstName(command.getFirstName());
        existingKid.setLastName(command.getLastName());
        existingKid.setBirthDate(command.getBirthDate());

        kidRepository.save(existingKid);

        return kidMapper.mapFromEntityToDto(existingKid);
    }


    public void deleteKid(int kidId) {
        if (!kidRepository.existsById(kidId)) {
            throw new IllegalArgumentException(KID_NOT_FOUND_MESSAGE+ kidId);
        }
        kidRepository.deleteById(kidId);
    }
}