package com.goldenrealstate.gretodo.common;

/**
 * Base DTO to represent Entity and Paging data without using actual Entity objects.
 *
 * @author Mathews Motta
 * @since 1.0
 */
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

    @Override
    public String toString() {
        return "ProjectRepresentationResponse{" + super.toString() + ", person=" + person.toString() + ", building=" + building.toString() + "}";
    }
}
