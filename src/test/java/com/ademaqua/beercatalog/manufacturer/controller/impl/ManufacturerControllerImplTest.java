package com.ademaqua.beercatalog.manufacturer.controller.impl;

import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;
import com.ademaqua.beercatalog.manufacturer.assembler.ManufacturerModelAssembler;
import com.ademaqua.beercatalog.manufacturer.service.ManufacturerService;
import com.ademaqua.beercatalog.manufacturer.validator.ManufacturerValidator;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ManufacturerControllerImplTest {

    @InjectMocks
    private ManufacturerControllerImpl controller;

    @Mock
    private ManufacturerValidator validator;

    @Mock
    private ManufacturerService service;

    @Mock
    private ManufacturerModelAssembler assembler;

    @Test
    public void shouldGetAllManufacturers() {
        // given
        List<Manufacturer> manufacturerList = List.of(createManufacturer());

        when(service.findAllManufacturers()).thenReturn(manufacturerList);

        // when
        ResponseEntity<CollectionModel<EntityModel<Manufacturer>>> actualValue = controller.getAll();

        // then
        verify(assembler, times(1)).toCollectionModel(manufacturerList);
    }

    @Test
    public void shouldReturnManufacturer() {
        // given
        Manufacturer manufacturer = createManufacturer();
        when(service.findManufacturerById(anyLong())).thenReturn(Optional.of(manufacturer));

        // when
        ResponseEntity<EntityModel<Manufacturer>> actualValue = controller.getById(1L);

        // then
        verify(assembler, times(1)).toModel(manufacturer);
        assertEquals(HttpStatus.OK, actualValue.getStatusCode());
    }

    @Test
    public void shouldLaunchExceptionWhenManufacturerNotFound() {
        // given
        when(service.findManufacturerById(anyLong())).thenReturn(Optional.empty());

        // then
        assertThrows(ResponseStatusException.class, () -> controller.getById(1L));
    }

    @Test
    public void shouldThrowConflictWhenManufacturerAlreadyExists() {
        // given
        when(validator.validate(any(Manufacturer.class))).thenReturn(false);

        // then
        assertThrows(ResponseStatusException.class, () -> controller.addManufacturer(Manufacturer.builder().build()));
    }

    @Test
    public void shouldThrownBadRequestWhenNotCorrectlyFormattedDto() {
        // given
        when(validator.validate(any(Manufacturer.class))).thenReturn(true);
        when(service.exists(any(Manufacturer.class))).thenReturn(true);

        // then
        assertThrows(ResponseStatusException.class, () -> controller.addManufacturer(Manufacturer.builder().build()));
    }

    @Test
    public void shouldAddNewManufacturer() {
        // given
        when(service.exists(any(Manufacturer.class))).thenReturn(false);
        when(validator.validate(any(Manufacturer.class))).thenReturn(true);
        when(service.saveManufacturer(any(Manufacturer.class))).thenReturn(Manufacturer.builder().build());

        // when
        ResponseEntity<EntityModel<Manufacturer>> actualValue = controller.addManufacturer(Manufacturer.builder().build());

        // then
        assertEquals(HttpStatus.CREATED, actualValue.getStatusCode());
        verify(assembler, times(1)).toModel(any(Manufacturer.class));
    }

    @Test
    public void shouldDeleteManufacturer() {
        // when
        ResponseEntity<Void> actualValue = controller.deleteManufacturerById(1L);

        // then
        assertEquals(HttpStatus.NO_CONTENT, actualValue.getStatusCode());
    }

    @Test
    public void shouldThrowAnErrorWhenTryingToUpdateNotExistentManufacturer() {
        // given
        when(service.findManufacturerById(anyLong())).thenReturn(Optional.empty());

        // then
        assertThrows(ResponseStatusException.class, () -> controller.updateManufacturer(1L, createManufacturer()));
    }

    @Test
    public void shouldUpdateNameWhenIsAvailable() {
        // given
        Manufacturer manufacturer = createManufacturer();
        manufacturer.setName("NEW_NAME");
        Manufacturer inMemoryManufacturer = createManufacturer();
        when(service.findManufacturerById(anyLong())).thenReturn(Optional.of(inMemoryManufacturer));
        when(assembler.toModel(any())).thenCallRealMethod();

        // when
        ResponseEntity<EntityModel<Manufacturer>> actualValue = controller.updateManufacturer(1L, manufacturer);

        // then
        verify(service, times(1)).updateManufacturer(any());
        assertEquals(HttpStatus.OK, actualValue.getStatusCode());
        assertEquals(manufacturer.getName(), Objects.requireNonNull(Objects.requireNonNull(actualValue.getBody()).getContent()).getName());
    }

    @Test
    public void shouldUpdateNationalityWhenIsAvailable() {
        // given
        Manufacturer manufacturer = createManufacturer();
        manufacturer.setNationality("NEW NATIONALITY");
        Manufacturer inMemoryManufacturer = createManufacturer();
        when(service.findManufacturerById(anyLong())).thenReturn(Optional.of(inMemoryManufacturer));
        when(assembler.toModel(any())).thenCallRealMethod();

        // when
        ResponseEntity<EntityModel<Manufacturer>> actualValue = controller.updateManufacturer(1L, manufacturer);

        // then
        verify(service, times(1)).updateManufacturer(any());
        assertEquals(HttpStatus.OK, actualValue.getStatusCode());
        assertEquals(manufacturer.getNationality(), Objects.requireNonNull(Objects.requireNonNull(actualValue.getBody()).getContent()).getNationality());
    }

    private Manufacturer createManufacturer() {
        return Manufacturer.builder().name("MANUFACTURER").nationality("COUNTRY").build();
    }
}