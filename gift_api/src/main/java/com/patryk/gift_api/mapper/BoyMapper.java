package com.patryk.gift_api.mapper;

import com.patryk.gift_api.model.Boy;
import com.patryk.gift_api.model.command.CreateKidCommand;
import com.patryk.gift_api.model.dto.BoyDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class BoyMapper {

    public Boy mapFromCommand(CreateKidCommand command) {
        return Boy.builder()
                .firstName(command.getParams().get("firstName"))
                .lastName(command.getParams().get("lastName"))
                .birthDate(LocalDate.parse(command.getParams().get("birthDate")))
                .colorOfShirt(command.getParams().get("colorOfShirt"))
                .build();
    }

    public BoyDto mapFromEntityToDto(Boy boy) {
        return BoyDto.builder()
                .id(boy.getId())
                .type(boy.getClass().getSimpleName())
                .firstName(boy.getFirstName())
                .lastName(boy.getLastName())
                .birthDate(boy.getBirthDate())
                .colorOfShirt(boy.getColorOfShirt())
                .build();
    }
}