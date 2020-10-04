package com.goldenrealstate.gretodo.data.model;


import com.goldenrealstate.gretodo.data.model.common.EntityBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity()
@Table(name  = "building")
public class Building extends EntityBase {

    @Column(nullable = false)
    private String name;

    public Building () {}

    public Building(String name){
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}