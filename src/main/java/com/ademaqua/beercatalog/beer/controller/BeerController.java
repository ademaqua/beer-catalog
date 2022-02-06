package com.ademaqua.beercatalog.beer.controller;

import com.ademaqua.beercatalog.beer.entity.Beer;
import com.ademaqua.beercatalog.beer.entity.BeerDto;
import com.ademaqua.beercatalog.beer.entity.BeerModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface BeerController {

    ResponseEntity<PagedModel<BeerModel>> getAll(@RequestParam(defaultValue = "0") int pageNumber,
                                                 @RequestParam(defaultValue = "25") int pageSize,
                                                 @RequestParam(defaultValue = "asc") String order);

    ResponseEntity<BeerModel> getById(Long id);

    ResponseEntity<BeerModel> addBeer(BeerDto beer);

    ResponseEntity<Void> deleteBeerById(Long id);

    ResponseEntity<BeerModel> updateBeer(Long id, Beer beer);

    ResponseEntity<PagedModel<BeerModel>> getByManufacturer(@PathVariable("id") Long id,
                                                            @RequestParam(defaultValue = "0") int pageNumber,
                                                            @RequestParam(defaultValue = "25") int pageSize,
                                                            @RequestParam(defaultValue = "asc") String order);
}
