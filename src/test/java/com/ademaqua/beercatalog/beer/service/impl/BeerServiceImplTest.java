package com.ademaqua.beercatalog.beer.service.impl;

import com.ademaqua.beercatalog.beer.entity.Beer;
import com.ademaqua.beercatalog.beer.entity.BeerDto;
import com.ademaqua.beercatalog.beer.repository.BeerRepository;
import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;
import com.ademaqua.beercatalog.manufacturer.service.ManufacturerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BeerServiceImplTest {

    @InjectMocks
    private BeerServiceImpl beerService;

    @Mock
    private BeerRepository beerRepository;

    @Mock
    private ManufacturerService manufacturerService;

    @Test
    public void shouldCallFindByIdInRepository() {
        // when
        beerService.findById(1L);

        // then
        verify(beerRepository, times(1)).findById(1L);
    }

    @Test
    public void shouldCallFindAllBeersInRepository() {
        // when
        Pageable pageable = PageRequest.of(0,1);
        beerService.findAllBeers(pageable);

        // then
        verify(beerRepository, times(1)).findAll(pageable);
    }

    @Test
    public void shouldCallExistBeer() {
        // when
        when(manufacturerService.findManufacturerById(anyLong())).thenReturn(Optional.of(new Manufacturer(1L, "Name", "Nationality")));
        beerService.beerExists(BeerDto.builder().name("Name").type("Type").manufacturerId(1L).build());

        // then
        verify(beerRepository, times(1)).existsBeerByNameAndTypeAndManufacturer(anyString(), anyString(), any());
    }

    @Test
    public void shouldCallFindByNameMethod() {
        // when
        beerService.findByName("NAME");

        // then
        verify(beerRepository, times(1)).findBeerByName("NAME");
    }

    @Test
    public void shouldSaveBeer() {
        // given
        when(manufacturerService.findManufacturerById(anyLong())).thenReturn(Optional.of(new Manufacturer(1L, "Name", "Nationality")));

        // when
        beerService.saveBeer(BeerDto.builder().manufacturerId(1L).build());

        // then
        verify(beerRepository, times(1)).save(any());
    }

    @Test
    public void shouldCallDeleteMethod() {
        // when
        beerService.deleteBeerById(1L);

        // then
        verify(beerRepository, times(1)).deleteById(1L);
    }

    @Test
    public void shouldFindManufacturerAndCallMethod() {
        // given
        when(manufacturerService.findManufacturerById(anyLong())).thenReturn(Optional.of(new Manufacturer()));

        // when
        Pageable pageable = PageRequest.of(0,1);
        beerService.findAllBeers(pageable);
        beerService.findBeersByManufacturerIdAndPageable(1L, pageable);

        // then
        verify(beerRepository, times(1)).findBeersByManufacturer(any(), eq(pageable));
    }

    @Test
    public void shouldThrowExceptionWhenNoManufacturerFound() {
        // given
        when(manufacturerService.findManufacturerById(anyLong())).thenReturn(Optional.empty());

        // then
        assertThrows(ResponseStatusException.class, () -> beerService.findBeersByManufacturerIdAndPageable(1L, PageRequest.of(0, 1)));
    }

    @Test
    public void shouldCallUpdateMethod() {
        // given
        when(beerRepository.findById(any())).thenReturn(Optional.of(createBeer()));

        // when
        beerService.updateBeer(createBeer());

        // then
        verify(beerRepository, times(1)).saveAndFlush(any());
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