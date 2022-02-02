package com.ademaqua.beercatalog.manufacturer.service.impl;

import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;
import com.ademaqua.beercatalog.manufacturer.repository.ManufacturerRepository;
import com.ademaqua.beercatalog.manufacturer.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Override
    public List<Manufacturer> findAllManufacturers() {
        return manufacturerRepository.findAllManufacturers();
    }

    @Override
    public Optional<Manufacturer> findManufacturerById(Long id) {
        return manufacturerRepository.findManufacturerById(id);
    }

    @Override
    public boolean exists(Manufacturer manufacturer) {
        return manufacturerRepository.existsManufacturer(manufacturer);
    }

    @Override
    public Manufacturer saveManufacturer(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    @Override
    public void deleteManufacturerById(Long id) {
        manufacturerRepository.deleteManufacturerById(id);
    }

    @Override
    public void updateManufacturer(Manufacturer actualManufacturer) {
        manufacturerRepository.updateManufacturer(actualManufacturer);
    }

}
