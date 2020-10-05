package com.goldenrealstate.gretodo.common;

import java.io.Serializable;

/**
 * DTO to represent Project data without using actual Entity objects.
 *
 * @author Mathews Motta
 * @since 1.0
 */
public class ProjectRepresentation extends ProjectRepresentationBase implements Serializable {
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

    @Override
    public String toString() {
        return "ProjectRepresentation{" + super.toString() + ", personId='" + personId + "', buildingId='" + buildingId + "'}";
    }
}
