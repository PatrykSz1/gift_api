package com.patryk.gift_api.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SearchRequest {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Integer minGifts;
    private Integer maxGifts;
}