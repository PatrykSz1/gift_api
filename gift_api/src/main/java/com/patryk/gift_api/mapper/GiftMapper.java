package com.patryk.gift_api.mapper;

import com.patryk.gift_api.model.Gift;
import com.patryk.gift_api.model.command.CreateGiftCommand;
import com.patryk.gift_api.model.dto.GiftDto;
import org.springframework.stereotype.Component;

@Component
public class GiftMapper {

    public static Gift fromCommandToEntity(CreateGiftCommand giftCommand) {
        return Gift.builder()
                .name(giftCommand.getName())
                .price(giftCommand.getPrice())
                .build();
    }

    public static GiftDto fromEntityToDto(Gift gift) {
        return GiftDto.builder()
                .name(gift.getName())
                .price(gift.getPrice())
                .build();
    }
}
