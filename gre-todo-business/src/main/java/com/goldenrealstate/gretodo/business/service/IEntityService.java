package com.goldenrealstate.gretodo.business.service;

import com.goldenrealstate.gretodo.business.exception.IdNotFoundException;
import com.goldenrealstate.gretodo.data.model.common.EntityBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Generic operations to be implemented by any service that provide queries or mutation/changes of sub-classes of {@link EntityBase}
 *
 * @param <T> a type that extends {@link EntityBase}
 * @author Mathews Motta
 * @see com.goldenrealstate.gretodo.data.model.common.EntityBase
 * @since 1.0
 */
public interface IEntityService<T extends EntityBase> {
    /**
     * Finds all entities using paging information.
     *
     * @param pageable the paging information to retrieve from server
     * @return a paginated list of entities that extends {@link EntityBase}
     */
    Page<T> findAll(Pageable pageable);

    /**
     * Find a single entity based on the provided ID
     *
     * @param id the id to look for
     * @return a single entity that extends {@link EntityBase}
     */
    T findById(long id);

    /**
     * Deletes a single entity with the provided ID
     *
     * @param id the id to be deleted
     * @throws IdNotFoundException if the provided id is not found
     */
    void delete(long id) throws IdNotFoundException;
}
