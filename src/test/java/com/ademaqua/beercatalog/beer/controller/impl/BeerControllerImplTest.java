package com.ademaqua.beercatalog.beer.controller.impl;


import com.ademaqua.beercatalog.beer.assembler.BeerModelAssembler;
import com.ademaqua.beercatalog.beer.entity.Beer;
import com.ademaqua.beercatalog.beer.entity.dto.BeerDto;
import com.ademaqua.beercatalog.beer.service.BeerService;
import com.ademaqua.beercatalog.beer.validator.BeerValidator;
import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BeerControllerImplTest {

    @InjectMocks
    private BeerControllerImpl beerController;

    @Mock
    private BeerModelAssembler assembler;

    @Mock
    private BeerService beerService;

    @Mock
    private BeerValidator beerValidator;

    @Test
    public void shouldGetAllBeers() {
        // given
        List<Beer> beers = List.of(createBeer());

        when(beerService.findAllBeers()).thenReturn(beers);

        // when
        ResponseEntity<CollectionModel<EntityModel<Beer>>> actualValue = beerController.getAll();

        // then
        verify(assembler, times(1)).toCollectionModel(beers);
    }

    @Test
    public void shouldReturnBeer() {
        // given
        Beer beer = createBeer();
        when(beerService.findById(anyLong())).thenReturn(Optional.of(beer));

        // when
        ResponseEntity<EntityModel<Beer>> actualValue = beerController.getById(1L);

        // then
        verify(assembler, times(1)).toModel(beer);
        assertEquals(HttpStatus.OK, actualValue.getStatusCode());
    }

    @Test
    public void shouldLaunchExceptionWhenBeerNotFound() {
        // given
        when(beerService.findById(anyLong())).thenReturn(Optional.empty());

        // then
        assertThrows(ResponseStatusException.class, () -> beerController.getById(1L));
    }

    @Test
    public void shouldThrowConflictWhenBeerAlreadyExists() {
        // given
        when(beerValidator.validateBeer(any(BeerDto.class))).thenReturn(false);

        // then
        assertThrows(ResponseStatusException.class, () -> beerController.addBeer(new BeerDto()));
    }

    @Test
    public void shouldThrownBadRequestWhenNotCorrectlyFormattedDto() {
        // given
        when(beerValidator.validateBeer(any(BeerDto.class))).thenReturn(true);
        when(beerService.beerExists(any(BeerDto.class))).thenReturn(true);

        // then
        assertThrows(ResponseStatusException.class, () -> beerController.addBeer(new BeerDto()));
    }

    @Test
    public void shouldAddNewBeer() {
        // given
        when(beerService.beerExists(any(BeerDto.class))).thenReturn(false);
        when(beerValidator.validateBeer(any(BeerDto.class))).thenReturn(true);
        when(beerService.saveBeer(any(BeerDto.class))).thenReturn(Beer.builder().build());

        // when
        ResponseEntity<EntityModel<Beer>> actualValue = beerController.addBeer(new BeerDto());

        // then
        assertEquals(HttpStatus.CREATED, actualValue.getStatusCode());
        verify(assembler, times(1)).toModel(any(Beer.class));
    }

    @Test
    public void shouldDeleteBeer() {
        // when
        ResponseEntity<Void> actualValue = beerController.deleteBeerById(1L);

        // then
        assertEquals(HttpStatus.NO_CONTENT, actualValue.getStatusCode());
    }

    @Test
    public void shouldThrowAnErrorWhenTryingToUpdateNotExistentBeer() {
        // given
        when(beerService.findById(anyLong())).thenReturn(Optional.empty());

        // then
        assertThrows(ResponseStatusException.class, () -> beerController.updateBeer(1L, createBeer()));
    }

    @Test
    public void shouldUpdateNameWhenIsAvailable() {
        // given
        Beer beer = createBeer();
        beer.setName("NEW_NAME");
        Beer inMemoryBeer = createBeer();
        when(beerService.findById(anyLong())).thenReturn(Optional.of(inMemoryBeer));
        when(assembler.toModel(any())).thenCallRealMethod();

        // when
        ResponseEntity<EntityModel<Beer>> actualValue = beerController.updateBeer(1L, beer);

        // then
        verify(beerService, times(1)).updateBeer(any());
        assertEquals(HttpStatus.OK, actualValue.getStatusCode());
        assertEquals(beer.getName(), Objects.requireNonNull(Objects.requireNonNull(actualValue.getBody()).getContent()).getName());
    }

    @Test
    public void shouldUpdateDescriptionWhenIsAvailable() {
        // given
        Beer beer = createBeer();
        beer.setDescription("NEW_DESCRIPTION");
        Beer inMemoryBeer = createBeer();
        when(beerService.findById(anyLong())).thenReturn(Optional.of(inMemoryBeer));
        when(assembler.toModel(any())).thenCallRealMethod();

        // when
        ResponseEntity<EntityModel<Beer>> actualValue = beerController.updateBeer(1L, beer);

        // then
        verify(beerService, times(1)).updateBeer(any());
        assertEquals(HttpStatus.OK, actualValue.getStatusCode());
        assertEquals(beer.getDescription(), Objects.requireNonNull(Objects.requireNonNull(actualValue.getBody()).getContent()).getDescription());
    }

    @Test
    public void shouldUpdateTypeWhenIsAvailable() {
        // given
        Beer beer = createBeer();
        beer.setType("NEW_TYPE");
        Beer inMemoryBeer = createBeer();
        when(beerService.findById(anyLong())).thenReturn(Optional.of(inMemoryBeer));
        when(assembler.toModel(any())).thenCallRealMethod();

        // when
        ResponseEntity<EntityModel<Beer>> actualValue = beerController.updateBeer(1L, beer);

        // then
        verify(beerService, times(1)).updateBeer(any());
        assertEquals(HttpStatus.OK, actualValue.getStatusCode());
        assertEquals(beer.getType(), Objects.requireNonNull(Objects.requireNonNull(actualValue.getBody()).getContent()).getType());
    }

    @Test
    public void shouldUpdateGraduationWhenIsAvailable() {
        // given
        Beer beer = createBeer();
        beer.setGraduation(4.0);
        Beer inMemoryBeer = createBeer();
        when(beerService.findById(anyLong())).thenReturn(Optional.of(inMemoryBeer));
        when(assembler.toModel(any())).thenCallRealMethod();

        // when
        ResponseEntity<EntityModel<Beer>> actualValue = beerController.updateBeer(1L, beer);

        // then
        verify(beerService, times(1)).updateBeer(any());
        assertEquals(HttpStatus.OK, actualValue.getStatusCode());
        assertEquals(beer.getGraduation(), Objects.requireNonNull(Objects.requireNonNull(actualValue.getBody()).getContent()).getGraduation());
    }

    @Test
    public void shouldUpdateManufacturerWhenIsAvailable() {
        // given
        Beer beer = createBeer();
        beer.setManufacturer(Manufacturer.builder().name("NEW_MANUFACTURER").build());
        Beer inMemoryBeer = createBeer();
        when(beerService.findById(anyLong())).thenReturn(Optional.of(inMemoryBeer));
        when(assembler.toModel(any())).thenCallRealMethod();

        // when
        ResponseEntity<EntityModel<Beer>> actualValue = beerController.updateBeer(1L, beer);

        // then
        verify(beerService, times(1)).updateBeer(any());
        assertEquals(HttpStatus.OK, actualValue.getStatusCode());
        assertEquals(beer.getDescription(), Objects.requireNonNull(Objects.requireNonNull(actualValue.getBody()).getContent()).getDescription());
    }

    @Test
    public void shouldRetrieveAllBeersPerOneManufacturer() {
        // when
        beerController.getByManufacturer(1L);

        // then
        verify(assembler, times(1)).toCollectionModel(any());
        verify(beerService, times(1)).findBeersByManufacturerId(1L);
    }

    private Beer createBeer() {
        return Beer.builder()
                .name("NAME").type("TYPE").graduation(0.0)
                .description("DESCRIPTION").manufacturer(
                        Manufacturer.builder().name("MANUFACTURER").nationality("COUNTRY").build())
                .build();
    }
}