package com.ademaqua.beercatalog.manufacturer.repository;

import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class ManufacturerRepositoryTest {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Test
    public void shouldSaveManufacturer() {
        Manufacturer manufacturer = createManufacturer("New_Name");
        Manufacturer savedManufacturer = manufacturerRepository.save(manufacturer);
        assertThat(savedManufacturer).usingRecursiveComparison().ignoringFields("id").isEqualTo(manufacturer);
    }

    @Test
    public void shouldReturnManufacturerWhenFound() {
        saveManufacturersInDB(3);
        List<Manufacturer> savedManufacturers = manufacturerRepository.findAll();
        assertThat(savedManufacturers).isNotEmpty();
        assertThat(savedManufacturers).hasSize(3);
        assertThat(savedManufacturers).doesNotContainNull();
    }

    @Test
    public void shouldReturnPagedManufacturers() {
        saveManufacturersInDB(5);
        Pageable pageable = PageRequest.of(0,2);
        Page<Manufacturer> savedManufacturers = manufacturerRepository.findAll(pageable);
        assertThat(savedManufacturers).isNotEmpty();
        assertThat(savedManufacturers).hasSize(2);
    }

    @Test
    public void shouldReturnTrueWhenManufacturerExists() {
        saveManufacturersInDB(5);
        boolean exists = manufacturerRepository.existsManufacturerByNameAndNationality("Name1", "Nationality");
        assertThat(exists).isTrue();
    }

    @Test
    public void shouldReturnFalseWhenManufacturerNotExists() {
        saveManufacturersInDB(5);
        boolean exists = manufacturerRepository.existsManufacturerByNameAndNationality("Name9", "Nationality");
        assertThat(exists).isFalse();
    }

    private void saveManufacturersInDB(int quantity) {
        for (int i = 0; i < quantity; i++) {
            manufacturerRepository.save(createManufacturer("Name" + i));
        }
    }

    private Manufacturer createManufacturer(String name) {
        return new Manufacturer(null, name, "Nationality");
    }
}