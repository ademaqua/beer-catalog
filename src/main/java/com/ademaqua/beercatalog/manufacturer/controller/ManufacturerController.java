package com.ademaqua.beercatalog.manufacturer.controller;

import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;
import com.ademaqua.beercatalog.manufacturer.entity.ManufacturerModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

public interface ManufacturerController {

    ResponseEntity<PagedModel<ManufacturerModel>> getAll(@RequestParam(defaultValue = "0") int pageNumber,
                                                         @RequestParam(defaultValue = "25") int pageSize,
                                                         @RequestParam(defaultValue = "asc") String order);

    ResponseEntity<ManufacturerModel> getById(Long id);

    ResponseEntity<ManufacturerModel> addManufacturer(Manufacturer manufacturer);

    ResponseEntity<Void> deleteManufacturerById(Long id);

    ResponseEntity<ManufacturerModel> updateManufacturer(Long id, Manufacturer manufacturer);
}
