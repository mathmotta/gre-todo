package com.goldenrealstate.gretodo.data.model;


import com.goldenrealstate.gretodo.data.model.common.EntityBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity()
@Table(name  = "person")
public class Person extends EntityBase {

    @Column(nullable = false)
    private String name;

    public Person() {}

    public Person(String name){
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}