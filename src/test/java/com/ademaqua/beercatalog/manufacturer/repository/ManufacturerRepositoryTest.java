package com.ademaqua.beercatalog.manufacturer.repository;

import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ManufacturerRepositoryTest {

    @InjectMocks
    private ManufacturerRepository repository;

    @Test
    public void shouldSaveManufacturer() {
        // given
        Manufacturer manufacturer = createManufacturer();

        // when
        Manufacturer savedValue = repository.save(manufacturer);

        // then
        assertEquals(1, savedValue.getId());
        assertAll("manufacturer attributed",
                () -> assertEquals(savedValue.getName(), manufacturer.getName()),
                () -> assertEquals(savedValue.getNationality(), manufacturer.getNationality()));
    }

    @Test
    public void shouldReturnEmptyWhenNoManufacturerFound() {
        // when
        Optional<Manufacturer> manufacturerOptional = repository.findManufacturerById(1L);

        // then
        assertTrue(manufacturerOptional.isEmpty());
    }

    @Test
    public void shouldReturnManufacturerWhenFound() {
        // given
        Manufacturer manufacturer = createManufacturer();
        repository.save(manufacturer);

        // when
        Optional<Manufacturer> manufacturerOptional = repository.findManufacturerById(1L);

        // then
        assertTrue(manufacturerOptional.isPresent());
        assertAll("manufacturer attributed",
                () -> assertEquals(manufacturerOptional.get().getName(), manufacturer.getName()),
                () -> assertEquals(manufacturerOptional.get().getNationality(), manufacturer.getNationality()));
    }

    @Test
    public void shouldReturnEmptyListOfManufacturers() {
        // when
        List<Manufacturer> manufacturerList = repository.findAllManufacturers();

        // then
        assertTrue(manufacturerList.isEmpty());
    }

    @Test
    public void shouldReturnFalseIfManufacturerNotFound() {
        // given
        Manufacturer manufacturer = createManufacturer();

        // then
        assertFalse(repository.existsManufacturer(manufacturer));
    }

    @Test
    public void shouldReturnTrueWhenManufacturerFound() {
        // given
        Manufacturer manufacturer = createManufacturer();
        repository.save(manufacturer);

        // then
        assertTrue(repository.existsManufacturer(manufacturer));
    }

    @Test
    public void shouldDeleteManufacturer() {
        // given
        Manufacturer manufacturer = createManufacturer();
        repository.save(manufacturer);

        // when
        repository.deleteManufacturerById(1L);

        // then
        Optional<Manufacturer> manufacturerOptional = repository.findManufacturerById(1L);
        assertTrue(manufacturerOptional.isEmpty());
    }

    @Test
    public void shouldUpdateManufacturer() {
        // given
        Manufacturer manufacturer = createManufacturer();
        manufacturer.setId(1L);
        repository.save(manufacturer);
        manufacturer.setNationality("NEW COUNTRY");

        // when
        repository.updateManufacturer(manufacturer);

        // then
        Optional<Manufacturer> manufacturerOptional = repository.findManufacturerById(1L);
        assertTrue(manufacturerOptional.isPresent());
        assertEquals(manufacturerOptional.get().getNationality(), manufacturer.getNationality());
    }

    private Manufacturer createManufacturer() {
        return Manufacturer.builder().name("NAME").nationality("COUNTRY").build();
    }
}