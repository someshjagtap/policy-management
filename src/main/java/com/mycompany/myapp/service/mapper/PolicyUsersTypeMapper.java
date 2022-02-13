package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.PolicyUsersType;
import com.mycompany.myapp.service.dto.PolicyUsersTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PolicyUsersType} and its DTO {@link PolicyUsersTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PolicyUsersTypeMapper extends EntityMapper<PolicyUsersTypeDTO, PolicyUsersType> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PolicyUsersTypeDTO toDtoId(PolicyUsersType policyUsersType);
}
