package com.patryk.gift_api.service.operations;

import com.patryk.gift_api.model.Kid;
import com.patryk.gift_api.model.command.CreateKidCommand;
import com.patryk.gift_api.model.dto.KidDto;

public interface KidOperations {

    Kid create(CreateKidCommand command);

    KidDto mapToDto(Kid kid);

    String getInsertQuery();

    String formatParams(String[] data);
}