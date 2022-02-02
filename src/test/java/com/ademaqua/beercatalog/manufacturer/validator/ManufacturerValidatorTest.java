package com.ademaqua.beercatalog.manufacturer.validator;

import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ManufacturerValidatorTest {

    @InjectMocks
    private ManufacturerValidator validator;

    @Test
    public void shouldReturnFalseWhenNoManufactureInfo() {
        // given
        Manufacturer manufacturer = Manufacturer.builder().build();

        // then
        assertFalse(validator.validate(manufacturer));
    }

    @Test
    public void shouldReturnFalseWhenOnlyNameInfo() {
        // given
        Manufacturer manufacturer = Manufacturer.builder().name("NAME").build();

        // then
        assertFalse(validator.validate(manufacturer));
    }

    @Test
    public void shouldReturnFalseWhenAllInfo() {
        // given
        Manufacturer manufacturer = Manufacturer.builder().name("NAME").nationality("COUNTRY").build();

        // then
        assertTrue(validator.validate(manufacturer));
    }
}