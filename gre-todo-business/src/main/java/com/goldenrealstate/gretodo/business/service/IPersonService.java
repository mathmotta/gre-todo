package com.goldenrealstate.gretodo.business.service;

import com.goldenrealstate.gretodo.business.exception.IdNotFoundException;
import com.goldenrealstate.gretodo.business.exception.InvalidNameException;
import com.goldenrealstate.gretodo.data.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * An implementation contract for any PersonService implementer.
 *
 * @author Mathews Motta
 * @see com.goldenrealstate.gretodo.business.service.IEntityService
 * @see com.goldenrealstate.gretodo.data.model.Person
 * @since 1.0
 */
public interface IPersonService extends IEntityService<Person> {
    /**
     * Creates a user using a provided name.
     *
     * @param name the name of the user
     * @return a newly created user
     * @throws InvalidNameException if the provided name is invalid
     */
    Person create(String name) throws InvalidNameException;

    /**
     * Updates a user using a provided id and name.
     *
     * @param id      the id of the user to be updated
     * @param newName the new name for the user
     * @return the updated user
     * @throws InvalidNameException if the provided name is invalid
     * @throws IdNotFoundException  if the provided id is not found
     */
    Person update(long id, String newName) throws InvalidNameException, IdNotFoundException;

    /**
     * Finds a user using a provided name and paging information
     *
     * @param name     the user name to be found
     * @param pageable the paging information to retrieve from server
     * @return a paginated list of users
     */
    Page<Person> findByName(String name, Pageable pageable);
}
