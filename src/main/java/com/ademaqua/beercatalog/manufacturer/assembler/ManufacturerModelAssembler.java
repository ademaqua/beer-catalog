package com.ademaqua.beercatalog.manufacturer.assembler;

import com.ademaqua.beercatalog.manufacturer.controller.ManufacturerController;
import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ManufacturerModelAssembler implements RepresentationModelAssembler<Manufacturer, EntityModel<Manufacturer>> {


    @Override
    public EntityModel<Manufacturer> toModel(Manufacturer manufacturer) {
        return EntityModel.of(manufacturer,
                linkTo(methodOn(ManufacturerController.class).getById(manufacturer.getId())).withSelfRel(),
                linkTo(methodOn(ManufacturerController.class).deleteManufacturerById(manufacturer.getId())).withRel("delete"),
                linkTo(methodOn(ManufacturerController.class).updateManufacturer(manufacturer.getId(), manufacturer)).withRel("update"));
    }

    @Override
    public CollectionModel<EntityModel<Manufacturer>> toCollectionModel(Iterable<? extends Manufacturer> entities) {
        CollectionModel<EntityModel<Manufacturer>> manufacturerModel = RepresentationModelAssembler.super.toCollectionModel(entities);
        manufacturerModel.add(linkTo(methodOn(ManufacturerController.class).getAll()).withSelfRel());
        manufacturerModel.add(Link.of(linkTo(methodOn(ManufacturerController.class)).toUriComponentsBuilder().build().toUriString()).withRel("add"));
        return manufacturerModel;
    }
}
