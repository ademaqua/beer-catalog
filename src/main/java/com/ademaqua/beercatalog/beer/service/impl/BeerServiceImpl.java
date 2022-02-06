package com.ademaqua.beercatalog.beer.service.impl;

import com.ademaqua.beercatalog.beer.entity.Beer;
import com.ademaqua.beercatalog.beer.entity.BeerDto;
import com.ademaqua.beercatalog.beer.repository.BeerRepository;
import com.ademaqua.beercatalog.beer.service.BeerService;
import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;
import com.ademaqua.beercatalog.manufacturer.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class BeerServiceImpl implements BeerService {

    @Autowired
    private BeerRepository beerRepository;

    @Autowired
    private ManufacturerService manufacturerService;

    @Override
    public Optional<Beer> findById(Long id) {
        return beerRepository.findById(id);
    }

    @Override
    public Page<Beer> findAllBeers(Pageable pageable) {
        return beerRepository.findAll(pageable);
    }

    @Override
    public boolean beerExists(BeerDto beerDto) {
        Manufacturer manufacturer =
                manufacturerService.findManufacturerById(
                        beerDto.getManufacturerId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Manufacturer not found"));
        return beerRepository.existsBeerByNameAndTypeAndManufacturer(beerDto.getName(), beerDto.getType(), manufacturer);
    }

    @Override
    public Optional<Beer> findByName(String name) {
        return beerRepository.findBeerByName(name);
    }

    @Override
    public Beer saveBeer(BeerDto beerDto) {
        Beer beer = new Beer();
        Manufacturer manufacturer =
                manufacturerService.findManufacturerById(
                        beerDto.getManufacturerId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Manufacturer not found"));
        beer.setName(beerDto.getName());
        beer.setType(beerDto.getType());
        beer.setDescription(beerDto.getDescription());
        beer.setGraduation(beerDto.getGraduation());
        beer.setManufacturer(manufacturer);
        return beerRepository.save(beer);
    }

    @Override
    public void deleteBeerById(Long id) {
        beerRepository.deleteById(id);
    }

    @Override
    public void updateBeer(Beer actualBeer) {
        Beer beer = beerRepository.findById(actualBeer.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Beer not found"));
        beer.setName(actualBeer.getName());
        beer.setType(actualBeer.getType());
        beer.setDescription(actualBeer.getDescription());
        beer.setGraduation(actualBeer.getGraduation());
        beer.setManufacturer(actualBeer.getManufacturer());
        beerRepository.saveAndFlush(beer);
    }

    @Override
    public Page<Beer> findBeersByManufacturerIdAndPageable(Long id, Pageable pageRequest) {
        Manufacturer manufacturer =
                manufacturerService.findManufacturerById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Manufacturer not found"));
        return beerRepository.findBeersByManufacturer(manufacturer, pageRequest);
    }
}
