package com.ademaqua.beercatalog.beer.service;

import com.ademaqua.beercatalog.beer.entity.Beer;
import com.ademaqua.beercatalog.beer.entity.BeerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BeerService {

    Optional<Beer> findById(Long id);

    Page<Beer> findAllBeers(Pageable pageable);

    boolean beerExists(BeerDto beer);

    Optional<Beer> findByName(String name);

    Beer saveBeer(BeerDto beer);

    void deleteBeerById(Long id);

    void updateBeer(Beer actualBeer);

    Page<Beer> findBeersByManufacturerIdAndPageable(Long id, Pageable pageRequest);
}
