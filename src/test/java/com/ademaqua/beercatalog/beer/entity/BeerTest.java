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
        return Beer.builder()
                .name("NAME").type("TYPE").graduation(0.0)
                .description("DESCRIPTION").manufacturer(
                        Manufacturer.builder().id(1L).name("MANUFACTURER").nationality("COUNTRY").build())
                .build();
    }
}