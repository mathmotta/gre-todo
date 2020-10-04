package com.goldenrealstate.gretodo.data.filter;

import com.goldenrealstate.gretodo.common.ProjectRepresentation;
import com.goldenrealstate.gretodo.data.model.Project;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * A Specification to define custom repository filters when searching for a project by multiple columns.
 *
 * @author Mathews Motta
 * @see com.goldenrealstate.gretodo.data.model.Project
 * @since 1.0
 */
public class ProjectSpecification implements Specification<Project> {

    private final ProjectRepresentation criteria;

    /**
     * Creates a Speficiation using a provided {@link ProjectRepresentation} data container
     *
     * @param criteria the data container to be used to create a specification
     */
    public ProjectSpecification(ProjectRepresentation criteria) {
        this.criteria = criteria;
    }

    /**
     * Creates a predicate query based on given criteria.
     *
     * @param root            the generic entity element to have a criteria built upon
     * @param criteriaQuery   the criteria query to actually take effect from the built criteria
     * @param criteriaBuilder the criteria builder
     * @return the predicate restriction to be used to filter results
     */
    @Override
    public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (criteria.getName() != null)
            predicates.add(criteriaBuilder.equal(root.get("name"), criteria.getName()));
        if (criteria.getStatus() != null)
            predicates.add(criteriaBuilder.equal(root.get("projectStatus"), criteria.getStatus()));
        if (criteria.getBuildingId() != null)
            predicates.add(criteriaBuilder.equal(root.get("building").get("id"), criteria.getBuildingId()));
        if (criteria.getPersonId() != null)
            predicates.add(criteriaBuilder.equal(root.get("person").get("id"), criteria.getPersonId()));

        return criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])))
                .distinct(true).getRestriction();
    }
}
