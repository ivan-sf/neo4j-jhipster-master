package com.be4tech.b4collectneo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4collectneo.IntegrationTest;
import com.be4tech.b4collectneo.domain.UserCollect;
import com.be4tech.b4collectneo.repository.UserCollectRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link UserCollectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserCollectResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_VITAL_KEY = "AAAAAAAAAA";
    private static final String UPDATED_VITAL_KEY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/user-collects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private UserCollectRepository userCollectRepository;

    @Autowired
    private MockMvc restUserCollectMockMvc;

    private UserCollect userCollect;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserCollect createEntity() {
        UserCollect userCollect = new UserCollect()
            .name(DEFAULT_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .username(DEFAULT_USERNAME)
            .vitalKey(DEFAULT_VITAL_KEY);
        return userCollect;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserCollect createUpdatedEntity() {
        UserCollect userCollect = new UserCollect()
            .name(UPDATED_NAME)
            .lastName(UPDATED_LAST_NAME)
            .username(UPDATED_USERNAME)
            .vitalKey(UPDATED_VITAL_KEY);
        return userCollect;
    }

    @BeforeEach
    public void initTest() {
        userCollectRepository.deleteAll();
        userCollect = createEntity();
    }

    @Test
    void createUserCollect() throws Exception {
        int databaseSizeBeforeCreate = userCollectRepository.findAll().size();
        // Create the UserCollect
        restUserCollectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userCollect)))
            .andExpect(status().isCreated());

        // Validate the UserCollect in the database
        List<UserCollect> userCollectList = userCollectRepository.findAll();
        assertThat(userCollectList).hasSize(databaseSizeBeforeCreate + 1);
        UserCollect testUserCollect = userCollectList.get(userCollectList.size() - 1);
        assertThat(testUserCollect.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUserCollect.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testUserCollect.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testUserCollect.getVitalKey()).isEqualTo(DEFAULT_VITAL_KEY);
    }

    @Test
    void createUserCollectWithExistingId() throws Exception {
        // Create the UserCollect with an existing ID
        userCollect.setId("existing_id");

        int databaseSizeBeforeCreate = userCollectRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserCollectMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userCollect)))
            .andExpect(status().isBadRequest());

        // Validate the UserCollect in the database
        List<UserCollect> userCollectList = userCollectRepository.findAll();
        assertThat(userCollectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllUserCollects() throws Exception {
        // Initialize the database
        userCollectRepository.save(userCollect);

        // Get all the userCollectList
        restUserCollectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].vitalKey").value(hasItem(DEFAULT_VITAL_KEY)));
    }

    @Test
    void getUserCollect() throws Exception {
        // Initialize the database
        userCollectRepository.save(userCollect);

        // Get the userCollect
        restUserCollectMockMvc
            .perform(get(ENTITY_API_URL_ID, userCollect.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.vitalKey").value(DEFAULT_VITAL_KEY));
    }

    @Test
    void getNonExistingUserCollect() throws Exception {
        // Get the userCollect
        restUserCollectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingUserCollect() throws Exception {
        // Initialize the database
        userCollectRepository.save(userCollect);

        int databaseSizeBeforeUpdate = userCollectRepository.findAll().size();

        // Update the userCollect
        UserCollect updatedUserCollect = userCollectRepository.findById(userCollect.getId()).get();
        updatedUserCollect.name(UPDATED_NAME).lastName(UPDATED_LAST_NAME).username(UPDATED_USERNAME).vitalKey(UPDATED_VITAL_KEY);

        restUserCollectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserCollect.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUserCollect))
            )
            .andExpect(status().isOk());

        // Validate the UserCollect in the database
        List<UserCollect> userCollectList = userCollectRepository.findAll();
        assertThat(userCollectList).hasSize(databaseSizeBeforeUpdate);
        UserCollect testUserCollect = userCollectList.get(userCollectList.size() - 1);
        assertThat(testUserCollect.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUserCollect.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testUserCollect.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testUserCollect.getVitalKey()).isEqualTo(UPDATED_VITAL_KEY);
    }

    @Test
    void putNonExistingUserCollect() throws Exception {
        int databaseSizeBeforeUpdate = userCollectRepository.findAll().size();
        userCollect.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserCollectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userCollect.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userCollect))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserCollect in the database
        List<UserCollect> userCollectList = userCollectRepository.findAll();
        assertThat(userCollectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchUserCollect() throws Exception {
        int databaseSizeBeforeUpdate = userCollectRepository.findAll().size();
        userCollect.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserCollectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userCollect))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserCollect in the database
        List<UserCollect> userCollectList = userCollectRepository.findAll();
        assertThat(userCollectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamUserCollect() throws Exception {
        int databaseSizeBeforeUpdate = userCollectRepository.findAll().size();
        userCollect.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserCollectMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userCollect)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserCollect in the database
        List<UserCollect> userCollectList = userCollectRepository.findAll();
        assertThat(userCollectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateUserCollectWithPatch() throws Exception {
        // Initialize the database
        userCollectRepository.save(userCollect);

        int databaseSizeBeforeUpdate = userCollectRepository.findAll().size();

        // Update the userCollect using partial update
        UserCollect partialUpdatedUserCollect = new UserCollect();
        partialUpdatedUserCollect.setId(userCollect.getId());

        partialUpdatedUserCollect.name(UPDATED_NAME).username(UPDATED_USERNAME);

        restUserCollectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserCollect.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserCollect))
            )
            .andExpect(status().isOk());

        // Validate the UserCollect in the database
        List<UserCollect> userCollectList = userCollectRepository.findAll();
        assertThat(userCollectList).hasSize(databaseSizeBeforeUpdate);
        UserCollect testUserCollect = userCollectList.get(userCollectList.size() - 1);
        assertThat(testUserCollect.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUserCollect.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testUserCollect.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testUserCollect.getVitalKey()).isEqualTo(DEFAULT_VITAL_KEY);
    }

    @Test
    void fullUpdateUserCollectWithPatch() throws Exception {
        // Initialize the database
        userCollectRepository.save(userCollect);

        int databaseSizeBeforeUpdate = userCollectRepository.findAll().size();

        // Update the userCollect using partial update
        UserCollect partialUpdatedUserCollect = new UserCollect();
        partialUpdatedUserCollect.setId(userCollect.getId());

        partialUpdatedUserCollect.name(UPDATED_NAME).lastName(UPDATED_LAST_NAME).username(UPDATED_USERNAME).vitalKey(UPDATED_VITAL_KEY);

        restUserCollectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserCollect.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserCollect))
            )
            .andExpect(status().isOk());

        // Validate the UserCollect in the database
        List<UserCollect> userCollectList = userCollectRepository.findAll();
        assertThat(userCollectList).hasSize(databaseSizeBeforeUpdate);
        UserCollect testUserCollect = userCollectList.get(userCollectList.size() - 1);
        assertThat(testUserCollect.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUserCollect.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testUserCollect.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testUserCollect.getVitalKey()).isEqualTo(UPDATED_VITAL_KEY);
    }

    @Test
    void patchNonExistingUserCollect() throws Exception {
        int databaseSizeBeforeUpdate = userCollectRepository.findAll().size();
        userCollect.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserCollectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userCollect.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userCollect))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserCollect in the database
        List<UserCollect> userCollectList = userCollectRepository.findAll();
        assertThat(userCollectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchUserCollect() throws Exception {
        int databaseSizeBeforeUpdate = userCollectRepository.findAll().size();
        userCollect.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserCollectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userCollect))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserCollect in the database
        List<UserCollect> userCollectList = userCollectRepository.findAll();
        assertThat(userCollectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamUserCollect() throws Exception {
        int databaseSizeBeforeUpdate = userCollectRepository.findAll().size();
        userCollect.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserCollectMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userCollect))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserCollect in the database
        List<UserCollect> userCollectList = userCollectRepository.findAll();
        assertThat(userCollectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteUserCollect() throws Exception {
        // Initialize the database
        userCollectRepository.save(userCollect);

        int databaseSizeBeforeDelete = userCollectRepository.findAll().size();

        // Delete the userCollect
        restUserCollectMockMvc
            .perform(delete(ENTITY_API_URL_ID, userCollect.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserCollect> userCollectList = userCollectRepository.findAll();
        assertThat(userCollectList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
