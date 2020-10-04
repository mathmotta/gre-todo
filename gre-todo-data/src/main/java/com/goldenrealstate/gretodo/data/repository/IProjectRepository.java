package com.goldenrealstate.gretodo.data.repository;

import com.goldenrealstate.gretodo.data.filter.ProjectSpecification;
import com.goldenrealstate.gretodo.common.ProjectRepresentation;
import com.goldenrealstate.gretodo.data.model.Project;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Defines a ProjectRepository.
 * Projects use custom filtered queries defined in {@link ProjectSpecification} using a provided {@link ProjectRepresentation}
 *
 * @author Mathews Motta
 * @see com.goldenrealstate.gretodo.data.filter.ProjectSpecification
 * @see com.goldenrealstate.gretodo.common.ProjectRepresentation
 * @since 1.0
 */
public interface IProjectRepository extends PagingAndSortingRepository<Project, Long>, JpaSpecificationExecutor<Project> {
}
