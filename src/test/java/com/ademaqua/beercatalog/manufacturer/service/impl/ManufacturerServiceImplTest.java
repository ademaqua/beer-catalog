package com.ademaqua.beercatalog.manufacturer.service.impl;

import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;
import com.ademaqua.beercatalog.manufacturer.repository.ManufacturerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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
        verify(repository, times(1)).findById(1L);
    }

    @Test
    public void shouldCallFindAllBeersInRepository() {
        // when
        Pageable pageable = PageRequest.of(0,1);
        service.findAllManufacturersPaginated(pageable);

        // then
        verify(repository, times(1)).findAll(pageable);
    }

    @Test
    public void shouldCallExistBeer() {
        // when
        service.exists(createManufacturer());

        // then
        verify(repository, times(1)).existsManufacturerByNameAndNationality(anyString(), anyString());
    }

    @Test
    public void shouldCallMapAndSaveMethods() {
        // when
        service.saveManufacturer(createManufacturer());

        // then
        verify(repository, times(1)).saveAndFlush(any());
    }

    @Test
    public void shouldCallDeleteMethod() {
        // when
        service.deleteManufacturerById(1L);

        // then
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    public void shouldCallUpdateMethod() {
        // given
        when(repository.findById(anyLong())).thenReturn(Optional.of(createManufacturer()));
        // when
        service.updateManufacturer(createManufacturer());

        // then
        verify(repository, times(1)).saveAndFlush(any());
    }

    private Manufacturer createManufacturer() {
        return new Manufacturer(1L, "Name", "Nationality");
    }
}