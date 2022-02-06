package com.ademaqua.beercatalog.beer.assembler;

import com.ademaqua.beercatalog.beer.entity.Beer;
import com.ademaqua.beercatalog.beer.entity.BeerModel;
import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class BeerModelAssemblerTest {

    @InjectMocks
    private BeerModelAssembler assembler;

    @Test
    public void shouldGenerateLinksWithGivenBeer() {
        // given
        Beer beer = getBeer();

        // when
        BeerModel actualResponse = assembler.toModel(beer);

        // then
        assertAll("beer mapping",
                () -> assertEquals(beer.getName(), actualResponse.getName()),
                () -> assertEquals(beer.getDescription(), actualResponse.getDescription()),
                () -> assertEquals(beer.getGraduation(), actualResponse.getGraduation()),
                () -> assertEquals(beer.getType(), actualResponse.getType()),
                () -> assertEquals(beer.getManufacturer(), actualResponse.getManufacturer()));
        assertTrue(actualResponse.getLinks().hasSize(3));
    }

    private Beer getBeer() {
        Beer beer = new Beer();
        beer.setName("Name");
        beer.setGraduation(0.0);
        beer.setType("Type");
        beer.setDescription("Description");
        beer.setManufacturer(createManufacturer("Manufacturer"));
        return beer;
    }

    private Manufacturer createManufacturer(String manufacturer) {
        return new Manufacturer(null, manufacturer, "Nationality");
    }
}