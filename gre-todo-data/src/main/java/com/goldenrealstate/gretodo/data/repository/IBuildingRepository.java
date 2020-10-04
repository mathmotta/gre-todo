package com.goldenrealstate.gretodo.data.repository;

import com.goldenrealstate.gretodo.data.model.Building;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Defines a BuildingRepository.
 * Buildings use normal Repository queries.
 *
 * @author Mathews Motta
 * @since 1.0
 */
public interface IBuildingRepository extends JpaRepository<Building, Long> {

    /**
     * Finds a building by a provided name and paginate the results using the provided Pageable information
     *
     * @param name     the name of the building to find
     * @param pageable the pageable information
     * @return a paginated list of buildings
     */
    Page<Building> findByName(String name, Pageable pageable);
}
