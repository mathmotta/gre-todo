package com.goldenrealstate.gretodo.business.service;

import com.goldenrealstate.gretodo.business.exception.IdNotFoundException;
import com.goldenrealstate.gretodo.business.exception.InvalidNameException;
import com.goldenrealstate.gretodo.data.model.Building;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * An implementation contract for any BuildingService implementer.
 *
 * @author Mathews Motta
 * @see com.goldenrealstate.gretodo.business.service.IEntityService
 * @see com.goldenrealstate.gretodo.data.model.Building
 * @since 1.0
 */
public interface IBuildingService extends IEntityService<Building> {
    /**
     * Creates a building using a provided name.
     *
     * @param name the name of the building
     * @return a newly created building
     * @throws InvalidNameException if the provided name is invalid
     */
    Building create(String name) throws InvalidNameException;

    /**
     * Updates a building using a provided id and name.
     *
     * @param id      the id of the building to be updated
     * @param newName the new name for the building
     * @return the updated building
     * @throws InvalidNameException if the provided name is invalid
     * @throws IdNotFoundException  if the provided id is not found
     */
    Building update(long id, String newName) throws InvalidNameException, IdNotFoundException;

    /**
     * Finds a building using a provided name and paging information
     *
     * @param name     the building name to be found
     * @param pageable the paging information to retrieve from server
     * @return a paginated list of building
     */
    Page<Building> findByName(String name, Pageable pageable);
}
