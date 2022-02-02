package com.ademaqua.beercatalog.beer.repository;

import com.ademaqua.beercatalog.beer.entity.Beer;
import com.ademaqua.beercatalog.beer.entity.dto.BeerDto;
import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class BeerRepositoryTest {

    @InjectMocks
    private BeerRepository repository;

    @Test
    public void shouldSaveNewBeer() {
        // given
        Beer expectedBeer = createBeer();

        // when
        Beer savedBeer = repository.save(expectedBeer);

        // then
        assertEquals(1, savedBeer.getId());
        assertAll("beer content",
                () -> assertEquals(expectedBeer.getName(), savedBeer.getName()),
                () -> assertEquals(expectedBeer.getType(), savedBeer.getType()),
                () -> assertEquals(expectedBeer.getDescription(), savedBeer.getDescription()),
                () -> assertEquals(expectedBeer.getGraduation(), savedBeer.getGraduation()),
                () -> assertEquals(expectedBeer.getManufacturer(), savedBeer.getManufacturer()));
    }

    @Test
    public void shouldReturnEmptyWhenNoBeerFound() {
        // when
        Optional<Beer> beer = repository.findBeerById(1L);

        // then
        assertTrue(beer.isEmpty());
    }

    @Test
    public void shouldReturnBeerWhenFound() {
        // given
        Beer expectedBeer = createBeer();
        repository.save(expectedBeer);

        // when
        Optional<Beer> savedBeer = repository.findBeerById(1L);

        // then
        assertTrue(savedBeer.isPresent());
        assertAll("beer content",
                () -> assertEquals(1, savedBeer.get().getId()),
                () -> assertEquals(expectedBeer.getName(), savedBeer.get().getName()),
                () -> assertEquals(expectedBeer.getType(), savedBeer.get().getType()),
                () -> assertEquals(expectedBeer.getDescription(), savedBeer.get().getDescription()),
                () -> assertEquals(expectedBeer.getGraduation(), savedBeer.get().getGraduation()),
                () -> assertEquals(expectedBeer.getManufacturer(), savedBeer.get().getManufacturer()));
    }

    @Test
    public void shouldReturnEmptyListOfBeers() {
        // when
        List<Beer> beers = repository.findAllBeers();

        // then
        assertTrue(beers.isEmpty());
    }

    @Test
    public void shouldReturnTrueIfKeyExists() {
        // given
        repository.save(createBeer());
        BeerDto beerDto = new BeerDto();
        beerDto.setId(1L);

        // then
        assertTrue(repository.existsBeer(beerDto));
    }

    @Test
    public void shouldReturnTrueIfNameExists() {
        // given
        repository.save(createBeer());
        BeerDto beerDto = new BeerDto();
        beerDto.setName("NAME");

        // then
        assertTrue(repository.existsBeer(beerDto));
    }

    @Test
    public void shouldReturnTrueIfDescriptionExists() {
        // given
        repository.save(createBeer());
        BeerDto beerDto = new BeerDto();
        beerDto.setDescription("DESCRIPTION");

        // then
        assertTrue(repository.existsBeer(beerDto));
    }

    @Test
    public void shouldReturnTrueIfTypeExists() {
        // given
        repository.save(createBeer());
        BeerDto beerDto = new BeerDto();
        beerDto.setType("TYPE");

        // then
        assertTrue(repository.existsBeer(beerDto));
    }

    @Test
    public void shouldReturnTrueIfGraduationExists() {
        // given
        repository.save(createBeer());
        BeerDto beerDto = new BeerDto();
        beerDto.setGraduation(0.0);

        // then
        assertTrue(repository.existsBeer(beerDto));
    }

    @Test
    public void shouldReturnTrueIfManufacturerIdExists() {
        // given
        repository.save(createBeer());
        BeerDto beerDto = new BeerDto();
        beerDto.setManufacturerId(1L);

        // then
        assertTrue(repository.existsBeer(beerDto));
    }

    @Test
    public void shouldFilterValueWhenSomeDataIsFiltered() {
        // given
        repository.save(createBeer());
        BeerDto beerDto = new BeerDto();
        beerDto.setName("NEW_NAME");

        // then
        assertFalse(repository.existsBeer(beerDto));
    }

    @Test
    public void shouldFindBeerByItsName() {
        // given
        Beer expectedBeer = createBeer();
        repository.save(expectedBeer);

        // when
        Optional<Beer> beer = repository.findBeerByName("NAME");

        // then
        assertTrue(beer.isPresent());
        assertAll("beer content",
                () -> assertEquals(1, beer.get().getId()),
                () -> assertEquals(expectedBeer.getName(), beer.get().getName()),
                () -> assertEquals(expectedBeer.getType(), beer.get().getType()),
                () -> assertEquals(expectedBeer.getDescription(), beer.get().getDescription()),
                () -> assertEquals(expectedBeer.getGraduation(), beer.get().getGraduation()),
                () -> assertEquals(expectedBeer.getManufacturer(), beer.get().getManufacturer()));
    }

    @Test
    public void shouldDeletePresentBeer() {
        // given
        repository.save(createBeer());

        // when
        repository.deleteBeerById(1L);

        // then
        Optional<Beer> beer = repository.findBeerById(1L);
        assertTrue(beer.isEmpty());
    }

    @Test
    public void shouldFindBeersByManufacturer() {
        // given
        Beer expectedBeer = createBeer();
        repository.save(expectedBeer);

        // when
        List<Beer> beers = repository.findBeersByManufacturer(Manufacturer.builder().name("MANUFACTURER").nationality("COUNTRY").build());

        // then
        assertEquals(1, beers.size());
    }

    @Test
    public void shouldUpdateABeer() {
        // given
        Beer expectedBeer = createBeer();
        expectedBeer.setId(1L);
        repository.save(expectedBeer);
        expectedBeer.setName("NEW_NAME");

        // when
        repository.updateBeer(expectedBeer);

        // then
        Optional<Beer> savedBeer = repository.findBeerById(1L);
        assertTrue(savedBeer.isPresent());
        assertAll("beer content",
                () -> assertEquals(1, savedBeer.get().getId()),
                () -> assertEquals(expectedBeer.getName(), savedBeer.get().getName()),
                () -> assertEquals(expectedBeer.getType(), savedBeer.get().getType()),
                () -> assertEquals(expectedBeer.getDescription(), savedBeer.get().getDescription()),
                () -> assertEquals(expectedBeer.getGraduation(), savedBeer.get().getGraduation()),
                () -> assertEquals(expectedBeer.getManufacturer(), savedBeer.get().getManufacturer()));

    }

    private Beer createBeer() {
        return Beer.builder()
                .name("NAME").type("TYPE").graduation(0.0)
                .description("DESCRIPTION").manufacturer(
                        Manufacturer.builder().id(1L).name("MANUFACTURER").nationality("COUNTRY").build())
                .build();
    }
}