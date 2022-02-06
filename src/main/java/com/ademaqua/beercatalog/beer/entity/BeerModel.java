package com.ademaqua.beercatalog.beer.entity;

import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName(value = "beer")
@Relation(collectionRelation = "beers")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BeerModel extends RepresentationModel<BeerModel> {
    private Long id;
    private String name;
    private Double graduation;
    private String type;
    private String description;
    private Manufacturer manufacturer;
}
