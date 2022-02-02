package com.ademaqua.beercatalog.manufacturer.service;

import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;

import java.util.List;
import java.util.Optional;

public interface ManufacturerService {

    List<Manufacturer> findAllManufacturers();

    Optional<Manufacturer> findManufacturerById(Long id);

    boolean exists(Manufacturer manufacturer);

    Manufacturer saveManufacturer(Manufacturer manufacturer);

    void deleteManufacturerById(Long id);

    void updateManufacturer(Manufacturer actualManufacturer);
}
