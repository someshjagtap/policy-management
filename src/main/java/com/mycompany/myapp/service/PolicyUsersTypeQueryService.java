package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.PolicyUsersType;
import com.mycompany.myapp.repository.PolicyUsersTypeRepository;
import com.mycompany.myapp.service.criteria.PolicyUsersTypeCriteria;
import com.mycompany.myapp.service.dto.PolicyUsersTypeDTO;
import com.mycompany.myapp.service.mapper.PolicyUsersTypeMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link PolicyUsersType} entities in the database.
 * The main input is a {@link PolicyUsersTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PolicyUsersTypeDTO} or a {@link Page} of {@link PolicyUsersTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PolicyUsersTypeQueryService extends QueryService<PolicyUsersType> {

    private final Logger log = LoggerFactory.getLogger(PolicyUsersTypeQueryService.class);

    private final PolicyUsersTypeRepository policyUsersTypeRepository;

    private final PolicyUsersTypeMapper policyUsersTypeMapper;

    public PolicyUsersTypeQueryService(PolicyUsersTypeRepository policyUsersTypeRepository, PolicyUsersTypeMapper policyUsersTypeMapper) {
        this.policyUsersTypeRepository = policyUsersTypeRepository;
        this.policyUsersTypeMapper = policyUsersTypeMapper;
    }

    /**
     * Return a {@link List} of {@link PolicyUsersTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PolicyUsersTypeDTO> findByCriteria(PolicyUsersTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PolicyUsersType> specification = createSpecification(criteria);
        return policyUsersTypeMapper.toDto(policyUsersTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PolicyUsersTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PolicyUsersTypeDTO> findByCriteria(PolicyUsersTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PolicyUsersType> specification = createSpecification(criteria);
        return policyUsersTypeRepository.findAll(specification, page).map(policyUsersTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PolicyUsersTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PolicyUsersType> specification = createSpecification(criteria);
        return policyUsersTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link PolicyUsersTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PolicyUsersType> createSpecification(PolicyUsersTypeCriteria criteria) {
        Specification<PolicyUsersType> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PolicyUsersType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), PolicyUsersType_.name));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModified(), PolicyUsersType_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), PolicyUsersType_.lastModifiedBy));
            }
            if (criteria.getPolicyUsersId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPolicyUsersId(),
                            root -> root.join(PolicyUsersType_.policyUsers, JoinType.LEFT).get(PolicyUsers_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
