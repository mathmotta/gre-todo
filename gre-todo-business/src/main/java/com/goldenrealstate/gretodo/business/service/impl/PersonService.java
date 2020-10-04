package com.goldenrealstate.gretodo.business.service.impl;

import com.goldenrealstate.gretodo.business.exception.IdNotFoundException;
import com.goldenrealstate.gretodo.business.exception.InvalidNameException;
import com.goldenrealstate.gretodo.business.service.IPersonService;
import com.goldenrealstate.gretodo.data.model.Person;
import com.goldenrealstate.gretodo.data.repository.IPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * A default implementer of {@link IPersonService}.
 *
 * @author Mathews Motta
 * @see com.goldenrealstate.gretodo.business.service.IPersonService
 * @see com.goldenrealstate.gretodo.business.service.IEntityService
 * @since 1.0
 */
public class PersonService implements IPersonService {

    @Autowired
    private IPersonRepository personRepository;

    @Override
    public Person create(String name) throws InvalidNameException {
        verifyName(name);

        Person person = new Person(name);
        return personRepository.save(person);
    }

    @Override
    public Person update(long id, String newName) throws IdNotFoundException, InvalidNameException {
        verifyName(newName);
        Optional<Person> person = personRepository.findById(id);

        Person actual = person.orElseThrow(() -> new IdNotFoundException(id));
        actual.setName(newName);
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
        if (!personRepository.existsById(id))
            throw new IdNotFoundException(id);
        personRepository.deleteById(id);
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
