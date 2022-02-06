package com.ademaqua.beercatalog.beer.controller.impl;

import com.ademaqua.beercatalog.beer.controller.BeerController;
import com.ademaqua.beercatalog.beer.entity.Beer;
import com.ademaqua.beercatalog.beer.entity.BeerDto;
import com.ademaqua.beercatalog.beer.entity.BeerModel;
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
class BeerControllerImplTest {

    @Autowired
    private BeerController beerController;

    @Autowired
    private ManufacturerController manufacturerController;

    private List<BeerModel> savedBeers;
    private List<ManufacturerModel> savedManufacturers;

    @BeforeEach
    public void setUp() {
        // given
        saveManufacturersInDB(50);
        saveBeersInDB(250);
    }

    @AfterEach
    public void cleanUp() {
        deleteBeersInDB();
        deleteManufacturersInDB();
    }

    @Test
    public void shouldLoadAllManufacturersWithPageable() {
        // when
        PagedModel<BeerModel> retrievedManufacturers = beerController.getAll(0, 25, "asc").getBody();

        // then
        assertAll("pagination data",
                () -> assertNotNull(retrievedManufacturers),
                () -> assertEquals(25, retrievedManufacturers.getMetadata().getSize()),
                () -> assertEquals(10, retrievedManufacturers.getMetadata().getTotalPages()),
                () -> assertTrue(retrievedManufacturers.getLinks().hasSize(4)));
    }
    @Test
    public void shouldSaveNewManufacturer() {
        // given
        ManufacturerModel manufacturerModel = savedManufacturers.get(0);
        BeerDto beer = new BeerDto();
        beer.setName("Name");
        beer.setGraduation(4.2);
        beer.setType("Type");
        beer.setDescription("Description");
        beer.setManufacturerId(manufacturerModel.getId());

        // when
        BeerModel savedBeer = beerController.addBeer(beer).getBody();

        // then
        assertAll("map manufacturer",
                () -> assertNotNull(savedBeer),
                () -> assertEquals(beer.getName(), savedBeer.getName()),
                () -> assertEquals(beer.getGraduation(), savedBeer.getGraduation()),
                () -> assertEquals(beer.getManufacturerId(), savedBeer.getManufacturer().getId()),
                () -> assertEquals(beer.getType(), savedBeer.getType()));
    }

    @Test
    public void shouldReturnBeerWhenIdFound() {
        // when
        BeerModel expectedBeer = beerController.getById(savedBeers.get((int) (Math.random() * savedBeers.size())).getId()).getBody();

        // then
        assertAll("map manufacturer",
                () -> assertNotNull(expectedBeer));
    }

    @Test
    public void shouldLaunchExceptionWhenConflictWhileSaving() {
        BeerModel beerModel = beerController.getById(savedBeers.get((int) (Math.random() * savedBeers.size())).getId()).getBody();

        BeerDto beer = new BeerDto();
        beer.setName(beerModel.getName());
        beer.setType(beerModel.getType());
        beer.setManufacturerId(beerModel.getManufacturer().getId());

        assertThrows(ResponseStatusException.class, () -> beerController.addBeer(beer));
    }

    @Test
    public void shouldLaunchExceptionWhenManufacturerNotFound() {
        Beer beer = new Beer();
        beer.setId(2000L);

        assertThrows(ResponseStatusException.class, () -> beerController.getById(beer.getId()));
    }

    private void saveBeersInDB(int quantity) {
        savedBeers = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            savedBeers.add(beerController.addBeer(BeerDto.builder()
                    .name("Name" + i).type("Type")
                    .manufacturerId(savedManufacturers.get((int) (Math.random() * savedManufacturers.size())).getId())
                    .graduation(Math.random() * 10).description("Description")
                    .build()).getBody());
        }
    }

    private void saveManufacturersInDB(int quantity) {
        savedManufacturers = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            savedManufacturers.add(manufacturerController.addManufacturer(createManufacturer("Name" + i)).getBody());
        }
    }

    private Manufacturer createManufacturer(String name) {
        return new Manufacturer(null, name, "Nationality");
    }

    private void deleteManufacturersInDB() {
        manufacturerController.getAll(0, 100, "desc").getBody().getContent().forEach(manufacturerModel -> manufacturerController.deleteManufacturerById(manufacturerModel.getId()));
    }

    private void deleteBeersInDB() {
        beerController.getAll(0,500, "desc").getBody().getContent().forEach(beerModel -> beerController.deleteBeerById(beerModel.getId()));
    }
}