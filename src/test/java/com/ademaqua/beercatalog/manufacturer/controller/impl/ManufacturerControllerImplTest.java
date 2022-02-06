package com.ademaqua.beercatalog.manufacturer.controller.impl;

import com.ademaqua.beercatalog.manufacturer.controller.ManufacturerController;
import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;
import com.ademaqua.beercatalog.manufacturer.entity.ManufacturerModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ManufacturerControllerImplTest {

    @Autowired
    private ManufacturerController controller;

    private List<ManufacturerModel> elementsSaved;

    @BeforeEach
    public void setUp() {
        // given
        saveManufacturersInDB(50);
    }

    @AfterEach
    public void cleanUp() {
        deleteManufacturersInDB();
    }

    @Test
    public void shouldSaveNewManufacturer() {
        // given
        Manufacturer manufacturer = new Manufacturer(null, "NameSaved", "Nationality");

        // when
        ManufacturerModel savedManufacturer = controller.addManufacturer(manufacturer).getBody();

        // then
        assertAll("map manufacturer",
                () -> assertNotNull(savedManufacturer),
                () -> assertEquals(manufacturer.getName(), savedManufacturer.getName()),
                () -> assertEquals(manufacturer.getNationality(), savedManufacturer.getNationality()));
    }

    @Test
    public void shouldLoadAllManufacturersWithPageable() {
        // when
        PagedModel<ManufacturerModel> retrievedManufacturers = controller.getAll(0, 10, "asc").getBody();

        // then
        assertAll("pagination data",
                () -> assertNotNull(retrievedManufacturers),
                () -> assertEquals(10, retrievedManufacturers.getMetadata().getSize()),
                () -> assertEquals(5, retrievedManufacturers.getMetadata().getTotalPages()),
                () -> assertTrue(retrievedManufacturers.getLinks().hasSize(4)));
    }

    @Test
    public void shouldReturnManufacturerWhenIdFound() {
        // given
        Manufacturer manufacturer = new Manufacturer(null, "Name", "Nationality");
        ManufacturerModel savedManufacturer = controller.addManufacturer(manufacturer).getBody();

        // when
        assert savedManufacturer != null;
        ManufacturerModel expectedManufacturer = controller.getById(savedManufacturer.getId()).getBody();

        // then
        assertAll("map manufacturer",
                () -> assertNotNull(expectedManufacturer),
                () -> assertEquals(savedManufacturer.getName(), expectedManufacturer.getName()),
                () -> assertEquals(savedManufacturer.getNationality(), expectedManufacturer.getNationality()));
    }

    @Test
    public void shouldLaunchExceptionWhenConflictWhileSaving() {
        Manufacturer manufacturer = new Manufacturer(null, "Name1", "Nationality");

        assertThrows(ResponseStatusException.class, () -> controller.addManufacturer(manufacturer));
    }

    @Test
    public void shouldLaunchExceptionWhenManufacturerNotFound() {
        Manufacturer manufacturer = new Manufacturer(500L, "Name100", "Nationality");

        assertThrows(ResponseStatusException.class, () -> controller.getById(manufacturer.getId()));
    }

    @Test
    public void shouldReturnErrorWhenManufacturerNotWellSended() {
        Manufacturer manufacturer = new Manufacturer();

        assertThrows(ResponseStatusException.class, () -> controller.addManufacturer(manufacturer));
    }

    @Test
    public void shouldReturnErrorWhenManufacturerNotPartiallyWellSended() {
        Manufacturer manufacturer = new Manufacturer(null, "Name", null);

        assertThrows(ResponseStatusException.class, () -> controller.addManufacturer(manufacturer));
    }

    @Test
    public void shouldThrowAnErrorWhenTryingToUpdateNotExistentManufacturer() {
        // then
        assertThrows(ResponseStatusException.class, () -> controller.updateManufacturer(500L, createManufacturer("NAME")));
    }

    @Test
    public void shouldUpdateNameWhenIsAvailable() {
        // given
        Manufacturer manufacturer = createManufacturer("NEW_NAME");
        // when
        ResponseEntity<ManufacturerModel> actualValue = controller.updateManufacturer(elementsSaved.get(0).getId(), manufacturer);

        // then

        assertEquals(HttpStatus.OK, actualValue.getStatusCode());
        assertEquals(manufacturer.getName(), Objects.requireNonNull(actualValue.getBody()).getName());
    }

    @Test
    public void shouldUpdateNationalityWhenIsAvailable() {
        // given
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setNationality("NEW NATIONALITY");

        // when
        ResponseEntity<ManufacturerModel> actualValue = controller.updateManufacturer(elementsSaved.get(0).getId(), manufacturer);

        // then

        assertEquals(HttpStatus.OK, actualValue.getStatusCode());
        assertEquals(manufacturer.getNationality(), Objects.requireNonNull(actualValue.getBody()).getNationality());
    }

    private void saveManufacturersInDB(int quantity) {
        elementsSaved = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            elementsSaved.add(controller.addManufacturer(createManufacturer("Name" + i)).getBody());
        }
    }

    private Manufacturer createManufacturer(String name) {
        return new Manufacturer(null, name, "Nationality");
    }

    private void deleteManufacturersInDB() {
        controller.getAll(0, 100, "desc").getBody().getContent().forEach(manufacturerModel -> controller.deleteManufacturerById(manufacturerModel.getId()));
    }
}