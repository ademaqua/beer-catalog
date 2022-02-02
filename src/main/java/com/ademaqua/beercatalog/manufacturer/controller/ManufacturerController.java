package com.ademaqua.beercatalog.manufacturer.controller;

import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

public interface ManufacturerController {

    ResponseEntity<CollectionModel<EntityModel<Manufacturer>>> getAll();

    ResponseEntity<EntityModel<Manufacturer>> getById(Long id);

    ResponseEntity<EntityModel<Manufacturer>> addManufacturer(Manufacturer manufacturer);

    ResponseEntity<Void> deleteManufacturerById(Long id);

    ResponseEntity<EntityModel<Manufacturer>> updateManufacturer(Long id, Manufacturer manufacturer);
}
