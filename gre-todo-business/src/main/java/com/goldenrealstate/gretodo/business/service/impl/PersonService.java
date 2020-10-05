package com.goldenrealstate.gretodo.business.service.impl;

import com.goldenrealstate.gretodo.business.exception.IdNotFoundException;
import com.goldenrealstate.gretodo.business.exception.InvalidNameException;
import com.goldenrealstate.gretodo.business.service.IPersonService;
import com.goldenrealstate.gretodo.data.model.Person;
import com.goldenrealstate.gretodo.data.repository.IPersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.Optional;

/**
 * A default implementer of {@link IPersonService}.
 *
 * @author Mathews Motta
 * @see com.goldenrealstate.gretodo.business.service.IPersonService
 * @see com.goldenrealstate.gretodo.business.service.IEntityService
 * @since 1.0
 */
@Service
public class PersonService implements IPersonService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private IPersonRepository personRepository;

    @Override
    public Person create(String name) throws InvalidNameException {
        verifyName(name);

        Person person = new Person(name);
        LOGGER.debug("Creating entity: {}", person);
        return personRepository.save(person);
    }

    @Override
    public Person update(long id, String newName) throws IdNotFoundException, InvalidNameException {
        verifyName(newName);
        Optional<Person> person = personRepository.findById(id);

        Person actual = person.orElseThrow(() -> new IdNotFoundException(id));
        actual.setName(newName);
        LOGGER.debug("Updating entity: {}", actual);
        return personRepository.save(actual);
    }

    @Override
    public Page<Person> findAll(Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    @Override
    public Person findById(long id) {
        Optional<Person> person = personRepository.findById(id);
        return person.orElse(null);
    }

    @Override
    public void delete(long id) throws IdNotFoundException {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent()) {
            LOGGER.debug("Deleting entity with id: {}", id);
            personRepository.deleteById(id);
            return;
        }
        throw new IdNotFoundException(id);
    }

    @Override
    public Page<Person> findByName(String name, Pageable pageable) {
        return personRepository.findByName(name, pageable);
    }

    /**
     * Verifies if a provided name is either null or empty.
     * If the name is null or empty, an exception is thrown.
     *
     * @param name the name to ve verified
     * @throws InvalidNameException if the name is null or empty
     */
    private void verifyName(String name) throws InvalidNameException {
        if (name == null || name.isEmpty())
            throw new InvalidNameException();
    }
}
