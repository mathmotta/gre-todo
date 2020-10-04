package com.goldenrealstate.gretodo.rest.controller;

import com.goldenrealstate.gretodo.business.exception.InvalidNameException;
import com.goldenrealstate.gretodo.business.service.IPersonService;
import com.goldenrealstate.gretodo.common.AbstractResultRepresentation;
import com.goldenrealstate.gretodo.common.ErrorRepresentation;
import com.goldenrealstate.gretodo.common.PersonRepresentation;
import com.goldenrealstate.gretodo.common.ResultRepresentationList;
import com.goldenrealstate.gretodo.data.model.Person;
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

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private IPersonService personService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<AbstractResultRepresentation> create(@RequestBody PersonRepresentation person){
        try{
            PersonRepresentation pr = personService.create(person.getName()).toDto();
            return ResponseEntity.created(URI.create(person.getName())).body(pr);
        }catch (InvalidNameException ine){
            return ResponseEntity.badRequest().body(new ErrorRepresentation(ine.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorRepresentation(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<AbstractResultRepresentation> update(@PathVariable long id, @RequestBody PersonRepresentation person){
        try{
            PersonRepresentation pr = personService.update(id, person.getName()).toDto();
            return new ResponseEntity<>(pr, HttpStatus.OK);
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
            personService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (EntityNotFoundException enfe){
            return ResponseEntity.badRequest().body(new ErrorRepresentation(enfe.getMessage(), HttpStatus.NOT_FOUND.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorRepresentation(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<PersonRepresentation> findById(@PathVariable long id){
        Person person = personService.findById(id);
        return new ResponseEntity<>(person.toDto(), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultRepresentationList<PersonRepresentation> findAll(@RequestParam(required = false) String name, Pageable pageable){
        Page<Person> persons;
        if(name != null)
            persons = personService.findByName(name, pageable);
        else
            persons = personService.findAll(pageable);
        return new ResultRepresentationList<>(
                persons.getTotalElements(),
                persons.getSize(),
                persons.getNumber(),
                persons.getContent().stream().map(Person::toDto).collect(Collectors.toList()));
    }

}
