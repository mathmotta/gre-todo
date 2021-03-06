package com.goldenrealstate.gretodo.data.model;


import com.goldenrealstate.gretodo.common.BuildingRepresentation;
import com.goldenrealstate.gretodo.data.model.common.EntityBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The {@link Building} entity holds information about a building and reuses generic entity information from {@link EntityBase}
 *
 * @author Mathews Motta
 * @see com.goldenrealstate.gretodo.data.model.common.EntityBase
 * @since 1.0
 */
@Entity()
@Table(name = "building")
public class Building extends EntityBase {

    @Column(nullable = false)
    private String name;

    public Building() {
    }

    public Building(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BuildingRepresentation toDto(){
        BuildingRepresentation br = new BuildingRepresentation();
        br.setId(getId());
        br.setName(getName());
        return br;
    }
}