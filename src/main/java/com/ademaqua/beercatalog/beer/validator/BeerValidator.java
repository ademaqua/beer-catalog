package com.ademaqua.beercatalog.beer.validator;

import com.ademaqua.beercatalog.beer.entity.BeerDto;
import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;
import com.ademaqua.beercatalog.manufacturer.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Objects.isNull;
import static org.apache.logging.log4j.util.Strings.isBlank;

@Component
public class BeerValidator {

    @Autowired
    private ManufacturerService manufacturerService;

    public boolean validateBeer(BeerDto beer) {
        Optional<Manufacturer> manufacturer = manufacturerService.findManufacturerById(beer.getManufacturerId());
        return !isBlank(beer.getName()) && !isBlank(beer.getType()) && !isBlank(beer.getDescription())
                && !isNull(beer.getManufacturerId()) && !isNull(beer.getGraduation()) && manufacturer.isPresent();
    }
}
