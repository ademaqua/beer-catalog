package com.ademaqua.beercatalog.beer.assembler;

import com.ademaqua.beercatalog.beer.controller.BeerController;
import com.ademaqua.beercatalog.beer.entity.Beer;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BeerModelAssembler implements RepresentationModelAssembler<Beer, EntityModel<Beer>> {

    @Override
    public EntityModel<Beer> toModel(Beer beer) {
        return EntityModel.of(beer,
                linkTo(methodOn(BeerController.class).getById(beer.getId())).withSelfRel(),
                linkTo(methodOn(BeerController.class).deleteBeerById(beer.getId())).withRel("delete"),
                linkTo(methodOn(BeerController.class).updateBeer(beer.getId(), beer)).withRel("update"),
                linkTo(methodOn(BeerController.class).getByManufacturer(beer.getManufacturer().getId())).withRel("manufacturer"));
    }

    @Override
    public CollectionModel<EntityModel<Beer>> toCollectionModel(Iterable<? extends Beer> beers) {
        CollectionModel<EntityModel<Beer>> beerModel = RepresentationModelAssembler.super.toCollectionModel(beers);
        beerModel.add(linkTo(methodOn(BeerController.class).getAll()).withSelfRel());
        beerModel.add(Link.of(linkTo(methodOn(BeerController.class)).toUriComponentsBuilder().build().toUriString()).withRel("add"));
        return beerModel;
    }
}
