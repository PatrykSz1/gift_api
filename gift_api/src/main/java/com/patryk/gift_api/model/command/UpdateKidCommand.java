package com.patryk.gift_api.model.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UpdateKidCommand {

    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String gender;
}