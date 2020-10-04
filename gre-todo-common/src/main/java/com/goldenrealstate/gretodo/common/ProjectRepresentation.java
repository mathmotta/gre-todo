package com.goldenrealstate.gretodo.common;

/**
 * DTO to represent Project data without using actual Entity objects.
 *
 * @author Mathews Motta
 * @since 1.0
 */
public class ProjectRepresentation extends ProjectRepresentationBase {
    private Long personId;
    private Long buildingId;

    public ProjectRepresentation() {
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }
}
