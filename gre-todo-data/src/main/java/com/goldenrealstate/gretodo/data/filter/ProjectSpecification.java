package com.goldenrealstate.gretodo.data.filter;

import com.goldenrealstate.gretodo.common.ProjectRepresentation;
import com.goldenrealstate.gretodo.common.ProjectStatus;
import com.goldenrealstate.gretodo.data.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.invoke.MethodHandles;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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
    @SuppressWarnings("NullableProblems")
    @Override
    public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (criteria.getName() != null)
            predicates.add(criteriaBuilder.equal(root.get("name"), criteria.getName()));
        if (criteria.getStatus() != null)
            predicates.add(criteriaBuilder.equal(root.get("projectStatus"), ProjectStatus.fromString(criteria.getStatus())));
        if (criteria.getBuildingId() != null)
            predicates.add(criteriaBuilder.equal(root.get("building").get("id"), criteria.getBuildingId()));
        if (criteria.getPersonId() != null)
            predicates.add(criteriaBuilder.equal(root.get("person").get("id"), criteria.getPersonId()));

        if(LOGGER.isDebugEnabled()){
            StringBuilder sb = new StringBuilder();
            predicates.forEach(p -> sb.append(p.getExpressions()).append(","));
            sb.deleteCharAt(sb.length()-1);
            LOGGER.debug("Creating query with predicates: {}", sb);
        }
        return criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])))
                .distinct(true).getRestriction();
    }
}
