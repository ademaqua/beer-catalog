package com.ademaqua.beercatalog.manufacturer.controller.impl;

import com.ademaqua.beercatalog.manufacturer.assembler.ManufacturerModelAssembler;
import com.ademaqua.beercatalog.manufacturer.controller.ManufacturerController;
import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;
import com.ademaqua.beercatalog.manufacturer.entity.ManufacturerModel;
import com.ademaqua.beercatalog.manufacturer.service.ManufacturerService;
import com.ademaqua.beercatalog.manufacturer.validator.ManufacturerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Autowired
    private PagedResourcesAssembler<Manufacturer> pagedResourcesAssembler;

    @Override
    @GetMapping
    public ResponseEntity<PagedModel<ManufacturerModel>> getAll(@RequestParam(defaultValue = "0") int pageNumber,
                                                                        @RequestParam(defaultValue = "25") int pageSize,
                                                                        @RequestParam(defaultValue = "asc") String order) {
        Sort sort = Sort.by("id");
        if (order.equals("asc")) {
            sort.ascending();
        } else {
            sort.descending();
        }
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        Page<Manufacturer> manufacturerPage = manufacturerService.findAllManufacturersPaginated(pageRequest);

        PagedModel<ManufacturerModel> collectionModel = pagedResourcesAssembler.toModel(manufacturerPage, manufacturerModelAssembler);
        return ResponseEntity.ok(collectionModel);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ManufacturerModel> getById(@PathVariable("id") Long id) {
        Optional<Manufacturer> manufacturer = manufacturerService.findManufacturerById(id);
        if (manufacturer.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Manufacturer not found!");
        }
        return ResponseEntity.ok(manufacturerModelAssembler.toModel(manufacturer.get()));
    }

    @Override
    @PostMapping
    public ResponseEntity<ManufacturerModel> addManufacturer(@RequestBody Manufacturer manufacturer) {
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
    public ResponseEntity<ManufacturerModel> updateManufacturer(@PathVariable("id") Long id, @RequestBody Manufacturer manufacturer) {
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
