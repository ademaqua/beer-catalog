package com.ademaqua.beercatalog.beer.entity;


import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class BeerTest {

    @Test
    public void shouldReturnTrueWhenEqualsNotConsideringId() {
        // given
        Beer beerA = createBeer();
        beerA.setId(1L);
        Beer beerB = createBeer();
        beerB.setId(2L);

        // then
        assertEquals(beerA, beerB);
    }

    @Test
    public void shouldReturnFalseWhenNotEquals() {
        // given
        Beer beerA = createBeer();
        beerA.setName("NEW_NAME");
        Beer beerB = createBeer();

        // then
        assertNotEquals(beerA, beerB);
    }

    @Test
    public void shouldEqualsWhenHashing() {
        Beer beerA = createBeer();
        beerA.setId(1L);
        Beer beerB = createBeer();
        beerB.setId(2L);

        assertEquals(beerA.hashCode(), beerB.hashCode());
    }

    private Beer createBeer() {
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