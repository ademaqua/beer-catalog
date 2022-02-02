package com.ademaqua.beercatalog.beer.service.impl;

import com.ademaqua.beercatalog.beer.entity.Beer;
import com.ademaqua.beercatalog.beer.entity.dto.BeerDto;
import com.ademaqua.beercatalog.beer.mapper.BeerMapper;
import com.ademaqua.beercatalog.beer.repository.BeerRepository;
import com.ademaqua.beercatalog.beer.service.BeerService;
import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;
import com.ademaqua.beercatalog.manufacturer.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class BeerServiceImpl implements BeerService {

    @Autowired
    private BeerRepository beerRepository;

    @Autowired
    private BeerMapper beerMapper;

    @Autowired
    private ManufacturerService manufacturerService;

    @Override
    public Optional<Beer> findById(Long id) {
        return beerRepository.findBeerById(id);
    }

    @Override
    public List<Beer> findAllBeers() {
        return beerRepository.findAllBeers();
    }

    @Override
    public boolean beerExists(BeerDto beer) {
        return beerRepository.existsBeer(beer);
    }

    @Override
    public Optional<Beer> findByName(String name) {
        return beerRepository.findBeerByName(name);
    }

    @Override
    public Beer saveBeer(BeerDto beer) {
        Beer beerToSave = beerMapper.map(beer);
        return beerRepository.save(beerToSave);
    }

    @Override
    public void deleteBeerById(Long id) {
        beerRepository.deleteBeerById(id);
    }

    @Override
    public List<Beer> findBeersByManufacturerId(Long id) {
        Manufacturer manufacturer = manufacturerService.findManufacturerById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Manufacturer not found"));
        return beerRepository.findBeersByManufacturer(manufacturer);
    }

    @Override
    public void updateBeer(Beer actualBeer) {
        beerRepository.updateBeer(actualBeer);
    }


}
