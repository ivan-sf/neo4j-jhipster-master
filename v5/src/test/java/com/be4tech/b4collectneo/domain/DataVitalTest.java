package com.be4tech.b4collectneo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4collectneo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DataVitalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataVital.class);
        DataVital dataVital1 = new DataVital();
        dataVital1.setId("id1");
        DataVital dataVital2 = new DataVital();
        dataVital2.setId(dataVital1.getId());
        assertThat(dataVital1).isEqualTo(dataVital2);
        dataVital2.setId("id2");
        assertThat(dataVital1).isNotEqualTo(dataVital2);
        dataVital1.setId(null);
        assertThat(dataVital1).isNotEqualTo(dataVital2);
    }
}
