package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PolicyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Policy.class);
        Policy policy1 = new Policy();
        policy1.setId(1L);
        Policy policy2 = new Policy();
        policy2.setId(policy1.getId());
        assertThat(policy1).isEqualTo(policy2);
        policy2.setId(2L);
        assertThat(policy1).isNotEqualTo(policy2);
        policy1.setId(null);
        assertThat(policy1).isNotEqualTo(policy2);
    }
}
