package com.goldenrealstate.gretodo.data.repository;

import com.goldenrealstate.gretodo.data.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Defines a BuildingRepository.
 * Buildings use normal Repository queries.
 *
 * @author Mathews Motta
 * @since 1.0
 */
public interface IBuildingRepository extends JpaRepository<Building, Long> {

}
