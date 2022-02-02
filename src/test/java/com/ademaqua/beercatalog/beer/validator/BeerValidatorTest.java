package com.ademaqua.beercatalog.beer.validator;

import com.ademaqua.beercatalog.beer.entity.dto.BeerDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class BeerValidatorTest {

    @InjectMocks
    private BeerValidator beerValidator;

    @Test
    public void shouldReturnFalseWhenValuesAreBlank() {
        // given
        BeerDto beerDto = new BeerDto();

        // then
        assertFalse(beerValidator.validateBeer(beerDto));
    }

    @Test
    public void shouldReturnFalseWhenOnlyNameIsWritten() {
        // given
        BeerDto beerDto = new BeerDto();
        beerDto.setName("NAME");

        // then
        assertFalse(beerValidator.validateBeer(beerDto));
    }

    @Test
    public void shouldReturnFalseWhenNameAndTypeAreWritten() {
        // given
        BeerDto beerDto = new BeerDto();
        beerDto.setName("NAME");
        beerDto.setType("TYPE");

        // then
        assertFalse(beerValidator.validateBeer(beerDto));
    }

    @Test
    public void shouldReturnFalseWhenNameAndTypeAndDescriptionAreWritten() {
        // given
        BeerDto beerDto = new BeerDto();
        beerDto.setName("NAME");
        beerDto.setType("TYPE");
        beerDto.setDescription("DESCRIPTION");

        // then
        assertFalse(beerValidator.validateBeer(beerDto));
    }

    @Test
    public void shouldReturnFalseWhenNameAndTypeAndDescriptionAndManufacturerAreWritten() {
        // given
        BeerDto beerDto = new BeerDto();
        beerDto.setName("NAME");
        beerDto.setType("TYPE");
        beerDto.setDescription("DESCRIPTION");
        beerDto.setManufacturerId(1L);

        // then
        assertFalse(beerValidator.validateBeer(beerDto));
    }

    @Test
    public void shouldReturnTrueWhenAllValidData() {
        // given
        BeerDto beerDto = new BeerDto();
        beerDto.setName("NAME");
        beerDto.setType("TYPE");
        beerDto.setDescription("DESCRIPTION");
        beerDto.setManufacturerId(1L);
        beerDto.setGraduation(1.0);

        // then
        assertTrue(beerValidator.validateBeer(beerDto));
    }
}