package com.ademaqua.beercatalog.beer.validator;

import com.ademaqua.beercatalog.beer.entity.dto.BeerDto;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;
import static org.apache.logging.log4j.util.Strings.isBlank;

@Component
public class BeerValidator {

    public boolean validateBeer(BeerDto beer) {
        return !isBlank(beer.getName()) && !isBlank(beer.getType()) && !isBlank(beer.getDescription())
                && !isNull(beer.getManufacturerId()) && !isNull(beer.getGraduation());
    }
}
