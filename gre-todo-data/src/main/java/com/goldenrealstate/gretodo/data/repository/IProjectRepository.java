package com.goldenrealstate.gretodo.data.repository;

import com.goldenrealstate.gretodo.data.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Defines a ProjectRepository.
 * Projects use some uncommon queries such as findByPerson or findByBuilding (not yet implemented)
 *
 * @since 1.0
 * @author Mathews Motta
 */
public interface IProjectRepository extends JpaRepository<Project, Long> {

}
