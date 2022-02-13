package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.PolicyUsersTypeRepository;
import com.mycompany.myapp.service.PolicyUsersTypeQueryService;
import com.mycompany.myapp.service.PolicyUsersTypeService;
import com.mycompany.myapp.service.criteria.PolicyUsersTypeCriteria;
import com.mycompany.myapp.service.dto.PolicyUsersTypeDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.PolicyUsersType}.
 */
@RestController
@RequestMapping("/api")
public class PolicyUsersTypeResource {

    private final Logger log = LoggerFactory.getLogger(PolicyUsersTypeResource.class);

    private static final String ENTITY_NAME = "policyUsersType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PolicyUsersTypeService policyUsersTypeService;

    private final PolicyUsersTypeRepository policyUsersTypeRepository;

    private final PolicyUsersTypeQueryService policyUsersTypeQueryService;

    public PolicyUsersTypeResource(
        PolicyUsersTypeService policyUsersTypeService,
        PolicyUsersTypeRepository policyUsersTypeRepository,
        PolicyUsersTypeQueryService policyUsersTypeQueryService
    ) {
        this.policyUsersTypeService = policyUsersTypeService;
        this.policyUsersTypeRepository = policyUsersTypeRepository;
        this.policyUsersTypeQueryService = policyUsersTypeQueryService;
    }

    /**
     * {@code POST  /policy-users-types} : Create a new policyUsersType.
     *
     * @param policyUsersTypeDTO the policyUsersTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new policyUsersTypeDTO, or with status {@code 400 (Bad Request)} if the policyUsersType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/policy-users-types")
    public ResponseEntity<PolicyUsersTypeDTO> createPolicyUsersType(@Valid @RequestBody PolicyUsersTypeDTO policyUsersTypeDTO)
        throws URISyntaxException {
        log.debug("REST request to save PolicyUsersType : {}", policyUsersTypeDTO);
        if (policyUsersTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new policyUsersType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PolicyUsersTypeDTO result = policyUsersTypeService.save(policyUsersTypeDTO);
        return ResponseEntity
            .created(new URI("/api/policy-users-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /policy-users-types/:id} : Updates an existing policyUsersType.
     *
     * @param id the id of the policyUsersTypeDTO to save.
     * @param policyUsersTypeDTO the policyUsersTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated policyUsersTypeDTO,
     * or with status {@code 400 (Bad Request)} if the policyUsersTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the policyUsersTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/policy-users-types/{id}")
    public ResponseEntity<PolicyUsersTypeDTO> updatePolicyUsersType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PolicyUsersTypeDTO policyUsersTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PolicyUsersType : {}, {}", id, policyUsersTypeDTO);
        if (policyUsersTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, policyUsersTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!policyUsersTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PolicyUsersTypeDTO result = policyUsersTypeService.save(policyUsersTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, policyUsersTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /policy-users-types/:id} : Partial updates given fields of an existing policyUsersType, field will ignore if it is null
     *
     * @param id the id of the policyUsersTypeDTO to save.
     * @param policyUsersTypeDTO the policyUsersTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated policyUsersTypeDTO,
     * or with status {@code 400 (Bad Request)} if the policyUsersTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the policyUsersTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the policyUsersTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/policy-users-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PolicyUsersTypeDTO> partialUpdatePolicyUsersType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PolicyUsersTypeDTO policyUsersTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PolicyUsersType partially : {}, {}", id, policyUsersTypeDTO);
        if (policyUsersTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, policyUsersTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!policyUsersTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PolicyUsersTypeDTO> result = policyUsersTypeService.partialUpdate(policyUsersTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, policyUsersTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /policy-users-types} : get all the policyUsersTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of policyUsersTypes in body.
     */
    @GetMapping("/policy-users-types")
    public ResponseEntity<List<PolicyUsersTypeDTO>> getAllPolicyUsersTypes(
        PolicyUsersTypeCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PolicyUsersTypes by criteria: {}", criteria);
        Page<PolicyUsersTypeDTO> page = policyUsersTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /policy-users-types/count} : count all the policyUsersTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/policy-users-types/count")
    public ResponseEntity<Long> countPolicyUsersTypes(PolicyUsersTypeCriteria criteria) {
        log.debug("REST request to count PolicyUsersTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(policyUsersTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /policy-users-types/:id} : get the "id" policyUsersType.
     *
     * @param id the id of the policyUsersTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the policyUsersTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/policy-users-types/{id}")
    public ResponseEntity<PolicyUsersTypeDTO> getPolicyUsersType(@PathVariable Long id) {
        log.debug("REST request to get PolicyUsersType : {}", id);
        Optional<PolicyUsersTypeDTO> policyUsersTypeDTO = policyUsersTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(policyUsersTypeDTO);
    }

    /**
     * {@code DELETE  /policy-users-types/:id} : delete the "id" policyUsersType.
     *
     * @param id the id of the policyUsersTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/policy-users-types/{id}")
    public ResponseEntity<Void> deletePolicyUsersType(@PathVariable Long id) {
        log.debug("REST request to delete PolicyUsersType : {}", id);
        policyUsersTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
