package com.ademaqua.beercatalog.manufacturer.service.impl;

import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;
import com.ademaqua.beercatalog.manufacturer.repository.ManufacturerRepository;
import com.ademaqua.beercatalog.manufacturer.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Override
    public Page<Manufacturer> findAllManufacturersPaginated(Pageable pageable) {
        return manufacturerRepository.findAll(pageable);
    }

    @Override
    public Optional<Manufacturer> findManufacturerById(Long id) {
        return manufacturerRepository.findById(id);
    }

    @Override
    public boolean exists(Manufacturer manufacturer) {
        return manufacturerRepository.existsManufacturerByNameAndNationality(manufacturer.getName(), manufacturer.getNationality());
    }

    @Override
    public Manufacturer saveManufacturer(Manufacturer manufacturer) {
        return manufacturerRepository.saveAndFlush(manufacturer);
    }

    @Override
    public void deleteManufacturerById(Long id) {
        manufacturerRepository.deleteById(id);
    }

    @Override
    public void updateManufacturer(Manufacturer actualManufacturer) {
        Manufacturer manufacturer = manufacturerRepository
                .findById(actualManufacturer.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Manufacturer not found"));
        manufacturer.setNationality(actualManufacturer.getNationality());
        manufacturer.setName(actualManufacturer.getName());
        manufacturerRepository.saveAndFlush(manufacturer);
    }


}
