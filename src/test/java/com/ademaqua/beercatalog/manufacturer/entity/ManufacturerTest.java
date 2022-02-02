package com.ademaqua.beercatalog.manufacturer.entity;

import com.ademaqua.beercatalog.beer.entity.Beer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


class ManufacturerTest {

    @Test
    public void shouldReturnTrueWhenEqualsNotConsideringId() {
        // given
        Manufacturer manufacturerA = createManufacturer();
        manufacturerA.setId(1L);
        Manufacturer manufacturerB = createManufacturer();
        manufacturerB.setId(2L);

        // then
        assertEquals(manufacturerA, manufacturerB);
    }

    @Test
    public void shouldReturnFalseWhenNotEquals() {
        // given
        Manufacturer manufacturerA = createManufacturer();
        manufacturerA.setName("NEW_NAME");
        Manufacturer manufacturerB = createManufacturer();

        // then
        assertNotEquals(manufacturerA, manufacturerB);
    }

    @Test
    public void shouldEqualsWhenHashing() {
        Manufacturer manufacturerA = createManufacturer();
        manufacturerA.setId(1L);
        Manufacturer manufacturerB = createManufacturer();
        manufacturerB.setId(2L);

        assertEquals(manufacturerA.hashCode(), manufacturerB.hashCode());
    }

    private Manufacturer createManufacturer() {
        return Manufacturer.builder().name("NAME").nationality("NATIONALITY").build();
    }

}