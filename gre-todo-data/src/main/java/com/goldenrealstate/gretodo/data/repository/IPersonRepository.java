package com.goldenrealstate.gretodo.data.repository;

import com.goldenrealstate.gretodo.data.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Defines a PersonRepository.
 * Persons use normal Repository queries.
 *
 * @since 1.0
 * @author Mathews Motta
 */
public interface IPersonRepository extends JpaRepository<Person, Long> {

}