package com.ademaqua.beercatalog.manufacturer.assembler;

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
class ManufacturerModelAssemblerTest {

    @InjectMocks
    private ManufacturerModelAssembler assembler;

    @Test
    public void shouldGenerateLinksWithGivenManufacturer() {
        // given
        Manufacturer manufacturer = getManufacturer();

        // when
        EntityModel<Manufacturer> actualResponse = assembler.toModel(manufacturer);

        // then
        assertEquals(manufacturer, actualResponse.getContent());
        assertTrue(actualResponse.getLinks().hasSize(3));
    }

    @Test
    public void shouldGenerateACollectionModel() {
        // given
        List<Manufacturer> manufacturerList = List.of(getManufacturer());

        // when
        CollectionModel<EntityModel<Manufacturer>> actualResponse = assembler.toCollectionModel(manufacturerList);

        // then
        assertEquals(actualResponse.getContent().size(), 1);
        assertEquals(actualResponse.getContent().stream().map(EntityModel::getContent).collect(Collectors.toList()), manufacturerList);
        assertTrue(actualResponse.getLinks().hasSize(2));
    }

    private Manufacturer getManufacturer() {
        return Manufacturer.builder().name("MANUFACTURER").nationality("COUNTRY").build();
    }
}