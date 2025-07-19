package com.patryk.gift_api.mapper;

import com.patryk.gift_api.model.Kid;
import com.patryk.gift_api.model.KidView;
import com.patryk.gift_api.model.command.CreateKidCommand;
import com.patryk.gift_api.model.command.UpdateKidCommand;
import com.patryk.gift_api.model.dto.KidDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

@Component
public class KidMapper {

    public static Kid mapFromCommand(CreateKidCommand command) {
        Map<String, String> params = command.getParams();
        return Kid.builder()
                .firstName(params.get("firstName"))
                .lastName(params.get("lastName"))
                .birthDate(LocalDate.parse(params.get("birthDate")))
                .build();
    }

    public KidDto mapToDto(Kid kid) {
        return KidDto.builder()
                .id(kid.getId())
                .type(kid.getClass().getSimpleName())
                .firstName(kid.getFirstName())
                .lastName(kid.getLastName())
                .birthDate(kid.getBirthDate())
                .version(kid.getVersion())
                .giftCount(kid.getGifts().size())
                .build();
    }

    public KidDto mapToDto(KidView kid) {
        return KidDto.builder()
                .id(kid.getId())
                .type(kid.getDtype())
                .firstName(kid.getFirstName())
                .lastName(kid.getLastName())
                .birthDate(kid.getBirthDate())
                .giftCount(kid.getGiftCount())
                .build();
    }

    public Kid mapForUpdate(Kid existingKid, UpdateKidCommand command) {
        Kid updated = existingKid.clone();
        updated.setFirstName(command.getFirstName());
        updated.setLastName(command.getLastName());
        updated.setBirthDate(command.getBirthDate());
        updated.setVersion(command.getVersion());
        return updated;
    }
}
