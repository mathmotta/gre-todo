package com.goldenrealstate.gretodo.rest.controller;

import com.goldenrealstate.gretodo.business.exception.InvalidNameException;
import com.goldenrealstate.gretodo.business.service.IProjectService;
import com.goldenrealstate.gretodo.common.*;
import com.goldenrealstate.gretodo.data.model.Project;
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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * Controller for Project's CRUD operations
 *
 * @author Mathews Motta
 * @since 1.0
 */
@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private IProjectService projectService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<AbstractResultRepresentation> create(@RequestBody ProjectRepresentation project){
        try{
            ProjectRepresentationResponse pr = projectService.create(project).toDto();
            return ResponseEntity.created(URI.create(URLEncoder.encode(project.getName(), StandardCharsets.UTF_8))).body(pr);
        }catch (InvalidNameException ine){
            return ResponseEntity.badRequest().body(new ErrorRepresentation(ine.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorRepresentation(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<AbstractResultRepresentation> update(@PathVariable long id, @RequestBody ProjectRepresentation project){
        try{
            ProjectRepresentationResponse pr = projectService.update(id, project).toDto();
            return new ResponseEntity<>(pr, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorRepresentation(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<AbstractResultRepresentation> delete(@PathVariable long id){
        try{
            projectService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (EntityNotFoundException enfe){
            return ResponseEntity.badRequest().body(new ErrorRepresentation(enfe.getMessage(), HttpStatus.NOT_FOUND.value()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorRepresentation(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ProjectRepresentationResponse> findById(@PathVariable long id){
        Project project = projectService.findById(id);
        return new ResponseEntity<>(project.toDto(), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultRepresentationList<ProjectRepresentationResponse> findAll(Pageable pageable){
        Page<Project> buildings = projectService.findAll(pageable);
        return new ResultRepresentationList<>(
                buildings.getTotalElements(),
                buildings.getSize(),
                buildings.getNumber(),
                buildings.getContent().stream().map(Project::toDto).collect(Collectors.toList()));
    }

    @PostMapping(value= "/filter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResultRepresentationList<ProjectRepresentationResponse> findByFilter(@RequestBody ProjectRepresentation project, Pageable pageable){
        Page<Project> buildings = projectService.findByFilter(project, pageable);
        return new ResultRepresentationList<>(
                buildings.getTotalElements(),
                buildings.getSize(),
                buildings.getNumber(),
                buildings.getContent().stream().map(Project::toDto).collect(Collectors.toList()));
    }

}
