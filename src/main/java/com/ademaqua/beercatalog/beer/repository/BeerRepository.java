package com.ademaqua.beercatalog.beer.repository;

import com.ademaqua.beercatalog.beer.entity.Beer;
import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BeerRepository extends JpaRepository<Beer, Long> {

    boolean existsBeerByNameAndTypeAndManufacturer(String name, String type, Manufacturer manufacturer);

    Page<Beer> findBeersByManufacturer(Manufacturer manufacturer, Pageable pageable);

    Optional<Beer> findBeerByName(String name);
}
