package com.patryk.gift_api.model.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateGirlCommand {

    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String colorOfDress;
}