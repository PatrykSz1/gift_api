package com.patryk.gift_api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class KidDto {

    private int id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
}
