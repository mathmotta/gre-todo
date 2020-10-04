package com.goldenrealstate.gretodo.business.service;

import com.goldenrealstate.gretodo.data.model.Person;
import com.goldenrealstate.gretodo.data.repository.IPersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

public class PersonServiceTest {

    @InjectMocks
    @Spy
    private PersonService personService;

    @Mock
    private IPersonRepository personRepository;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createPersonAndReturnSuccess() {
        String personName = "Odin";
        doAnswer(inv -> inv.getArguments()[0]).when(personRepository).save(any(Person.class));
        Person result = personService.create(personName);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(personName);
    }

    @Test
    public void createPersonAndFailBecauseOfNullName(){
        doAnswer(inv -> inv.getArguments()[0]).when(personRepository).save(any(Person.class));
        assertThatThrownBy(() -> personService.create(null)).isInstanceOf(InvalidNameException.class);
    }

    @Test
    public void createPersonAndFailBecauseOfEmptyName(){
        String personName = "";
        doAnswer(inv -> inv.getArguments()[0]).when(personRepository).save(any(Person.class));
        assertThatThrownBy(() -> personService.create(personName)).isInstanceOf(InvalidNameException.class);
    }
}
