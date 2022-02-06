package com.ademaqua.beercatalog.manufacturer.entity;

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
@JsonRootName(value = "manufacturer")
@Relation(collectionRelation = "manufacturers")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ManufacturerModel extends RepresentationModel<ManufacturerModel> {

    private Long id;
    private String name;
    private String nationality;
}
