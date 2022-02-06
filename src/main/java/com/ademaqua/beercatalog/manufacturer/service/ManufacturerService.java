package com.ademaqua.beercatalog.manufacturer.service;

import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ManufacturerService {

    Page<Manufacturer> findAllManufacturersPaginated(Pageable pageable);

    Optional<Manufacturer> findManufacturerById(Long id);

    boolean exists(Manufacturer manufacturer);

    Manufacturer saveManufacturer(Manufacturer manufacturer);

    void deleteManufacturerById(Long id);

    void updateManufacturer(Manufacturer actualManufacturer);
}
