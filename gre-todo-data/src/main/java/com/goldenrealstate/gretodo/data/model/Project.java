package com.goldenrealstate.gretodo.data.model;

import com.goldenrealstate.gretodo.common.ProjectStatus;
import com.goldenrealstate.gretodo.data.model.common.AuditableEntity;
import com.goldenrealstate.gretodo.data.model.common.EntityBase;

import javax.persistence.*;

/**
 * The {@link Project} entity holds information about a project.
 * It's an auditable class to keep track of project's history and thus reuses generic auditable entity information from {@link AuditableEntity}
 * It reuses generic entity information(such as id) from {@link EntityBase}
 * A project can have a name and description and can be assigned to a person and a building.
 *
 * @see com.goldenrealstate.gretodo.data.model.common.AuditableEntity
 * @see com.goldenrealstate.gretodo.data.model.common.EntityBase
 * @since 1.0
 * @author Mathews Motta
 */
@Entity()
@Table(name  = "project")
public class Project extends AuditableEntity {

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "project_status", nullable = false)
    private ProjectStatus projectStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="building_id")
    private Building building;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="person_id")
    private Person person;

    public Project () {
    }

    public Project (String name){
        setName(name);
        setProjectStatus(ProjectStatus.NEW);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}
