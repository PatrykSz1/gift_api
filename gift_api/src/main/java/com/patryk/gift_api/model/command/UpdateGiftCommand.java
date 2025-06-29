package com.patryk.gift_api.model.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class UpdateGiftCommand {

    private  String name;
    private BigDecimal price;
    private int version;
}
