package com.ademaqua.beercatalog.beer.service;

import com.ademaqua.beercatalog.beer.entity.Beer;
import com.ademaqua.beercatalog.beer.entity.dto.BeerDto;

import java.util.List;
import java.util.Optional;

public interface BeerService {

    Optional<Beer> findById(Long id);

    List<Beer> findAllBeers();

    boolean beerExists(BeerDto beer);

    Optional<Beer> findByName(String name);

    Beer saveBeer(BeerDto beer);

    void deleteBeerById(Long id);

    List<Beer> findBeersByManufacturerId(Long id);

    void updateBeer(Beer actualBeer);
}
