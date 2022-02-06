package com.ademaqua.beercatalog.beer.controller.impl;

import com.ademaqua.beercatalog.beer.assembler.BeerModelAssembler;
import com.ademaqua.beercatalog.beer.controller.BeerController;
import com.ademaqua.beercatalog.beer.entity.Beer;
import com.ademaqua.beercatalog.beer.entity.BeerDto;
import com.ademaqua.beercatalog.beer.entity.BeerModel;
import com.ademaqua.beercatalog.beer.service.BeerService;
import com.ademaqua.beercatalog.beer.validator.BeerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.isNotBlank;


@RestController
@RequestMapping("/api/v1/beers")
public class BeerControllerImpl implements BeerController {

    @Autowired
    private BeerModelAssembler assembler;

    @Autowired
    private BeerService beerService;

    @Autowired
    private BeerValidator beerValidator;

    @Autowired
    private PagedResourcesAssembler<Beer> pagedResourcesAssembler;


    @Override
    @GetMapping
    public ResponseEntity<PagedModel<BeerModel>> getAll(@RequestParam(defaultValue = "0") int pageNumber,
                                                        @RequestParam(defaultValue = "25") int pageSize,
                                                        @RequestParam(defaultValue = "asc") String order) {
        Sort sort = Sort.by("id");
        if (order.equals("asc")) {
            sort.ascending();
        } else {
            sort.descending();
        }
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        Page<Beer> manufacturerPage = beerService.findAllBeers(pageRequest);

        PagedModel<BeerModel> collectionModel = pagedResourcesAssembler.toModel(manufacturerPage, assembler);
        return ResponseEntity.ok(collectionModel);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<BeerModel> getById(@PathVariable("id") Long id) {
        Optional<Beer> beer = beerService.findById(id);
        if (beer.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Beer not found!");
        }
        return ResponseEntity.ok(assembler.toModel(beer.get()));
    }

    @Override
    @PostMapping
    public ResponseEntity<BeerModel> addBeer(@RequestBody BeerDto beer) {
        if (!beerValidator.validateBeer(beer)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data");
        }
        if (beerService.beerExists(beer)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Beer already exists");
        }
        Beer beerSaved = beerService.saveBeer(beer);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(beerSaved));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBeerById(@PathVariable("id") Long id){
        beerService.deleteBeerById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<BeerModel> updateBeer(@PathVariable("id") Long id, @RequestBody Beer beer) {
        Beer actualBeer = beerService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found"));
        if (isNotBlank(beer.getName())) {
            actualBeer.setName(beer.getName());
        }
        if (isNotBlank(beer.getDescription())) {
            actualBeer.setDescription(beer.getDescription());
        }
        if (isNotBlank(beer.getType())) {
            actualBeer.setType(beer.getType());
        }
        if (nonNull(beer.getGraduation())) {
            actualBeer.setGraduation(beer.getGraduation());
        }
        if (nonNull(beer.getManufacturer())) {
            actualBeer.setManufacturer(beer.getManufacturer());
        }
        beerService.updateBeer(actualBeer);
        return ResponseEntity.ok().body(assembler.toModel(actualBeer));
    }

    @Override
    @GetMapping("/manufacturer/{id}")
    public ResponseEntity<PagedModel<BeerModel>> getByManufacturer(@PathVariable("id") Long id,
                                                                   @RequestParam(defaultValue = "0") int pageNumber,
                                                                   @RequestParam(defaultValue = "25") int pageSize,
                                                                   @RequestParam(defaultValue = "asc") String order) {
        Sort sort = Sort.by("id");
        if (order.equals("asc")) {
            sort.ascending();
        } else {
            sort.descending();
        }
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        Page<Beer> manufacturerPage = beerService.findBeersByManufacturerIdAndPageable(id, pageRequest);

        PagedModel<BeerModel> collectionModel = pagedResourcesAssembler.toModel(manufacturerPage, assembler);
        return ResponseEntity.ok(collectionModel);
    }
}
