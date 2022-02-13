package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PolicyUsersTypeMapperTest {

    private PolicyUsersTypeMapper policyUsersTypeMapper;

    @BeforeEach
    public void setUp() {
        policyUsersTypeMapper = new PolicyUsersTypeMapperImpl();
    }
}
