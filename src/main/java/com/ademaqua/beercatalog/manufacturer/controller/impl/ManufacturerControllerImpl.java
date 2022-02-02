package com.ademaqua.beercatalog.manufacturer.controller.impl;

import com.ademaqua.beercatalog.manufacturer.assembler.ManufacturerModelAssembler;
import com.ademaqua.beercatalog.manufacturer.controller.ManufacturerController;
import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;
import com.ademaqua.beercatalog.manufacturer.service.ManufacturerService;
import com.ademaqua.beercatalog.manufacturer.validator.ManufacturerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

@RestController
@RequestMapping("/api/v1/manufacturers")
public class ManufacturerControllerImpl implements ManufacturerController {

    @Autowired
    private ManufacturerModelAssembler manufacturerModelAssembler;

    @Autowired
    private ManufacturerValidator manufacturerValidator;

    @Autowired
    private ManufacturerService manufacturerService;

    @Override
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Manufacturer>>> getAll() {
        return ResponseEntity.ok(manufacturerModelAssembler.toCollectionModel(manufacturerService.findAllManufacturers()));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Manufacturer>> getById(@PathVariable("id") Long id) {
        Optional<Manufacturer> manufacturer = manufacturerService.findManufacturerById(id);
        if (manufacturer.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Manufacturer not found!");
        }
        return ResponseEntity.ok(manufacturerModelAssembler.toModel(manufacturer.get()));
    }

    @Override
    @PostMapping
    public ResponseEntity<EntityModel<Manufacturer>> addManufacturer(@RequestBody Manufacturer manufacturer) {
        if (!manufacturerValidator.validate(manufacturer)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data");
        }
        if (manufacturerService.exists(manufacturer)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Manufacturer already Exists");
        }
        Manufacturer manufacturerSaved = manufacturerService.saveManufacturer(manufacturer);
        return ResponseEntity.status(HttpStatus.CREATED).body(manufacturerModelAssembler.toModel(manufacturerSaved));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManufacturerById(@PathVariable("id") Long id) {
        manufacturerService.deleteManufacturerById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Manufacturer>> updateManufacturer(@PathVariable("id") Long id, @RequestBody Manufacturer manufacturer) {
        Manufacturer actualManufacturer = manufacturerService.findManufacturerById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Manufacturer not found"));
        if (isNotBlank(manufacturer.getName())) {
            actualManufacturer.setName(manufacturer.getName());
        }
        if (isNotBlank(manufacturer.getNationality())) {
            actualManufacturer.setNationality(manufacturer.getNationality());
        }
        manufacturerService.updateManufacturer(actualManufacturer);
        return ResponseEntity.ok(manufacturerModelAssembler.toModel(actualManufacturer));
    }

}
