package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PolicyUsersTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PolicyUsersType.class);
        PolicyUsersType policyUsersType1 = new PolicyUsersType();
        policyUsersType1.setId(1L);
        PolicyUsersType policyUsersType2 = new PolicyUsersType();
        policyUsersType2.setId(policyUsersType1.getId());
        assertThat(policyUsersType1).isEqualTo(policyUsersType2);
        policyUsersType2.setId(2L);
        assertThat(policyUsersType1).isNotEqualTo(policyUsersType2);
        policyUsersType1.setId(null);
        assertThat(policyUsersType1).isNotEqualTo(policyUsersType2);
    }
}
