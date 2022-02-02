package com.ademaqua.beercatalog.beer.mapper;

import com.ademaqua.beercatalog.beer.entity.Beer;
import com.ademaqua.beercatalog.beer.entity.dto.BeerDto;
import com.ademaqua.beercatalog.manufacturer.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class BeerMapper {

    @Autowired
    private ManufacturerService manufacturerService;

    public Beer map(BeerDto beerDto) {
        return Beer.builder().name(beerDto.getName())
                .type(beerDto.getType())
                .description(beerDto.getDescription())
                .graduation(beerDto.getGraduation())
                .manufacturer(manufacturerService
                        .findManufacturerById(beerDto.getManufacturerId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Manufacturer does not exists")))
                .build();
    }
}
