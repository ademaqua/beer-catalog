package com.ademaqua.beercatalog.manufacturer.assembler;

import com.ademaqua.beercatalog.manufacturer.controller.impl.ManufacturerControllerImpl;
import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;
import com.ademaqua.beercatalog.manufacturer.entity.ManufacturerModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ManufacturerModelAssembler implements RepresentationModelAssembler<Manufacturer, ManufacturerModel> {


    @Override
    public ManufacturerModel toModel(Manufacturer manufacturer) {
        ManufacturerModel manufacturerModel = new ManufacturerModel();

        manufacturerModel.add(
                linkTo(methodOn(ManufacturerControllerImpl.class).getById(manufacturer.getId())).withSelfRel(),
                linkTo(methodOn(ManufacturerControllerImpl.class).deleteManufacturerById(manufacturer.getId())).withRel("delete"),
                linkTo(methodOn(ManufacturerControllerImpl.class).updateManufacturer(manufacturer.getId(), manufacturer)).withRel("update"));
        manufacturerModel.setId(manufacturer.getId());
        manufacturerModel.setName(manufacturer.getName());
        manufacturerModel.setNationality(manufacturer.getNationality());
        return manufacturerModel;
    }
}
