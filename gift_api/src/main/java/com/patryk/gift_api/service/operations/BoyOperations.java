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
//    private static final String INSERT_QUERY = "INSERT INTO kid(DTYPE, FIRST_NAME, LAST_NAME, BIRTH_DATE,COLOR_OF_SHIRT) VALUES %s";


    @Override
    public String getInsertQuery() {
        return "INSERT INTO kid (DTYPE, FIRST_NAME, LAST_NAME, BIRTH_DATE, COLOR_OF_SHIRT, VERSION) VALUES %s";
    }

    @Override
    public String formatParams(String[] data) {
        return String.format("(\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%d\"),",
                data[0],
                data[1],
                data[2],
                data[3],
                data[4],
                0
        );
    }

    @Override
    public Kid create(CreateKidCommand createKidCommand) {
        return boyMapper.mapFromCommand(createKidCommand);
    }

    @Override
    public KidDto mapToDto(Kid kid) {
        return boyMapper.mapFromEntityToDto((Boy) kid);
    }
}
