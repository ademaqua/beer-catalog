package com.ademaqua.beercatalog.beer.entity.dto;

import lombok.Data;

@Data
public class BeerDto {

    private Long id;
    private String name;
    private Double graduation;
    private String type;
    private String description;
    private Long manufacturerId;

}
