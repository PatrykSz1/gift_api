package com.patryk.gift_api.model.dto;

import lombok.Data;

@Data
public class SearchCriteria {

    private String key;
    private String operation;
    private Object value;
}

