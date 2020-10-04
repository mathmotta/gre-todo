package com.goldenrealstate.gretodo.common;

public class BuildingRepresentation extends AbstractResultRepresentation {
    private Long id;
    private String name;

    public BuildingRepresentation(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
