package com.goldenrealstate.gretodo.common;

/**
 * DTO to represent Project data without using actual Entity objects.
 *
 * @author Mathews Motta
 * @since 1.0
 */
public class ProjectRepresentationBase extends AbstractResultRepresentation {
    protected Long id;
    protected String name;
    protected String description;
    protected ProjectStatus status;

    public ProjectRepresentationBase() {
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

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "id=" + id + "', " + "name='" + name + "', " + "description='" + description + "', " + "status='" + status + "'";
    }
}
