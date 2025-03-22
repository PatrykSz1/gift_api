package com.patryk.gift_api.mapper;

import com.patryk.gift_api.model.Girl;
import com.patryk.gift_api.model.command.CreateGirlCommand;
import com.patryk.gift_api.model.dto.KidDto;
import org.springframework.stereotype.Component;

@Component
public class GirlMapper {

    public static Girl mapFromCommand(CreateGirlCommand command) {
        return Girl.builder()
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .birthDate(command.getBirthDate())
                .colorOfDress(command.getColorOfDress())
                .build();
    }

    public static KidDto mapFromEntityToDto(Girl girl) {
        return KidDto.builder()
                .firstName(girl.getFirstName())
                .lastName(girl.getLastName())
                .birthDate(girl.getBirthDate())
                .build();
    }
}