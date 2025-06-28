package com.patryk.gift_api.service.operations;

import com.patryk.gift_api.mapper.GirlMapper;
import com.patryk.gift_api.model.Girl;
import com.patryk.gift_api.model.Kid;
import com.patryk.gift_api.model.command.CreateKidCommand;
import com.patryk.gift_api.model.dto.KidDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("GIRL")
@RequiredArgsConstructor
public class GirlOperations implements KidOperations {

    private final GirlMapper girlMapper;
//    private static final String INSERT_QUERY = "INSERT INTO kid(DTYPE, FIRST_NAME, LAST_NAME, BIRTH_DATE,COLOR_OF_DRESS) VALUES %s";

    @Override
    public String getInsertQuery() {
        return "INSERT INTO kid(DTYPE, FIRST_NAME, LAST_NAME, BIRTH_DATE, COLOR_OF_DRESS, VERSION) VALUES %s";
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
        return GirlMapper.mapFromCommand(createKidCommand);
    }

    @Override
    public KidDto mapToDto(Kid kid) {
        return GirlMapper.mapFromEntityToDto((Girl) kid);
    }
}