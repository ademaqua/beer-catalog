package com.ademaqua.beercatalog.beer.mapper;

import com.ademaqua.beercatalog.beer.entity.Beer;
import com.ademaqua.beercatalog.beer.entity.dto.BeerDto;
import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;
import com.ademaqua.beercatalog.manufacturer.service.ManufacturerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BeerMapperTest {

    @InjectMocks
    private BeerMapper mapper;

    @Mock
    private ManufacturerService service;

    @Test
    public void shouldMapBeerFromDto() {
        // given
        BeerDto beerDto = new BeerDto();
        beerDto.setName("BEER");
        beerDto.setGraduation(1.0);
        beerDto.setDescription("New Description");
        beerDto.setType("New type");
        beerDto.setManufacturerId(1L);

        when(service.findManufacturerById(anyLong())).thenReturn(Optional.of(Manufacturer.builder().build()));

        // when
        Beer finalBeer = mapper.map(beerDto);

        // then
        assertAll("Beer values",
                () -> assertEquals(beerDto.getName(), finalBeer.getName()),
                () -> assertEquals(beerDto.getDescription(), finalBeer.getDescription()),
                () -> assertEquals(beerDto.getType(), finalBeer.getType()),
                () -> assertEquals(beerDto.getGraduation(), finalBeer.getGraduation()));

    }

    @Test
    public void shouldThrowExceptionWhenNoManufacturerFound() {
        // given
        BeerDto beerDto = new BeerDto();
        beerDto.setName("BEER");
        beerDto.setGraduation(1.0);
        beerDto.setDescription("New Description");
        beerDto.setType("New type");
        beerDto.setManufacturerId(1L);

        when(service.findManufacturerById(anyLong())).thenReturn(Optional.empty());

        // then
        assertThrows(ResponseStatusException.class, () -> mapper.map(beerDto));
    }

}