package com.ademaqua.beercatalog.manufacturer.entity;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ManufacturerModelTest {

    @Test
    public void shouldReturnTrueWhenEqualsNotConsideringId() {
        // given
        ManufacturerModel manufacturerA = createManufacturer();
        ManufacturerModel manufacturerB = createManufacturer();

        // then
        assertEquals(manufacturerA, manufacturerB);
    }

    @Test
    public void shouldReturnFalseWhenNotEquals() {
        // given
        ManufacturerModel manufacturerA = createManufacturer();
        manufacturerA.setName("NEW_NAME");
        ManufacturerModel manufacturerB = createManufacturer();

        // then
        assertNotEquals(manufacturerA, manufacturerB);
    }

    @Test
    public void shouldEqualsWhenHashing() {
        ManufacturerModel manufacturerA = createManufacturer();
        ManufacturerModel manufacturerB = createManufacturer();

        assertEquals(manufacturerA.hashCode(), manufacturerB.hashCode());
    }

    private ManufacturerModel createManufacturer() {
        return ManufacturerModel.builder().name("Name").nationality("Nationality").build();
    }}