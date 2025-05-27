package com.patryk.gift_api.model.dto;

import lombok.Data;

@Data
public class SearchCriteria {

    private String key;
    private String operation;
    private Object value;
}

//    private String firstName;
//    private String lastName;
//    private LocalDate birthDate;
//    private Integer minGifts;
//    private Integer maxGifts;
