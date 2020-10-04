package com.goldenrealstate.gretodo.business.service;

import com.goldenrealstate.gretodo.business.exception.IdNotFoundException;
import com.goldenrealstate.gretodo.business.exception.InvalidNameException;
import com.goldenrealstate.gretodo.common.ProjectRepresentation;
import com.goldenrealstate.gretodo.data.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * An implementation contract for any ProjectService implementer.
 *
 * @author Mathews Motta
 * @see com.goldenrealstate.gretodo.business.service.IEntityService
 * @see com.goldenrealstate.gretodo.common.ProjectRepresentation
 * @see com.goldenrealstate.gretodo.data.model.Project
 * @since 1.0
 */
public interface IProjectService extends IEntityService<Project> {
    /**
     * Creates a project using a provided {@link ProjectRepresentation} data container.
     *
     * @param project the data to be used to create a project
     * @return the created {@link Project} entity
     * @throws InvalidNameException if the provided name is invalid
     */
    Project create(ProjectRepresentation project) throws InvalidNameException;

    /**
     * Updates a project using a provided id and {@link ProjectRepresentation} data container.
     *
     * @param id                    the id of the project to be updated
     * @param projectRepresentation the data to be used to update a project
     * @return the updated {@link Project} entity
     * @throws IdNotFoundException  if the provided id is not found
     */
    Project update(long id, ProjectRepresentation projectRepresentation) throws IdNotFoundException;

    /**
     * Finds a project using a provided {@link ProjectRepresentation} data container as filter information.
     *
     * @param projectRepresentation the data to be used to find a project
     * @param pageable              pageable information to lower server loads
     * @return a list of found {@link Project} entities, paginated
     */
    Page<Project> findByFilter(ProjectRepresentation projectRepresentation, Pageable pageable);
}
