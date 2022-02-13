package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.PolicyUsersType;
import com.mycompany.myapp.repository.PolicyUsersTypeRepository;
import com.mycompany.myapp.service.dto.PolicyUsersTypeDTO;
import com.mycompany.myapp.service.mapper.PolicyUsersTypeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PolicyUsersType}.
 */
@Service
@Transactional
public class PolicyUsersTypeService {

    private final Logger log = LoggerFactory.getLogger(PolicyUsersTypeService.class);

    private final PolicyUsersTypeRepository policyUsersTypeRepository;

    private final PolicyUsersTypeMapper policyUsersTypeMapper;

    public PolicyUsersTypeService(PolicyUsersTypeRepository policyUsersTypeRepository, PolicyUsersTypeMapper policyUsersTypeMapper) {
        this.policyUsersTypeRepository = policyUsersTypeRepository;
        this.policyUsersTypeMapper = policyUsersTypeMapper;
    }

    /**
     * Save a policyUsersType.
     *
     * @param policyUsersTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public PolicyUsersTypeDTO save(PolicyUsersTypeDTO policyUsersTypeDTO) {
        log.debug("Request to save PolicyUsersType : {}", policyUsersTypeDTO);
        PolicyUsersType policyUsersType = policyUsersTypeMapper.toEntity(policyUsersTypeDTO);
        policyUsersType = policyUsersTypeRepository.save(policyUsersType);
        return policyUsersTypeMapper.toDto(policyUsersType);
    }

    /**
     * Partially update a policyUsersType.
     *
     * @param policyUsersTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PolicyUsersTypeDTO> partialUpdate(PolicyUsersTypeDTO policyUsersTypeDTO) {
        log.debug("Request to partially update PolicyUsersType : {}", policyUsersTypeDTO);

        return policyUsersTypeRepository
            .findById(policyUsersTypeDTO.getId())
            .map(existingPolicyUsersType -> {
                policyUsersTypeMapper.partialUpdate(existingPolicyUsersType, policyUsersTypeDTO);

                return existingPolicyUsersType;
            })
            .map(policyUsersTypeRepository::save)
            .map(policyUsersTypeMapper::toDto);
    }

    /**
     * Get all the policyUsersTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PolicyUsersTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PolicyUsersTypes");
        return policyUsersTypeRepository.findAll(pageable).map(policyUsersTypeMapper::toDto);
    }

    /**
     *  Get all the policyUsersTypes where PolicyUsers is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PolicyUsersTypeDTO> findAllWherePolicyUsersIsNull() {
        log.debug("Request to get all policyUsersTypes where PolicyUsers is null");
        return StreamSupport
            .stream(policyUsersTypeRepository.findAll().spliterator(), false)
            .filter(policyUsersType -> policyUsersType.getPolicyUsers() == null)
            .map(policyUsersTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one policyUsersType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PolicyUsersTypeDTO> findOne(Long id) {
        log.debug("Request to get PolicyUsersType : {}", id);
        return policyUsersTypeRepository.findById(id).map(policyUsersTypeMapper::toDto);
    }

    /**
     * Delete the policyUsersType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PolicyUsersType : {}", id);
        policyUsersTypeRepository.deleteById(id);
    }
}
