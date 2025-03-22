package com.patryk.gift_api.service.operations;

import com.patryk.gift_api.mapper.BoyMapper;
import com.patryk.gift_api.model.Boy;
import com.patryk.gift_api.model.Kid;
import com.patryk.gift_api.model.command.CreateBoyCommand;
import com.patryk.gift_api.model.command.CreateKidCommand;
import com.patryk.gift_api.model.dto.BoyDto;
import com.patryk.gift_api.model.dto.KidDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component("BOY")
@RequiredArgsConstructor
public class BoyOperations implements KidOperations {

    private final BoyMapper boyMapper;

    @Override
    public Kid create(CreateKidCommand createKidCommand) {
        CreateBoyCommand createBoyCommand = new CreateBoyCommand(
                createKidCommand.getParams().get("firstName"),
                createKidCommand.getParams().get("lastName"),
                LocalDate.parse(createKidCommand.getParams().get("birthDate")),
                createKidCommand.getParams().get("colorOfShirt")
        );
        return BoyMapper.mapFromCommand(createBoyCommand);
    }

    @Override
    public KidDto mapToDto(Kid kid) {
        return BoyMapper.mapFromEntityToDto((Boy) kid);
    }
}
