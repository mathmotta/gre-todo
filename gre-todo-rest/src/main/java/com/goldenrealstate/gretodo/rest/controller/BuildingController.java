package com.goldenrealstate.gretodo.rest.controller;

import com.goldenrealstate.gretodo.business.exception.InvalidNameException;
import com.goldenrealstate.gretodo.business.service.IBuildingService;
import com.goldenrealstate.gretodo.common.AbstractResultRepresentation;
import com.goldenrealstate.gretodo.common.BuildingRepresentation;
import com.goldenrealstate.gretodo.common.ErrorRepresentation;
import com.goldenrealstate.gretodo.common.ResultRepresentationList;
import com.goldenrealstate.gretodo.data.model.Building;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.stream.Collectors;

/**
 * Controller for Building's CRUD operations
 *
 * @author Mathews Motta
 * @since 1.0
 */
@RestController
@RequestMapping("/api/buildings")
public class BuildingController {

    @Autowired
    private IBuildingService buildingService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<AbstractResultRepresentation> create(@RequestBody BuildingRepresentation building){
        try{
            BuildingRepresentation br = buildingService.create(building.getName()).toDto();
            return ResponseEntity.created(URI.create(building.getName())).body(br);
        }catch (InvalidNameException ine){
            return ResponseEntity.badRequest().body(new ErrorRepresentation(ine.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorRepresentation(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<AbstractResultRepresentation> update(@PathVariable long id, @RequestBody BuildingRepresentation building){
        try{
            BuildingRepresentation br = buildingService.update(id, building.getName()).toDto();
            return new ResponseEntity<>(br, HttpStatus.OK);
        }catch (InvalidNameException ine){
            return ResponseEntity.badRequest().body(new ErrorRepresentation(ine.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorRepresentation(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<AbstractResultRepresentation> delete(@PathVariable long id){
        try{
            buildingService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (EntityNotFoundException enfe){
            return ResponseEntity.badRequest().body(new ErrorRepresentation(enfe.getMessage(), HttpStatus.NOT_FOUND.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorRepresentation(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<BuildingRepresentation> findById(@PathVariable long id){
        Building building = buildingService.findById(id);
        return new ResponseEntity<>(building.toDto(), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultRepresentationList<BuildingRepresentation> findAll(@RequestParam(required = false) String name, Pageable pageable){
        Page<Building> buildings;
        if(name != null)
            buildings = buildingService.findByName(name, pageable);
        else
            buildings = buildingService.findAll(pageable);
        return new ResultRepresentationList<>(
                buildings.getTotalElements(),
                buildings.getSize(),
                buildings.getNumber(),
                buildings.getContent().stream().map(Building::toDto).collect(Collectors.toList()));
    }

}
