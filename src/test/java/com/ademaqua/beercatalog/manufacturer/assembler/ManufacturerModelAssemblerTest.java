package com.ademaqua.beercatalog.manufacturer.assembler;

import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;
import com.ademaqua.beercatalog.manufacturer.entity.ManufacturerModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ManufacturerModelAssemblerTest {

    @InjectMocks
    private ManufacturerModelAssembler assembler;

    @Test
    public void shouldGenerateLinksWithGivenManufacturer() {
        // given
        Manufacturer manufacturer = getManufacturer();

        // when
        ManufacturerModel actualResponse = assembler.toModel(manufacturer);

        // then
        assertAll("mapping values",
                () -> assertEquals(manufacturer.getNationality(), actualResponse.getNationality()),
                () -> assertEquals(manufacturer.getName(), actualResponse.getName()));
        assertTrue(actualResponse.getLinks().hasSize(3));
    }


    private Manufacturer getManufacturer() {
        return new Manufacturer(null, "Name", "Nationality");
    }
}