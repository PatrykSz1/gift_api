package com.patryk.gift_api.service.operations;

import com.patryk.gift_api.mapper.GirlMapper;
import com.patryk.gift_api.model.Girl;
import com.patryk.gift_api.model.Kid;
import com.patryk.gift_api.model.command.CreateGirlCommand;
import com.patryk.gift_api.model.command.CreateKidCommand;
import com.patryk.gift_api.model.dto.GirlDto;
import com.patryk.gift_api.model.dto.KidDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component("GIRL")
@RequiredArgsConstructor
public class GirlOperations implements KidOperations {

    private final GirlMapper girlMapper;

    @Override
    public Kid create(CreateKidCommand createKidCommand) {
        CreateGirlCommand createGirlCommand = new CreateGirlCommand(
                createKidCommand.getParams().get("firstName"),
                createKidCommand.getParams().get("lastName"),
                LocalDate.parse(createKidCommand.getParams().get("birthDate")),
                createKidCommand.getParams().get("colorOfDress")
        );
        return GirlMapper.mapFromCommand(createGirlCommand);
    }

    @Override
    public KidDto mapToDto(Kid kid) {
        return GirlMapper.mapFromEntityToDto((Girl) kid);
    }
}