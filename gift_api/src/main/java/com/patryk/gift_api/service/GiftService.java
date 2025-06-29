package com.patryk.gift_api.service;

import com.patryk.gift_api.model.Gift;
import com.patryk.gift_api.model.Kid;
import com.patryk.gift_api.model.command.CreateGiftCommand;
import com.patryk.gift_api.model.command.UpdateGiftCommand;
import com.patryk.gift_api.model.dto.GiftDto;
import com.patryk.gift_api.repository.GiftRepository;
import com.patryk.gift_api.repository.KidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GiftService {


    private final GiftRepository giftRepository;
    private final KidRepository kidRepository;

    @Transactional
    public GiftDto addGiftToKid(CreateGiftCommand createGiftCommand, int kidId) {
        Kid kid = kidRepository.findById(kidId)
                .orElseThrow(() -> new IllegalArgumentException("Kid with ID " + kidId + " not found"));

        Gift gift = Gift.builder()
                .name(createGiftCommand.getName())
                .price(createGiftCommand.getPrice())
                .kid(kid)
                .build();

        gift = giftRepository.save(gift);
        return mapToDto(gift);
    }
    public List<GiftDto> getGiftsByKidId(int kidId) {
        List<Gift> gifts = giftRepository.findByKidId(kidId);
        return gifts.stream()
                .map(this::mapToDto)
                .toList();
    }

    @Transactional
    public GiftDto updateGiftForKid(int kidId, int giftId, UpdateGiftCommand updateGiftCommand) {
        Gift gift = giftRepository.findByIdAndKidId(giftId, kidId)
                .orElseThrow(() -> new IllegalArgumentException("Gift with ID " + giftId + " not found for Kid ID " + kidId));

        gift.setName(updateGiftCommand.getName());
        gift.setPrice(updateGiftCommand.getPrice());

        gift = giftRepository.save(gift);
        return mapToDto(gift);
    }

    @Transactional
    public void deleteGift(int giftId) {
        if (!giftRepository.existsById(giftId)) {
            throw new IllegalArgumentException("Gift with ID " + giftId + " not found");
        }
        giftRepository.deleteById(giftId);
    }

    private GiftDto mapToDto(Gift gift) {
        return GiftDto.builder()
                .name(gift.getName())
                .price(gift.getPrice())
                .build();
    }
}
