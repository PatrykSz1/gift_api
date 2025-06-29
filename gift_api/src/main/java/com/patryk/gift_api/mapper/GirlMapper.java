package com.patryk.gift_api.mapper;

import com.patryk.gift_api.model.Girl;
import com.patryk.gift_api.model.command.CreateKidCommand;
import com.patryk.gift_api.model.dto.GirlDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class GirlMapper {

    public static Girl mapFromCommand(CreateKidCommand command) {
        return Girl.builder()
                .firstName(command.getParams().get("firstName"))
                .lastName(command.getParams().get("lastName"))
                .birthDate(LocalDate.parse(command.getParams().get("birthDate")))
                .colorOfDress(command.getParams().get("colorOfDress"))
                .build();
    }

    public static GirlDto mapFromEntityToDto(Girl girl) {
        return GirlDto.builder()
                .type(girl.getClass().getSimpleName())
                .firstName(girl.getFirstName())
                .lastName(girl.getLastName())
                .birthDate(girl.getBirthDate())
                .colorOfDress(girl.getColorOfDress())
                .build();
    }
}