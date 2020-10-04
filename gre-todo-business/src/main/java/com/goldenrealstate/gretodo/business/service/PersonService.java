package com.goldenrealstate.gretodo.business.service;

import com.goldenrealstate.gretodo.data.model.Person;
import com.goldenrealstate.gretodo.data.repository.IPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public class PersonService implements IPersonService {

    @Autowired
    private IPersonRepository personRepository;

    @Override
    public Person create(String name) {
        verifyName(name);

        Person person = new Person(name);
        return personRepository.save(person);
    }

    @Override
    public Person update(long id, String newName) {
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
    public void delete(long id) {
        if(!personRepository.existsById(id))
            throw new IdNotFoundException(id);
        personRepository.deleteById(id);
    }

    @Override
    public Page<Person> findByName(String name, Pageable pageable) {
        return personRepository.findByName(name, pageable);
    }

    private void verifyName(String name){
        if(name == null || name.isEmpty())
            throw new InvalidNameException();
    }
}
