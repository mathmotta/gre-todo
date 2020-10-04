package com.goldenrealstate.gretodo.data.repository;

import com.goldenrealstate.gretodo.data.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Defines a PersonRepository.
 * Persons use normal Repository queries.
 *
 * @author Mathews Motta
 * @since 1.0
 */
public interface IPersonRepository extends JpaRepository<Person, Long> {

    /**
     * Finds a person by a provided name and paginate the results using the provided Pageable information
     *
     * @param name     the name of the person to find
     * @param pageable the pageable information
     * @return a paginated list of persons
     */
    Page<Person> findByName(String name, Pageable pageable);
}
