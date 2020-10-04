package com.goldenrealstate.gretodo.business.service;

import com.goldenrealstate.gretodo.data.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPersonService extends IEntityService<Person> {
    Person create(String name);

    Person update(long id, String newName);

    Page<Person> findByName(String name, Pageable pageable);
}
