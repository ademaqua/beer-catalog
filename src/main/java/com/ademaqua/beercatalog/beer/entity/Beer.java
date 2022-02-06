package com.ademaqua.beercatalog.beer.entity;

import com.ademaqua.beercatalog.manufacturer.entity.Manufacturer;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Data
@Entity
public class Beer extends RepresentationModel<Beer> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Double graduation;
    private String type;
    private String description;

    @ManyToOne
    private Manufacturer manufacturer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Beer beer = (Beer) o;
        return Double.compare(beer.graduation, graduation) == 0 && name.equals(beer.name)
                && type.equals(beer.type) && description.equals(beer.description)
                && manufacturer.equals(beer.manufacturer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, graduation, type, description, manufacturer);
    }
}
