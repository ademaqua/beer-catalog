package com.ademaqua.beercatalog.beer.repository;

import com.ademaqua.beercatalog.beer.entity.Beer;
import com.ademaqua.beercatalog.beer.entity.dto.BeerDto;
import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;


@Repository
public class BeerRepository {

    // We are going to use a inMemory Map
    // to store object data
    private final Map<Long, Beer> beerMap;
    private Long id;

    public BeerRepository() {
        this.beerMap = new HashMap<>();
        this.id = 0L;
    }

    public Beer save(Beer beer) {
        this.id = this.id + 1;
        beer.setId(this.id);
        beerMap.put(beer.getId(), beer);
        return beer;
    }

    public Optional<Beer> findBeerById(Long id) {
        if (isNull(beerMap.get(id))) {
            return Optional.empty();
        }
        return Optional.of(beerMap.get(id));
    }

    public List<Beer> findAllBeers() {
        return List.copyOf(beerMap.values());
    }

    public boolean existsBeer(BeerDto beer) {
        List<Beer> filteredValues = new ArrayList<>(beerMap.values());
        if (nonNull(beer.getId())) {
            return beerMap.containsKey(beer.getId());
        }
        if (nonNull(beer.getName())) {
            filteredValues = filteredValues.stream().filter(beerToFilter -> beerToFilter.getName().equals(beer.getName())).collect(Collectors.toList());
        }
        if (nonNull(beer.getDescription())) {
            filteredValues = filteredValues.stream().filter(beerToFilter -> beerToFilter.getDescription().equals(beer.getDescription())).collect(Collectors.toList());
        }
        if (nonNull(beer.getType())) {
            filteredValues = filteredValues.stream().filter(beerToFilter -> beerToFilter.getType().equals(beer.getType())).collect(Collectors.toList());
        }
        if (nonNull(beer.getGraduation())) {
            filteredValues = filteredValues.stream().filter(beerToFilter -> beerToFilter.getGraduation().equals(beer.getGraduation())).collect(Collectors.toList());
        }
        if (nonNull(beer.getManufacturerId())) {
            filteredValues = filteredValues.stream().filter(beerToFilter -> beerToFilter.getManufacturer().getId().equals(beer.getManufacturerId())).collect(Collectors.toList());
        }

        return filteredValues.size() == 1;
    }

    public Optional<Beer> findBeerByName(String name) {
        return beerMap.values().stream().filter(beerToFilter -> beerToFilter.getName().equals(name)).findFirst();
    }

    public void deleteBeerById(Long id) {
        beerMap.remove(id);
    }

    public List<Beer> findBeersByManufacturer(Manufacturer manufacturer) {
        return beerMap.values().stream().filter(beer -> beer.getManufacturer().equals(manufacturer)).collect(Collectors.toList());
    }

    public void updateBeer(Beer actualBeer) {
        beerMap.put(actualBeer.getId(), actualBeer);
    }
}
