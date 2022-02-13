package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.PolicyUsers;
import com.mycompany.myapp.service.dto.PolicyUsersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PolicyUsers} and its DTO {@link PolicyUsersDTO}.
 */
@Mapper(componentModel = "spring", uses = { PolicyUsersTypeMapper.class })
public interface PolicyUsersMapper extends EntityMapper<PolicyUsersDTO, PolicyUsers> {
    @Mapping(target = "policyUsersType", source = "policyUsersType", qualifiedByName = "id")
    PolicyUsersDTO toDto(PolicyUsers s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PolicyUsersDTO toDtoId(PolicyUsers policyUsers);
}
