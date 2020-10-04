package com.goldenrealstate.gretodo.data.repository;

import com.goldenrealstate.gretodo.data.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Defines a ProjectRepository.
 * Projects use some uncommon queries such as findByPerson or findByBuilding (not yet implemented)
 *
 * @author Mathews Motta
 * @since 1.0
 */
public interface IProjectRepository extends JpaRepository<Project, Long> {

}
