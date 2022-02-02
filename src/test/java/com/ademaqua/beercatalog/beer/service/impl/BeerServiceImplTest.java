package com.ademaqua.beercatalog.beer.service.impl;

import com.ademaqua.beercatalog.beer.entity.Beer;
import com.ademaqua.beercatalog.beer.entity.dto.BeerDto;
import com.ademaqua.beercatalog.beer.mapper.BeerMapper;
import com.ademaqua.beercatalog.beer.repository.BeerRepository;
import com.ademaqua.beercatalog.beer.service.BeerService;
import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;
import com.ademaqua.beercatalog.manufacturer.service.ManufacturerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BeerServiceImplTest {

    @InjectMocks
    private BeerServiceImpl beerService;

    @Mock
    private BeerRepository beerRepository;

    @Mock
    private BeerMapper beerMapper;

    @Mock
    private ManufacturerService manufacturerService;

    @Test
    public void shouldCallFindByIdInRepository() {
        // when
        beerService.findById(1L);

        // then
        verify(beerRepository, times(1)).findBeerById(1L);
    }

    @Test
    public void shouldCallFindAllBeersInRepository() {
        // when
        beerService.findAllBeers();

        // then
        verify(beerRepository, times(1)).findAllBeers();
    }

    @Test
    public void shouldCallExistBeer() {
        // when
        beerService.beerExists(new BeerDto());

        // then
        verify(beerRepository, times(1)).existsBeer(any(BeerDto.class));
    }

    @Test
    public void shouldCallFindByNameMethod() {
        // when
        beerService.findByName("NAME");

        // then
        verify(beerRepository, times(1)).findBeerByName("NAME");
    }

    @Test
    public void shouldCallMapAndSaveMethods() {
        // when
        beerService.saveBeer(new BeerDto());

        // then
        verify(beerMapper, times(1)).map(any(BeerDto.class));
        verify(beerRepository, times(1)).save(any());
    }

    @Test
    public void shouldCallDeleteMethod() {
        // when
        beerService.deleteBeerById(1L);

        // then
        verify(beerRepository, times(1)).deleteBeerById(1L);
    }

    @Test
    public void shouldFindManufacturerAndCallMethod() {
        // given
        when(manufacturerService.findManufacturerById(anyLong())).thenReturn(Optional.of(Manufacturer.builder().build()));

        // when
        beerService.findBeersByManufacturerId(1L);

        // then
        verify(beerRepository, times(1)).findBeersByManufacturer(any());
    }

    @Test
    public void shouldThrowExceptionWhenNoManufacturerFound() {
        // given
        when(manufacturerService.findManufacturerById(anyLong())).thenReturn(Optional.empty());

        // then
        assertThrows(ResponseStatusException.class, () -> beerService.findBeersByManufacturerId(1L));
    }

    @Test
    public void shouldCallUpdateMethod() {
        // when
        beerService.updateBeer(createBeer());

        // then
        verify(beerRepository, times(1)).updateBeer(any());
    }

    private Beer createBeer() {
        return Beer.builder()
                .name("NAME").type("TYPE").graduation(0.0)
                .description("DESCRIPTION").manufacturer(
                        Manufacturer.builder().id(1L).name("MANUFACTURER").nationality("COUNTRY").build())
                .build();
    }
}
