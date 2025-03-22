package com.patryk.gift_api.mapper;

import com.patryk.gift_api.model.Boy;
import com.patryk.gift_api.model.command.CreateBoyCommand;
import com.patryk.gift_api.model.dto.KidDto;
import org.springframework.stereotype.Component;

@Component
public class BoyMapper {

    public static Boy mapFromCommand(CreateBoyCommand command) {
        return Boy.builder()
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .birthDate(command.getBirthDate())
                .colorOfShirt(command.getColorOfShirt())
                .build();
    }

    public static KidDto mapFromEntityToDto(Boy boy) {
        return KidDto.builder()
                .firstName(boy.getFirstName())
                .lastName(boy.getLastName())
                .birthDate(boy.getBirthDate())
                .build();
    }
}