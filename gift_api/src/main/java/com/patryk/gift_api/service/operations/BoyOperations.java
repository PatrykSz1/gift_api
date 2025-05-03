package com.patryk.gift_api.service.operations;

import com.patryk.gift_api.mapper.BoyMapper;
import com.patryk.gift_api.model.Boy;
import com.patryk.gift_api.model.Kid;
import com.patryk.gift_api.model.command.CreateKidCommand;
import com.patryk.gift_api.model.dto.KidDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("BOY")
@RequiredArgsConstructor
public class BoyOperations implements KidOperations {

    private final BoyMapper boyMapper;

    @Override
    public Kid create(CreateKidCommand createKidCommand) {

        return boyMapper.mapFromCommand(createKidCommand);
    }

    @Override
    public KidDto mapToDto(Kid kid) {
        return boyMapper.mapFromEntityToDto((Boy) kid);
    }
}
