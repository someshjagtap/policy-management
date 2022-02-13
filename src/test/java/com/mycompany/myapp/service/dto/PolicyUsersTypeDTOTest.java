package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PolicyUsersTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PolicyUsersTypeDTO.class);
        PolicyUsersTypeDTO policyUsersTypeDTO1 = new PolicyUsersTypeDTO();
        policyUsersTypeDTO1.setId(1L);
        PolicyUsersTypeDTO policyUsersTypeDTO2 = new PolicyUsersTypeDTO();
        assertThat(policyUsersTypeDTO1).isNotEqualTo(policyUsersTypeDTO2);
        policyUsersTypeDTO2.setId(policyUsersTypeDTO1.getId());
        assertThat(policyUsersTypeDTO1).isEqualTo(policyUsersTypeDTO2);
        policyUsersTypeDTO2.setId(2L);
        assertThat(policyUsersTypeDTO1).isNotEqualTo(policyUsersTypeDTO2);
        policyUsersTypeDTO1.setId(null);
        assertThat(policyUsersTypeDTO1).isNotEqualTo(policyUsersTypeDTO2);
    }
}
