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

    Page<Person> findByName(String name, Pageable pageable);
}
