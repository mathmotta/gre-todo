package com.goldenrealstate.gretodo.business.service.impl;

import com.goldenrealstate.gretodo.business.exception.IdNotFoundException;
import com.goldenrealstate.gretodo.business.exception.InvalidNameException;
import com.goldenrealstate.gretodo.business.service.IBuildingService;
import com.goldenrealstate.gretodo.data.model.Building;
import com.goldenrealstate.gretodo.data.repository.IBuildingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.Optional;

@Service
public class BuildingService implements IBuildingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private IBuildingRepository buildingRepository;

    @Override
    public Building create(String name) throws InvalidNameException {
        verifyName(name);

        Building building = new Building(name);
        LOGGER.debug("Creating entity: {}", building);
        return buildingRepository.save(building);
    }

    @Override
    public Building update(long id, String newName) throws InvalidNameException, IdNotFoundException {
        verifyName(newName);
        Optional<Building> building = buildingRepository.findById(id);

        Building actual = building.orElseThrow(() -> new IdNotFoundException(id));

        actual.setName(newName);
        LOGGER.debug("Updating entity: {}", actual);
        return buildingRepository.save(actual);
    }

    @Override
    public Page<Building> findAll(Pageable pageable) {
        return buildingRepository.findAll(pageable);
    }

    @Override
    public Building findById(long id) {
        Optional<Building> building = buildingRepository.findById(id);
        return building.orElse(null);
    }

    @Override
    public void delete(long id) throws IdNotFoundException {
        if (!buildingRepository.existsById(id))
            throw new IdNotFoundException(id);
        LOGGER.debug("Deleting entity with id: {}", id);
        buildingRepository.deleteById(id);
    }

    @Override
    public Page<Building> findByName(String name, Pageable pageable) {
        return buildingRepository.findByName(name, pageable);
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
