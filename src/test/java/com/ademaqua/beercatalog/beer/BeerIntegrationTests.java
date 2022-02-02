package com.ademaqua.beercatalog.beer;

import com.ademaqua.beercatalog.beer.controller.BeerController;
import com.ademaqua.beercatalog.beer.entity.Beer;
import com.ademaqua.beercatalog.beer.entity.dto.BeerDto;
import com.ademaqua.beercatalog.beer.service.BeerService;
import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BeerController.class)
@ComponentScan("com.ademaqua")
class BeerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BeerService beerService;

    private static final ObjectMapper mapper = new ObjectMapper();


    @Test
    @SneakyThrows
    public void shouldRetrieveAllBeersOK() {
        // Given
        List<Beer> beers = getMultipleBeers();
        when(beerService.findAllBeers()).thenReturn(beers);

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/beers"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("_embedded.beerList").exists())
                .andExpect(jsonPath("_embedded.beerList", hasSize(3)))
                .andExpect(jsonPath("_embedded.beerList[0].name", equalTo(beers.get(0).getName())));
    }

    @Test
    @SneakyThrows
    public void shouldRetrieveOneBeer() {
        // Given
        List<Beer> beers = getMultipleBeers();
        when(beerService.findById(any())).thenReturn(Optional.of(beers.get(0)));


        // when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/beers/1"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name", equalTo(beers.get(0).getName())));
    }

    @Test
    @SneakyThrows
    public void shouldPostNewBeer() {
        // given
        BeerDto beerDto = new BeerDto();
        beerDto.setGraduation(5.2);
        beerDto.setName("New Beer");
        beerDto.setType("New Type");
        beerDto.setDescription("New beer to taste");
        beerDto.setManufacturerId(1L);
        Beer beer = Beer.builder()
                .name("New Beer").graduation(5.2)
                .description("New beer to taste!").type("New Type")
                .manufacturer(getManufacturer()).build();
        String parsedJson = mapper.writeValueAsString(beerDto);
        when(beerService.saveBeer(beerDto)).thenReturn(beer);

        // when
        mockMvc.perform(post("/api/v1/beers").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8").content(parsedJson).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo("New Beer")))
                .andExpect(jsonPath("$.graduation", equalTo(5.2)))
                .andExpect(jsonPath("$.type", equalTo("New Type")));
    }

    @Test
    @SneakyThrows
    public void shouldDeleteBeer() {
        mockMvc.perform(delete("/api/v1/beers/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    private static List<Beer> getMultipleBeers() {
        return List.of(
                getBeer("First Beer", 4.1, "TypeOne", getManufacturer()),
                getBeer("Second Beer", 4.1, "TypeSecond", getManufacturer()),
                getBeer("Third Beer", 4.1, "TypeThird", getManufacturer()));
    }

    private static Beer getBeer(String name, double graduation, String type, Manufacturer manufacturer) {
        return Beer.builder().name(name).graduation(graduation).type(type).manufacturer(manufacturer).build();
    }

    private static Manufacturer getManufacturer() {
        return Manufacturer.builder().name("Manufacturer").nationality("Spain").build();
    }
}