package com.goldenrealstate.gretodo.rest.controller;

import com.goldenrealstate.gretodo.business.exception.InvalidNameException;
import com.goldenrealstate.gretodo.business.service.IPersonService;
import com.goldenrealstate.gretodo.common.AbstractResultRepresentation;
import com.goldenrealstate.gretodo.common.ErrorRepresentation;
import com.goldenrealstate.gretodo.common.PersonRepresentation;
import com.goldenrealstate.gretodo.common.ResultRepresentationList;
import com.goldenrealstate.gretodo.data.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.util.stream.Collectors;

/**
 * Controller for Person's CRUD operations
 *
 * @author Mathews Motta
 * @since 1.0
 */
@RestController
@RequestMapping("/api/persons")
@CrossOrigin
public class PersonController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private IPersonService personService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<AbstractResultRepresentation> create(@RequestBody PersonRepresentation person){
        try{
            LOGGER.trace("Started processing: 'create' with data {}", person);
            PersonRepresentation pr = personService.create(person.getName()).toDto();
            LOGGER.trace("Finished processing: 'create' with data {}", person);
            return ResponseEntity.created(URI.create(person.getName())).body(pr);
        }catch (InvalidNameException ine){
            LOGGER.error("Invalid name when creating a person. The name cannot be null or empty. Stacktrace: \\n{}", ine.getStackTrace());
            return ResponseEntity.badRequest().body(new ErrorRepresentation(ine.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }catch (Exception e){
            LOGGER.error("An unhandled error happened when creating a project. Stacktrace: \n{}", e.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorRepresentation(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<AbstractResultRepresentation> update(@PathVariable long id, @RequestBody PersonRepresentation person){
        try{
            LOGGER.trace("Started processing: 'update' with id: {}, data {}", id, person);
            PersonRepresentation pr = personService.update(id, person.getName()).toDto();
            LOGGER.trace("Finished processing: 'update' with id: {}, data {}", id, person);
            return new ResponseEntity<>(pr, HttpStatus.OK);
        }catch (InvalidNameException ine){
            LOGGER.error("Invalid name when updating a person. The name cannot be null or empty. Stacktrace: \\n{}", ine.getStackTrace());
            return ResponseEntity.badRequest().body(new ErrorRepresentation(ine.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }catch (Exception e){
            LOGGER.error("An unhandled error happened when creating a project. Stacktrace: \n{}", e.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorRepresentation(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<AbstractResultRepresentation> delete(@PathVariable long id){
        try{
            LOGGER.trace("Started processing: 'delete' with id {}", id);
            personService.delete(id);
            LOGGER.trace("Finished processing: 'delete' with id {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (EntityNotFoundException enfe){
            LOGGER.error("Entity with id {} was not found. Stacktrace: \n{}", id, enfe.getStackTrace());
            return ResponseEntity.badRequest().body(new ErrorRepresentation(enfe.getMessage(), HttpStatus.NOT_FOUND.value()));
        }catch (Exception e){
            LOGGER.error("An unhandled error happened when creating a project. Stacktrace: \n{}", e.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorRepresentation(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<PersonRepresentation> findById(@PathVariable long id){
        LOGGER.trace("Started processing: 'findById' with id {}", id);
        Person person = personService.findById(id);
        LOGGER.trace("Finished processing: 'findById' with id {}", id);
        return new ResponseEntity<>(person.toDto(), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultRepresentationList<PersonRepresentation> findAll(@RequestParam(required = false) String name, Pageable pageable){
        LOGGER.trace("Started processing: 'findAll' with name {} and pageable: {}", name, pageable);
        Page<Person> persons;
        if(name != null)
            persons = personService.findByName(name, pageable);
        else
            persons = personService.findAll(pageable);
        LOGGER.trace("Finished processing: 'findAll' with name {} and pageable: {}", name, pageable);
        return new ResultRepresentationList<>(
                persons.getTotalElements(),
                persons.getSize(),
                persons.getNumber(),
                persons.getContent().stream().map(Person::toDto).collect(Collectors.toList()));
    }

}
