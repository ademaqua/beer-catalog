package com.ademaqua.beercatalog.manufacturer.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

@Data
@Builder
public class Manufacturer extends RepresentationModel<Manufacturer> {

    private Long id;
    private String name;
    private String nationality;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Manufacturer that = (Manufacturer) o;
        return name.equals(that.name) && nationality.equals(that.nationality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, nationality);
    }
}
