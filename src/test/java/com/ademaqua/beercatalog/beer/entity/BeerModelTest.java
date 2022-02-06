package com.ademaqua.beercatalog.beer.entity;

import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BeerModelTest {

    @Test
    public void shouldReturnTrueWhenEqualsNotConsideringId() {
        // given
        BeerModel beerA = createBeer();
        beerA.setId(1L);
        BeerModel beerB = createBeer();
        beerB.setId(1L);

        // then
        assertEquals(beerA, beerB);
    }

    @Test
    public void shouldReturnFalseWhenNotEquals() {
        // given
        BeerModel beerA = createBeer();
        beerA.setName("NEW_NAME");
        BeerModel beerB = createBeer();

        // then
        assertNotEquals(beerA, beerB);
    }

    @Test
    public void shouldEqualsWhenHashing() {
        BeerModel beerA = BeerModel.builder().id(1L).name("Name")
                .description("Description").type("Type")
                .graduation(0.0).manufacturer(createManufacturer("Name")).build();
        BeerModel beerB = new BeerModel(1L, "Name", 0.0, "Type", "Description", createManufacturer("Name"));

        assertEquals(beerA.hashCode(), beerB.hashCode());
    }

    private BeerModel createBeer() {
        BeerModel beer = new BeerModel();
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