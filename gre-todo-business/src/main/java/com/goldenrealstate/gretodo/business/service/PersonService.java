package com.goldenrealstate.gretodo.business.service;

import com.goldenrealstate.gretodo.data.model.Person;
import com.goldenrealstate.gretodo.data.repository.IPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class PersonService implements IPersonService {

    @Autowired
    private IPersonRepository personRepository;

    @Override
    public Person create(String name) {
        if(name == null || name.isEmpty())
            throw new InvalidNameException();

        Person person = new Person(name);
        return personRepository.save(person);
    }
}
