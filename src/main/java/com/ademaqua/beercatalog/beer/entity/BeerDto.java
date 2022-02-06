package com.ademaqua.beercatalog.beer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerDto {
    private Long id;
    private String name;
    private Double graduation;
    private String type;
    private String description;
    private Long manufacturerId;
}
