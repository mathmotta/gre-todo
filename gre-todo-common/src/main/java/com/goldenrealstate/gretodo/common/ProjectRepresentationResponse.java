package com.goldenrealstate.gretodo.common;

public class ProjectRepresentationResponse extends ProjectRepresentationBase {
    private PersonRepresentation person;
    private BuildingRepresentation building;

    public ProjectRepresentationResponse () {}

    public PersonRepresentation getPerson() {
        return person;
    }

    public void setPerson(PersonRepresentation person) {
        this.person = person;
    }

    public BuildingRepresentation getBuilding() {
        return building;
    }

    public void setBuilding(BuildingRepresentation building) {
        this.building = building;
    }
}
