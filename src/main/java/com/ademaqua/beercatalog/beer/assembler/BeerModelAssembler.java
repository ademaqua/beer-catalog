package com.ademaqua.beercatalog.beer.assembler;

import com.ademaqua.beercatalog.beer.controller.impl.BeerControllerImpl;
import com.ademaqua.beercatalog.beer.entity.Beer;
import com.ademaqua.beercatalog.beer.entity.BeerModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BeerModelAssembler implements RepresentationModelAssembler<Beer, BeerModel> {

    @Override
    public BeerModel toModel(Beer beer) {
        BeerModel beerModel = new BeerModel();
        beerModel.add(linkTo(methodOn(BeerControllerImpl.class).getById(beer.getId())).withSelfRel(),
                linkTo(methodOn(BeerControllerImpl.class).deleteBeerById(beer.getId())).withRel("delete"),
                linkTo(methodOn(BeerControllerImpl.class).updateBeer(beer.getId(), beer)).withRel("update"));
        beerModel.setId(beer.getId());
        beerModel.setName(beer.getName());
        beerModel.setDescription(beer.getDescription());
        beerModel.setGraduation(beer.getGraduation());
        beerModel.setType(beer.getType());
        beerModel.setManufacturer(beer.getManufacturer());
        return beerModel;
    }
}
