package com.ademaqua.beercatalog.beer.controller;

import com.ademaqua.beercatalog.beer.entity.Beer;
import com.ademaqua.beercatalog.beer.entity.dto.BeerDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

public interface BeerController {

    ResponseEntity<CollectionModel<EntityModel<Beer>>> getAll();

    ResponseEntity<EntityModel<Beer>> getById(Long id);

    ResponseEntity<EntityModel<Beer>> addBeer(BeerDto beer);

    ResponseEntity<Void> deleteBeerById(Long id);

    ResponseEntity<EntityModel<Beer>> updateBeer(Long id, Beer beer);

    ResponseEntity<CollectionModel<EntityModel<Beer>>> getByManufacturer(Long id);
}
