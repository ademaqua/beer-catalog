package com.ademaqua.beercatalog.manufacturer.validator;

import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;
import org.springframework.stereotype.Component;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Component
public class ManufacturerValidator {

    public boolean validate(Manufacturer manufacturer) {
        return !isBlank(manufacturer.getName()) && !isBlank(manufacturer.getNationality());
    }
}
