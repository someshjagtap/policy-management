package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AgencyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Agency.class);
        Agency agency1 = new Agency();
        agency1.setId(1L);
        Agency agency2 = new Agency();
        agency2.setId(agency1.getId());
        assertThat(agency1).isEqualTo(agency2);
        agency2.setId(2L);
        assertThat(agency1).isNotEqualTo(agency2);
        agency1.setId(null);
        assertThat(agency1).isNotEqualTo(agency2);
    }
}
