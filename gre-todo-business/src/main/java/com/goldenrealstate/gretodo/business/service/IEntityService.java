package com.goldenrealstate.gretodo.business.service;

import com.goldenrealstate.gretodo.data.model.common.EntityBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEntityService<T extends EntityBase> {

    Page<T> findAll(Pageable pageable);
    /**
     * Find a query format based on the provided ID
     * @param id - the ID to look for
     * @return query format
     */
    T findById(long id);

    /**
     * Deletes a query format
     * @param id - the ID of the query format to delete
     */
    void delete(long id);

}
