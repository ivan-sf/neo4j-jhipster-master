package com.be4tech.b4collectneo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4collectneo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserCollectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserCollect.class);
        UserCollect userCollect1 = new UserCollect();
        userCollect1.setId("id1");
        UserCollect userCollect2 = new UserCollect();
        userCollect2.setId(userCollect1.getId());
        assertThat(userCollect1).isEqualTo(userCollect2);
        userCollect2.setId("id2");
        assertThat(userCollect1).isNotEqualTo(userCollect2);
        userCollect1.setId(null);
        assertThat(userCollect1).isNotEqualTo(userCollect2);
    }
}
