package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.PolicyUsers;
import com.mycompany.myapp.domain.PolicyUsersType;
import com.mycompany.myapp.repository.PolicyUsersTypeRepository;
import com.mycompany.myapp.service.criteria.PolicyUsersTypeCriteria;
import com.mycompany.myapp.service.dto.PolicyUsersTypeDTO;
import com.mycompany.myapp.service.mapper.PolicyUsersTypeMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PolicyUsersTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PolicyUsersTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/policy-users-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PolicyUsersTypeRepository policyUsersTypeRepository;

    @Autowired
    private PolicyUsersTypeMapper policyUsersTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPolicyUsersTypeMockMvc;

    private PolicyUsersType policyUsersType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PolicyUsersType createEntity(EntityManager em) {
        PolicyUsersType policyUsersType = new PolicyUsersType()
            .name(DEFAULT_NAME)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return policyUsersType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PolicyUsersType createUpdatedEntity(EntityManager em) {
        PolicyUsersType policyUsersType = new PolicyUsersType()
            .name(UPDATED_NAME)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return policyUsersType;
    }

    @BeforeEach
    public void initTest() {
        policyUsersType = createEntity(em);
    }

    @Test
    @Transactional
    void createPolicyUsersType() throws Exception {
        int databaseSizeBeforeCreate = policyUsersTypeRepository.findAll().size();
        // Create the PolicyUsersType
        PolicyUsersTypeDTO policyUsersTypeDTO = policyUsersTypeMapper.toDto(policyUsersType);
        restPolicyUsersTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(policyUsersTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PolicyUsersType in the database
        List<PolicyUsersType> policyUsersTypeList = policyUsersTypeRepository.findAll();
        assertThat(policyUsersTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PolicyUsersType testPolicyUsersType = policyUsersTypeList.get(policyUsersTypeList.size() - 1);
        assertThat(testPolicyUsersType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPolicyUsersType.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testPolicyUsersType.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createPolicyUsersTypeWithExistingId() throws Exception {
        // Create the PolicyUsersType with an existing ID
        policyUsersType.setId(1L);
        PolicyUsersTypeDTO policyUsersTypeDTO = policyUsersTypeMapper.toDto(policyUsersType);

        int databaseSizeBeforeCreate = policyUsersTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPolicyUsersTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(policyUsersTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PolicyUsersType in the database
        List<PolicyUsersType> policyUsersTypeList = policyUsersTypeRepository.findAll();
        assertThat(policyUsersTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = policyUsersTypeRepository.findAll().size();
        // set the field null
        policyUsersType.setLastModified(null);

        // Create the PolicyUsersType, which fails.
        PolicyUsersTypeDTO policyUsersTypeDTO = policyUsersTypeMapper.toDto(policyUsersType);

        restPolicyUsersTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(policyUsersTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<PolicyUsersType> policyUsersTypeList = policyUsersTypeRepository.findAll();
        assertThat(policyUsersTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = policyUsersTypeRepository.findAll().size();
        // set the field null
        policyUsersType.setLastModifiedBy(null);

        // Create the PolicyUsersType, which fails.
        PolicyUsersTypeDTO policyUsersTypeDTO = policyUsersTypeMapper.toDto(policyUsersType);

        restPolicyUsersTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(policyUsersTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<PolicyUsersType> policyUsersTypeList = policyUsersTypeRepository.findAll();
        assertThat(policyUsersTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPolicyUsersTypes() throws Exception {
        // Initialize the database
        policyUsersTypeRepository.saveAndFlush(policyUsersType);

        // Get all the policyUsersTypeList
        restPolicyUsersTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(policyUsersType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getPolicyUsersType() throws Exception {
        // Initialize the database
        policyUsersTypeRepository.saveAndFlush(policyUsersType);

        // Get the policyUsersType
        restPolicyUsersTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, policyUsersType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(policyUsersType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getPolicyUsersTypesByIdFiltering() throws Exception {
        // Initialize the database
        policyUsersTypeRepository.saveAndFlush(policyUsersType);

        Long id = policyUsersType.getId();

        defaultPolicyUsersTypeShouldBeFound("id.equals=" + id);
        defaultPolicyUsersTypeShouldNotBeFound("id.notEquals=" + id);

        defaultPolicyUsersTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPolicyUsersTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultPolicyUsersTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPolicyUsersTypeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPolicyUsersTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersTypeRepository.saveAndFlush(policyUsersType);

        // Get all the policyUsersTypeList where name equals to DEFAULT_NAME
        defaultPolicyUsersTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the policyUsersTypeList where name equals to UPDATED_NAME
        defaultPolicyUsersTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPolicyUsersTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersTypeRepository.saveAndFlush(policyUsersType);

        // Get all the policyUsersTypeList where name not equals to DEFAULT_NAME
        defaultPolicyUsersTypeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the policyUsersTypeList where name not equals to UPDATED_NAME
        defaultPolicyUsersTypeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPolicyUsersTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        policyUsersTypeRepository.saveAndFlush(policyUsersType);

        // Get all the policyUsersTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPolicyUsersTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the policyUsersTypeList where name equals to UPDATED_NAME
        defaultPolicyUsersTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPolicyUsersTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyUsersTypeRepository.saveAndFlush(policyUsersType);

        // Get all the policyUsersTypeList where name is not null
        defaultPolicyUsersTypeShouldBeFound("name.specified=true");

        // Get all the policyUsersTypeList where name is null
        defaultPolicyUsersTypeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllPolicyUsersTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        policyUsersTypeRepository.saveAndFlush(policyUsersType);

        // Get all the policyUsersTypeList where name contains DEFAULT_NAME
        defaultPolicyUsersTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the policyUsersTypeList where name contains UPDATED_NAME
        defaultPolicyUsersTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPolicyUsersTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        policyUsersTypeRepository.saveAndFlush(policyUsersType);

        // Get all the policyUsersTypeList where name does not contain DEFAULT_NAME
        defaultPolicyUsersTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the policyUsersTypeList where name does not contain UPDATED_NAME
        defaultPolicyUsersTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPolicyUsersTypesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersTypeRepository.saveAndFlush(policyUsersType);

        // Get all the policyUsersTypeList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultPolicyUsersTypeShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the policyUsersTypeList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultPolicyUsersTypeShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPolicyUsersTypesByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersTypeRepository.saveAndFlush(policyUsersType);

        // Get all the policyUsersTypeList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultPolicyUsersTypeShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the policyUsersTypeList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultPolicyUsersTypeShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPolicyUsersTypesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        policyUsersTypeRepository.saveAndFlush(policyUsersType);

        // Get all the policyUsersTypeList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultPolicyUsersTypeShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the policyUsersTypeList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultPolicyUsersTypeShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPolicyUsersTypesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyUsersTypeRepository.saveAndFlush(policyUsersType);

        // Get all the policyUsersTypeList where lastModified is not null
        defaultPolicyUsersTypeShouldBeFound("lastModified.specified=true");

        // Get all the policyUsersTypeList where lastModified is null
        defaultPolicyUsersTypeShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllPolicyUsersTypesByLastModifiedContainsSomething() throws Exception {
        // Initialize the database
        policyUsersTypeRepository.saveAndFlush(policyUsersType);

        // Get all the policyUsersTypeList where lastModified contains DEFAULT_LAST_MODIFIED
        defaultPolicyUsersTypeShouldBeFound("lastModified.contains=" + DEFAULT_LAST_MODIFIED);

        // Get all the policyUsersTypeList where lastModified contains UPDATED_LAST_MODIFIED
        defaultPolicyUsersTypeShouldNotBeFound("lastModified.contains=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPolicyUsersTypesByLastModifiedNotContainsSomething() throws Exception {
        // Initialize the database
        policyUsersTypeRepository.saveAndFlush(policyUsersType);

        // Get all the policyUsersTypeList where lastModified does not contain DEFAULT_LAST_MODIFIED
        defaultPolicyUsersTypeShouldNotBeFound("lastModified.doesNotContain=" + DEFAULT_LAST_MODIFIED);

        // Get all the policyUsersTypeList where lastModified does not contain UPDATED_LAST_MODIFIED
        defaultPolicyUsersTypeShouldBeFound("lastModified.doesNotContain=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPolicyUsersTypesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersTypeRepository.saveAndFlush(policyUsersType);

        // Get all the policyUsersTypeList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultPolicyUsersTypeShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the policyUsersTypeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultPolicyUsersTypeShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPolicyUsersTypesByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersTypeRepository.saveAndFlush(policyUsersType);

        // Get all the policyUsersTypeList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultPolicyUsersTypeShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the policyUsersTypeList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultPolicyUsersTypeShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPolicyUsersTypesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        policyUsersTypeRepository.saveAndFlush(policyUsersType);

        // Get all the policyUsersTypeList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultPolicyUsersTypeShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the policyUsersTypeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultPolicyUsersTypeShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPolicyUsersTypesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyUsersTypeRepository.saveAndFlush(policyUsersType);

        // Get all the policyUsersTypeList where lastModifiedBy is not null
        defaultPolicyUsersTypeShouldBeFound("lastModifiedBy.specified=true");

        // Get all the policyUsersTypeList where lastModifiedBy is null
        defaultPolicyUsersTypeShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllPolicyUsersTypesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        policyUsersTypeRepository.saveAndFlush(policyUsersType);

        // Get all the policyUsersTypeList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultPolicyUsersTypeShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the policyUsersTypeList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultPolicyUsersTypeShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPolicyUsersTypesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        policyUsersTypeRepository.saveAndFlush(policyUsersType);

        // Get all the policyUsersTypeList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultPolicyUsersTypeShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the policyUsersTypeList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultPolicyUsersTypeShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPolicyUsersTypesByPolicyUsersIsEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersTypeRepository.saveAndFlush(policyUsersType);
        PolicyUsers policyUsers;
        if (TestUtil.findAll(em, PolicyUsers.class).isEmpty()) {
            policyUsers = PolicyUsersResourceIT.createEntity(em);
            em.persist(policyUsers);
            em.flush();
        } else {
            policyUsers = TestUtil.findAll(em, PolicyUsers.class).get(0);
        }
        em.persist(policyUsers);
        em.flush();
        policyUsersType.setPolicyUsers(policyUsers);
        policyUsers.setPolicyUsersType(policyUsersType);
        policyUsersTypeRepository.saveAndFlush(policyUsersType);
        Long policyUsersId = policyUsers.getId();

        // Get all the policyUsersTypeList where policyUsers equals to policyUsersId
        defaultPolicyUsersTypeShouldBeFound("policyUsersId.equals=" + policyUsersId);

        // Get all the policyUsersTypeList where policyUsers equals to (policyUsersId + 1)
        defaultPolicyUsersTypeShouldNotBeFound("policyUsersId.equals=" + (policyUsersId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPolicyUsersTypeShouldBeFound(String filter) throws Exception {
        restPolicyUsersTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(policyUsersType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restPolicyUsersTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPolicyUsersTypeShouldNotBeFound(String filter) throws Exception {
        restPolicyUsersTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPolicyUsersTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPolicyUsersType() throws Exception {
        // Get the policyUsersType
        restPolicyUsersTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPolicyUsersType() throws Exception {
        // Initialize the database
        policyUsersTypeRepository.saveAndFlush(policyUsersType);

        int databaseSizeBeforeUpdate = policyUsersTypeRepository.findAll().size();

        // Update the policyUsersType
        PolicyUsersType updatedPolicyUsersType = policyUsersTypeRepository.findById(policyUsersType.getId()).get();
        // Disconnect from session so that the updates on updatedPolicyUsersType are not directly saved in db
        em.detach(updatedPolicyUsersType);
        updatedPolicyUsersType.name(UPDATED_NAME).lastModified(UPDATED_LAST_MODIFIED).lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        PolicyUsersTypeDTO policyUsersTypeDTO = policyUsersTypeMapper.toDto(updatedPolicyUsersType);

        restPolicyUsersTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, policyUsersTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(policyUsersTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the PolicyUsersType in the database
        List<PolicyUsersType> policyUsersTypeList = policyUsersTypeRepository.findAll();
        assertThat(policyUsersTypeList).hasSize(databaseSizeBeforeUpdate);
        PolicyUsersType testPolicyUsersType = policyUsersTypeList.get(policyUsersTypeList.size() - 1);
        assertThat(testPolicyUsersType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPolicyUsersType.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testPolicyUsersType.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingPolicyUsersType() throws Exception {
        int databaseSizeBeforeUpdate = policyUsersTypeRepository.findAll().size();
        policyUsersType.setId(count.incrementAndGet());

        // Create the PolicyUsersType
        PolicyUsersTypeDTO policyUsersTypeDTO = policyUsersTypeMapper.toDto(policyUsersType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPolicyUsersTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, policyUsersTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(policyUsersTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PolicyUsersType in the database
        List<PolicyUsersType> policyUsersTypeList = policyUsersTypeRepository.findAll();
        assertThat(policyUsersTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPolicyUsersType() throws Exception {
        int databaseSizeBeforeUpdate = policyUsersTypeRepository.findAll().size();
        policyUsersType.setId(count.incrementAndGet());

        // Create the PolicyUsersType
        PolicyUsersTypeDTO policyUsersTypeDTO = policyUsersTypeMapper.toDto(policyUsersType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPolicyUsersTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(policyUsersTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PolicyUsersType in the database
        List<PolicyUsersType> policyUsersTypeList = policyUsersTypeRepository.findAll();
        assertThat(policyUsersTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPolicyUsersType() throws Exception {
        int databaseSizeBeforeUpdate = policyUsersTypeRepository.findAll().size();
        policyUsersType.setId(count.incrementAndGet());

        // Create the PolicyUsersType
        PolicyUsersTypeDTO policyUsersTypeDTO = policyUsersTypeMapper.toDto(policyUsersType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPolicyUsersTypeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(policyUsersTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PolicyUsersType in the database
        List<PolicyUsersType> policyUsersTypeList = policyUsersTypeRepository.findAll();
        assertThat(policyUsersTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePolicyUsersTypeWithPatch() throws Exception {
        // Initialize the database
        policyUsersTypeRepository.saveAndFlush(policyUsersType);

        int databaseSizeBeforeUpdate = policyUsersTypeRepository.findAll().size();

        // Update the policyUsersType using partial update
        PolicyUsersType partialUpdatedPolicyUsersType = new PolicyUsersType();
        partialUpdatedPolicyUsersType.setId(policyUsersType.getId());

        partialUpdatedPolicyUsersType.name(UPDATED_NAME);

        restPolicyUsersTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPolicyUsersType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPolicyUsersType))
            )
            .andExpect(status().isOk());

        // Validate the PolicyUsersType in the database
        List<PolicyUsersType> policyUsersTypeList = policyUsersTypeRepository.findAll();
        assertThat(policyUsersTypeList).hasSize(databaseSizeBeforeUpdate);
        PolicyUsersType testPolicyUsersType = policyUsersTypeList.get(policyUsersTypeList.size() - 1);
        assertThat(testPolicyUsersType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPolicyUsersType.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testPolicyUsersType.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdatePolicyUsersTypeWithPatch() throws Exception {
        // Initialize the database
        policyUsersTypeRepository.saveAndFlush(policyUsersType);

        int databaseSizeBeforeUpdate = policyUsersTypeRepository.findAll().size();

        // Update the policyUsersType using partial update
        PolicyUsersType partialUpdatedPolicyUsersType = new PolicyUsersType();
        partialUpdatedPolicyUsersType.setId(policyUsersType.getId());

        partialUpdatedPolicyUsersType.name(UPDATED_NAME).lastModified(UPDATED_LAST_MODIFIED).lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restPolicyUsersTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPolicyUsersType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPolicyUsersType))
            )
            .andExpect(status().isOk());

        // Validate the PolicyUsersType in the database
        List<PolicyUsersType> policyUsersTypeList = policyUsersTypeRepository.findAll();
        assertThat(policyUsersTypeList).hasSize(databaseSizeBeforeUpdate);
        PolicyUsersType testPolicyUsersType = policyUsersTypeList.get(policyUsersTypeList.size() - 1);
        assertThat(testPolicyUsersType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPolicyUsersType.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testPolicyUsersType.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingPolicyUsersType() throws Exception {
        int databaseSizeBeforeUpdate = policyUsersTypeRepository.findAll().size();
        policyUsersType.setId(count.incrementAndGet());

        // Create the PolicyUsersType
        PolicyUsersTypeDTO policyUsersTypeDTO = policyUsersTypeMapper.toDto(policyUsersType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPolicyUsersTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, policyUsersTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(policyUsersTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PolicyUsersType in the database
        List<PolicyUsersType> policyUsersTypeList = policyUsersTypeRepository.findAll();
        assertThat(policyUsersTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPolicyUsersType() throws Exception {
        int databaseSizeBeforeUpdate = policyUsersTypeRepository.findAll().size();
        policyUsersType.setId(count.incrementAndGet());

        // Create the PolicyUsersType
        PolicyUsersTypeDTO policyUsersTypeDTO = policyUsersTypeMapper.toDto(policyUsersType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPolicyUsersTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(policyUsersTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PolicyUsersType in the database
        List<PolicyUsersType> policyUsersTypeList = policyUsersTypeRepository.findAll();
        assertThat(policyUsersTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPolicyUsersType() throws Exception {
        int databaseSizeBeforeUpdate = policyUsersTypeRepository.findAll().size();
        policyUsersType.setId(count.incrementAndGet());

        // Create the PolicyUsersType
        PolicyUsersTypeDTO policyUsersTypeDTO = policyUsersTypeMapper.toDto(policyUsersType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPolicyUsersTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(policyUsersTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PolicyUsersType in the database
        List<PolicyUsersType> policyUsersTypeList = policyUsersTypeRepository.findAll();
        assertThat(policyUsersTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePolicyUsersType() throws Exception {
        // Initialize the database
        policyUsersTypeRepository.saveAndFlush(policyUsersType);

        int databaseSizeBeforeDelete = policyUsersTypeRepository.findAll().size();

        // Delete the policyUsersType
        restPolicyUsersTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, policyUsersType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PolicyUsersType> policyUsersTypeList = policyUsersTypeRepository.findAll();
        assertThat(policyUsersTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
