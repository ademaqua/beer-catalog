package com.ademaqua.beercatalog.manufacturer.service.impl;

import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;
import com.ademaqua.beercatalog.manufacturer.repository.ManufacturerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ManufacturerServiceImplTest {
    
    @InjectMocks
    private ManufacturerServiceImpl service;
    
    @Mock
    private ManufacturerRepository repository;

    @Test
    public void shouldCallFindByIdInRepository() {
        // when
        service.findManufacturerById(1L);

        // then
        verify(repository, times(1)).findManufacturerById(1L);
    }

    @Test
    public void shouldCallFindAllBeersInRepository() {
        // when
        service.findAllManufacturers();

        // then
        verify(repository, times(1)).findAllManufacturers();
    }

    @Test
    public void shouldCallExistBeer() {
        // when
        service.exists(createManufacturer());

        // then
        verify(repository, times(1)).existsManufacturer(any(Manufacturer.class));
    }

    @Test
    public void shouldCallMapAndSaveMethods() {
        // when
        service.saveManufacturer(createManufacturer());

        // then
        verify(repository, times(1)).save(any());
    }

    @Test
    public void shouldCallDeleteMethod() {
        // when
        service.deleteManufacturerById(1L);

        // then
        verify(repository, times(1)).deleteManufacturerById(1L);
    }

    @Test
    public void shouldCallUpdateMethod() {
        // when
        service.updateManufacturer(createManufacturer());

        // then
        verify(repository, times(1)).updateManufacturer(any());
    }

    private Manufacturer createManufacturer() {
        return Manufacturer.builder().build();
    }
}