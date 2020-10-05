package com.goldenrealstate.gretodo.business.service.impl;

import com.goldenrealstate.gretodo.business.exception.IdNotFoundException;
import com.goldenrealstate.gretodo.business.exception.InvalidNameException;
import com.goldenrealstate.gretodo.business.security.SecurityProvider;
import com.goldenrealstate.gretodo.business.service.IProjectService;
import com.goldenrealstate.gretodo.common.ProjectRepresentation;
import com.goldenrealstate.gretodo.data.filter.ProjectSpecification;
import com.goldenrealstate.gretodo.data.model.Building;
import com.goldenrealstate.gretodo.data.model.Person;
import com.goldenrealstate.gretodo.data.model.Project;
import com.goldenrealstate.gretodo.data.repository.IBuildingRepository;
import com.goldenrealstate.gretodo.data.repository.IPersonRepository;
import com.goldenrealstate.gretodo.data.repository.IProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.Optional;

@Service
public class ProjectService implements IProjectService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private IProjectRepository projectRepository;
    @Autowired
    private IPersonRepository personRepository;
    @Autowired
    private IBuildingRepository buildingRepository;

    @Autowired
    private SecurityProvider securityProvider;

    @Override
    public Project create(ProjectRepresentation projectRep) throws InvalidNameException {
        verifyName(projectRep.getName());

        Project project = new Project(projectRep.getName());
        project.setDescription(projectRep.getDescription());
        project.setCreatedBy(securityProvider.getCurrentUser());
        project.setUpdatedBy(securityProvider.getCurrentUser());
        if (projectRep.getPersonId() != null) {
            Optional<Person> person = personRepository.findById(projectRep.getPersonId());
            person.ifPresent(project::setPerson);
        }
        if (projectRep.getBuildingId() != null) {
            Optional<Building> building = buildingRepository.findById(projectRep.getBuildingId());
            building.ifPresent(project::setBuilding);
        }

        LOGGER.debug("Creating entity: {}", project);
        return projectRepository.save(project);
    }

    @Override
    public Project update(long id, ProjectRepresentation projectRepresentation) throws IdNotFoundException {
        Optional<Project> projectOpt = projectRepository.findById(id);

        Project project = projectOpt.orElseThrow(() -> new IdNotFoundException(id));

        if (projectRepresentation.getName() != null && !projectRepresentation.getName().isEmpty())
            project.setName(projectRepresentation.getName());

        if (projectRepresentation.getDescription() != null && !projectRepresentation.getDescription().isEmpty())
            project.setDescription(projectRepresentation.getDescription());

        if (projectRepresentation.getStatus() != null)
            project.setProjectStatus(projectRepresentation.getStatus());

        if (projectRepresentation.getPersonId() != null) {
            Optional<Person> person = personRepository.findById(projectRepresentation.getPersonId());
            person.ifPresent(project::setPerson);
        }

        if (projectRepresentation.getBuildingId() != null) {
            Optional<Building> building = buildingRepository.findById(projectRepresentation.getBuildingId());
            building.ifPresent(project::setBuilding);
        }

        project.setUpdatedBy(securityProvider.getCurrentUser());

        LOGGER.debug("Updating entity: {}", project);
        return projectRepository.save(project);
    }

    @Override
    public Page<Project> findByFilter(ProjectRepresentation projectRepresentation, Pageable pageable) {
        ProjectSpecification ps = new ProjectSpecification(projectRepresentation);
        return projectRepository
                .findAll(ps, pageable);
    }

    @Override
    public Page<Project> findAll(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

    @Override
    public Project findById(long id) {
        Optional<Project> project = projectRepository.findById(id);
        return project.orElse(null);
    }

    @Override
    public void delete(long id) throws IdNotFoundException{
        if (!personRepository.existsById(id))
            throw new IdNotFoundException(id);
        LOGGER.debug("Deleting entity with id: {}", id);
        projectRepository.deleteById(id);
    }

    /**
     * Verifies if a provided name is either null or empty.
     * If the name is null or empty, an exception is thrown.
     *
     * @param name the name to ve verified
     * @throws InvalidNameException if the name is null or empty
     */
    private void verifyName(String name) throws InvalidNameException {
        if (name == null || name.isEmpty())
            throw new InvalidNameException();
    }
}
