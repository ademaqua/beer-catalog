package com.ademaqua.beercatalog.manufacturer.repository;

import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.isNull;

@Repository
public class ManufacturerRepository {

    private final Map<Long, Manufacturer> manufacturerMap;
    private Long id;

    public ManufacturerRepository() {
        this.manufacturerMap = new HashMap<>();
        this.id = 0L;
    }

    public Manufacturer save(Manufacturer manufacturer) {
        this.id = this.id + 1;
        manufacturer.setId(this.id);
        manufacturerMap.put(manufacturer.getId(), manufacturer);
        return manufacturer;
    }

    public Optional<Manufacturer> findManufacturerById(Long id) {
        if (isNull(manufacturerMap.get(id))) {
            return Optional.empty();
        }
        return Optional.of(manufacturerMap.get(id));
    }

    public List<Manufacturer> findAllManufacturers() {
        return List.copyOf(manufacturerMap.values());
    }

    public boolean existsManufacturer(Manufacturer manufacturer) {
        Optional<Manufacturer> currentManufacturer = findManufacturer(manufacturer);
        return currentManufacturer.isPresent();
    }

    public void deleteManufacturerById(Long id) {
        manufacturerMap.remove(id);
    }

    public void updateManufacturer(Manufacturer actualManufacturer) {
        manufacturerMap.put(actualManufacturer.getId(), actualManufacturer);
    }

    private Optional<Manufacturer> findManufacturer(Manufacturer manufacturer) {
        return manufacturerMap.values().stream()
                .filter(manufacturerToFilter -> manufacturerToFilter.equals(manufacturer)).findFirst();
    }
}
