package com.ademaqua.beercatalog.manufacturer.entity;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Data
@Entity
public class Manufacturer extends RepresentationModel<Manufacturer> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String nationality;

    public Manufacturer() { }

    public Manufacturer(Long id, String name, String nationality) {
        this.id = id;
        this.name = name;
        this.nationality = nationality;
    }

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
