package com.goldenrealstate.gretodo.business.service.impl;

import com.goldenrealstate.gretodo.business.exception.IdNotFoundException;
import com.goldenrealstate.gretodo.business.exception.InvalidNameException;
import com.goldenrealstate.gretodo.business.service.IBuildingService;
import com.goldenrealstate.gretodo.data.model.Building;
import com.goldenrealstate.gretodo.data.repository.IBuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BuildingService implements IBuildingService {

    @Autowired
    private IBuildingRepository buildingRepository;

    @Override
    public Building create(String name) throws InvalidNameException{
        verifyName(name);

        Building building = new Building(name);
        return buildingRepository.save(building);
    }

    @Override
    public Building update(long id, String newName) throws InvalidNameException, IdNotFoundException{
        verifyName(newName);
        Optional<Building> building = buildingRepository.findById(id);

        Building actual;
        if(building.isPresent())
            actual = building.get();
        else
            throw new IdNotFoundException(id);

        actual.setName(newName);
        return buildingRepository.save(actual);
    }

    @Override
    public Page<Building> findAll(Pageable pageable) {
        return buildingRepository.findAll(pageable);
    }

    @Override
    public Building findById(long id) {
        Optional<Building> building = buildingRepository.findById(id);
        if(building.isEmpty())
            return null;
        return building.get();
    }

    @Override
    public void delete(long id) {
        buildingRepository.deleteById(id);
    }

    @Override
    public Page<Building> findByName(String name, Pageable pageable) {
        return buildingRepository.findByName(name, pageable);
    }

    private void verifyName(String name) throws InvalidNameException{
        if(name == null || name.isEmpty())
            throw new InvalidNameException();
    }
}
