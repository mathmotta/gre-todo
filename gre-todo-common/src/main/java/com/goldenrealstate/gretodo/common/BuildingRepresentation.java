package com.goldenrealstate.gretodo.common;

/**
 * Base DTO to represent Entity and Paging data without using actual Entity objects.
 *
 * @author Mathews Motta
 * @since 1.0
 */
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
