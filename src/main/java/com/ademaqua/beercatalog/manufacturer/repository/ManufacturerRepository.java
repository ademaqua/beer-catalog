package com.ademaqua.beercatalog.manufacturer.repository;

import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    boolean existsManufacturerByNameAndNationality(String name, String nationality);
}
