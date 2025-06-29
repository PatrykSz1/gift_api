package com.patryk.gift_api.mapper;

import com.patryk.gift_api.model.Kid;
import com.patryk.gift_api.model.command.CreateKidCommand;
import com.patryk.gift_api.model.dto.KidDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

@Component
public class KidMapper {

    public static Kid mapFromCommand(CreateKidCommand command) {
        Map<String,String> params = command.getParams();
        return Kid.builder()
                .firstName(params.get("firstName"))
                .lastName(params.get("lastName"))
                .birthDate(LocalDate.parse(params.get("birthDate")))
                .build();
    }

    public KidDto mapFromEntityToDto(Kid kid){
        return KidDto.builder()
                .id(kid.getId())
                .firstName(kid.getFirstName())
                .lastName(kid.getLastName())
                .birthDate(kid.getBirthDate())
                .version(kid.getVersion())
                .build();
    }
}
