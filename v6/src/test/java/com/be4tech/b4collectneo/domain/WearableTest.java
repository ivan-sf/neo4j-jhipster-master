package com.be4tech.b4collectneo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4collectneo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WearableTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Wearable.class);
        Wearable wearable1 = new Wearable();
        wearable1.setId("id1");
        Wearable wearable2 = new Wearable();
        wearable2.setId(wearable1.getId());
        assertThat(wearable1).isEqualTo(wearable2);
        wearable2.setId("id2");
        assertThat(wearable1).isNotEqualTo(wearable2);
        wearable1.setId(null);
        assertThat(wearable1).isNotEqualTo(wearable2);
    }
}
