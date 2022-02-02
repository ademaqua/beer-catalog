package com.ademaqua.beercatalog.beer.assembler;

import com.ademaqua.beercatalog.beer.entity.Beer;
import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class BeerModelAssemblerTest {

    @InjectMocks
    private BeerModelAssembler assembler;

    @Test
    public void shouldGenerateLinksWithGivenBeer() {
        // given
        Beer beer = getBeer();

        // when
        EntityModel<Beer> actualResponse = assembler.toModel(beer);

        // then
        assertEquals(beer, actualResponse.getContent());
        assertTrue(actualResponse.getLinks().hasSize(4));
    }

    @Test
    public void shouldGenerateACollectionModel() {
        // given
        List<Beer> beers = List.of(getBeer());

        // when
        CollectionModel<EntityModel<Beer>> actualResponse = assembler.toCollectionModel(beers);

        // then
        assertEquals(actualResponse.getContent().size(), 1);
        assertEquals(actualResponse.getContent().stream().map(EntityModel::getContent).collect(Collectors.toList()), beers);
        assertTrue(actualResponse.getLinks().hasSize(2));
    }

    private Beer getBeer() {
        return Beer.builder().name("NAME")
                .graduation(0.0).type("TYPE")
                .description("DESCRIPTION")
                .manufacturer(Manufacturer.builder().name("MANUFACTURER").nationality("COUNTRY").build())
                .build();
    }
}