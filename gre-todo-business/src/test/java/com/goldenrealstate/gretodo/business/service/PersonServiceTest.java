package com.goldenrealstate.gretodo.business.service;

import com.goldenrealstate.gretodo.business.exception.IdNotFoundException;
import com.goldenrealstate.gretodo.business.exception.InvalidNameException;
import com.goldenrealstate.gretodo.business.service.impl.PersonService;
import com.goldenrealstate.gretodo.data.model.Person;
import com.goldenrealstate.gretodo.data.repository.IPersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Tests the default person service {@link PersonService}
 * This should NOT spy/autowire interface services.
 *
 * @author Mathews Motta
 * @since 1.0
 */
public class PersonServiceTest {

    @InjectMocks
    @Spy
    private PersonService personService;

    @Mock
    private IPersonRepository personRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createPersonAndReturnSuccess() throws InvalidNameException {
        String personName = "Odin";
        doAnswer(inv -> inv.getArguments()[0]).when(personRepository).save(any(Person.class));
        Person result = personService.create(personName);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(personName);
    }

    @Test
    public void createPersonAndFailBecauseOfNullName() {
        doAnswer(inv -> inv.getArguments()[0]).when(personRepository).save(any(Person.class));
        assertThatThrownBy(() -> personService.create(null)).isInstanceOf(InvalidNameException.class);
    }

    @Test
    public void createPersonAndFailBecauseOfEmptyName() {
        String personName = "";
        doAnswer(inv -> inv.getArguments()[0]).when(personRepository).save(any(Person.class));
        assertThatThrownBy(() -> personService.create(personName)).isInstanceOf(InvalidNameException.class);
    }

    @Test
    public void updatePersonAndReturnSuccess() throws InvalidNameException, IdNotFoundException {
        String personName = "Thor";
        doReturn(Optional.of(mock(Person.class))).when(personRepository).findById(anyLong());
        doAnswer(inv -> inv.getArguments()[0]).when(personRepository).save(any(Person.class));
        Person result = personService.update(1L, personName);

        assertThat(result).isNotNull();
    }

    @Test
    public void updatePersonAndReturnFailBecauseOfNullName() {
        doReturn(Optional.of(mock(Person.class))).when(personRepository).findById(anyLong());
        doAnswer(inv -> inv.getArguments()[0]).when(personRepository).save(any(Person.class));
        assertThatThrownBy(() -> personService.update(1L, null)).isInstanceOf(InvalidNameException.class);
    }

    @Test
    public void updatePersonAndReturnFailBecauseOfEmptyName() {
        String personName = "";
        doReturn(Optional.of(mock(Person.class))).when(personRepository).findById(anyLong());
        doAnswer(inv -> inv.getArguments()[0]).when(personRepository).save(any(Person.class));
        assertThatThrownBy(() -> personService.update(1L, personName)).isInstanceOf(InvalidNameException.class);
    }

    @Test
    public void findAllPersonsAndReturnSuccess() {
        Person person1 = mock(Person.class);
        Person person2 = mock(Person.class);

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.ASC, "name");
        doReturn(new PageImpl<>(List.of(person1, person2), pageRequest, 10)).when(personRepository).findAll(Pageable.unpaged());

        Page<Person> results = personService.findAll(Pageable.unpaged());

        assertThat(results).isNotEmpty();
        assertThat(results.getContent()).contains(person1);
        assertThat(results.getContent()).contains(person2);
    }

    @Test
    public void findPersonsByNameAndReturnSuccess() {
        String personName = "Freyja";
        Person person = mock(Person.class);

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.ASC, "name");
        doReturn(new PageImpl<>(List.of(person), pageRequest, 10)).when(personRepository).findByName(anyString(), any());
        Page<Person> results = personService.findByName(personName, Pageable.unpaged());

        assertThat(results).isNotEmpty();
        assertThat(results.getContent()).contains(person);
    }
}
