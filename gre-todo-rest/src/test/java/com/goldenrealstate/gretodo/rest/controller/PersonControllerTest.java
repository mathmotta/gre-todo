package com.goldenrealstate.gretodo.rest.controller;


import com.goldenrealstate.gretodo.business.service.IPersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class PersonControllerTest {

    @InjectMocks
    @Spy
    private PersonController controller;

    @Mock
    private IPersonService personService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAllWithName() {
        doReturn(Page.empty()).when(personService).findByName(anyString(), any(Pageable.class));
        doReturn(Page.empty()).when(personService).findAll(any(Pageable.class));

        controller.findAll("Heimdall", Pageable.unpaged());

        verify(personService, times(1)).findByName(anyString(), any(Pageable.class));
        verify(personService, times(0)).findAll(any(Pageable.class));
    }

    @Test
    public void findAllWithoutName() {
        doReturn(Page.empty()).when(personService).findByName(anyString(), any(Pageable.class));
        doReturn(Page.empty()).when(personService).findAll(any(Pageable.class));

        controller.findAll(null, Pageable.unpaged());

        verify(personService, times(0)).findByName(anyString(), any(Pageable.class));
        verify(personService, times(1)).findAll(any(Pageable.class));
    }
}