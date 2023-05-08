package com.be4tech.b4collectneo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4collectneo.IntegrationTest;
import com.be4tech.b4collectneo.domain.Wearable;
import com.be4tech.b4collectneo.repository.WearableRepository;
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
 * Integration tests for the {@link WearableResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WearableResourceIT {

    private static final String DEFAULT_NAME_WEARABLE = "AAAAAAAAAA";
    private static final String UPDATED_NAME_WEARABLE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/wearables";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private WearableRepository wearableRepository;

    @Autowired
    private MockMvc restWearableMockMvc;

    private Wearable wearable;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wearable createEntity() {
        Wearable wearable = new Wearable().nameWearable(DEFAULT_NAME_WEARABLE).type(DEFAULT_TYPE);
        return wearable;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wearable createUpdatedEntity() {
        Wearable wearable = new Wearable().nameWearable(UPDATED_NAME_WEARABLE).type(UPDATED_TYPE);
        return wearable;
    }

    @BeforeEach
    public void initTest() {
        wearableRepository.deleteAll();
        wearable = createEntity();
    }

    @Test
    void createWearable() throws Exception {
        int databaseSizeBeforeCreate = wearableRepository.findAll().size();
        // Create the Wearable
        restWearableMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wearable)))
            .andExpect(status().isCreated());

        // Validate the Wearable in the database
        List<Wearable> wearableList = wearableRepository.findAll();
        assertThat(wearableList).hasSize(databaseSizeBeforeCreate + 1);
        Wearable testWearable = wearableList.get(wearableList.size() - 1);
        assertThat(testWearable.getNameWearable()).isEqualTo(DEFAULT_NAME_WEARABLE);
        assertThat(testWearable.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    void createWearableWithExistingId() throws Exception {
        // Create the Wearable with an existing ID
        wearable.setId("existing_id");

        int databaseSizeBeforeCreate = wearableRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWearableMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wearable)))
            .andExpect(status().isBadRequest());

        // Validate the Wearable in the database
        List<Wearable> wearableList = wearableRepository.findAll();
        assertThat(wearableList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllWearables() throws Exception {
        // Initialize the database
        wearableRepository.save(wearable);

        // Get all the wearableList
        restWearableMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].nameWearable").value(hasItem(DEFAULT_NAME_WEARABLE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    void getWearable() throws Exception {
        // Initialize the database
        wearableRepository.save(wearable);

        // Get the wearable
        restWearableMockMvc
            .perform(get(ENTITY_API_URL_ID, wearable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.nameWearable").value(DEFAULT_NAME_WEARABLE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    void getNonExistingWearable() throws Exception {
        // Get the wearable
        restWearableMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingWearable() throws Exception {
        // Initialize the database
        wearableRepository.save(wearable);

        int databaseSizeBeforeUpdate = wearableRepository.findAll().size();

        // Update the wearable
        Wearable updatedWearable = wearableRepository.findById(wearable.getId()).get();
        updatedWearable.nameWearable(UPDATED_NAME_WEARABLE).type(UPDATED_TYPE);

        restWearableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWearable.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedWearable))
            )
            .andExpect(status().isOk());

        // Validate the Wearable in the database
        List<Wearable> wearableList = wearableRepository.findAll();
        assertThat(wearableList).hasSize(databaseSizeBeforeUpdate);
        Wearable testWearable = wearableList.get(wearableList.size() - 1);
        assertThat(testWearable.getNameWearable()).isEqualTo(UPDATED_NAME_WEARABLE);
        assertThat(testWearable.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void putNonExistingWearable() throws Exception {
        int databaseSizeBeforeUpdate = wearableRepository.findAll().size();
        wearable.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWearableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, wearable.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wearable))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wearable in the database
        List<Wearable> wearableList = wearableRepository.findAll();
        assertThat(wearableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchWearable() throws Exception {
        int databaseSizeBeforeUpdate = wearableRepository.findAll().size();
        wearable.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWearableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wearable))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wearable in the database
        List<Wearable> wearableList = wearableRepository.findAll();
        assertThat(wearableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamWearable() throws Exception {
        int databaseSizeBeforeUpdate = wearableRepository.findAll().size();
        wearable.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWearableMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wearable)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Wearable in the database
        List<Wearable> wearableList = wearableRepository.findAll();
        assertThat(wearableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateWearableWithPatch() throws Exception {
        // Initialize the database
        wearableRepository.save(wearable);

        int databaseSizeBeforeUpdate = wearableRepository.findAll().size();

        // Update the wearable using partial update
        Wearable partialUpdatedWearable = new Wearable();
        partialUpdatedWearable.setId(wearable.getId());

        partialUpdatedWearable.nameWearable(UPDATED_NAME_WEARABLE);

        restWearableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWearable.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWearable))
            )
            .andExpect(status().isOk());

        // Validate the Wearable in the database
        List<Wearable> wearableList = wearableRepository.findAll();
        assertThat(wearableList).hasSize(databaseSizeBeforeUpdate);
        Wearable testWearable = wearableList.get(wearableList.size() - 1);
        assertThat(testWearable.getNameWearable()).isEqualTo(UPDATED_NAME_WEARABLE);
        assertThat(testWearable.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    void fullUpdateWearableWithPatch() throws Exception {
        // Initialize the database
        wearableRepository.save(wearable);

        int databaseSizeBeforeUpdate = wearableRepository.findAll().size();

        // Update the wearable using partial update
        Wearable partialUpdatedWearable = new Wearable();
        partialUpdatedWearable.setId(wearable.getId());

        partialUpdatedWearable.nameWearable(UPDATED_NAME_WEARABLE).type(UPDATED_TYPE);

        restWearableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWearable.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWearable))
            )
            .andExpect(status().isOk());

        // Validate the Wearable in the database
        List<Wearable> wearableList = wearableRepository.findAll();
        assertThat(wearableList).hasSize(databaseSizeBeforeUpdate);
        Wearable testWearable = wearableList.get(wearableList.size() - 1);
        assertThat(testWearable.getNameWearable()).isEqualTo(UPDATED_NAME_WEARABLE);
        assertThat(testWearable.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void patchNonExistingWearable() throws Exception {
        int databaseSizeBeforeUpdate = wearableRepository.findAll().size();
        wearable.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWearableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, wearable.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(wearable))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wearable in the database
        List<Wearable> wearableList = wearableRepository.findAll();
        assertThat(wearableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchWearable() throws Exception {
        int databaseSizeBeforeUpdate = wearableRepository.findAll().size();
        wearable.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWearableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(wearable))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wearable in the database
        List<Wearable> wearableList = wearableRepository.findAll();
        assertThat(wearableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamWearable() throws Exception {
        int databaseSizeBeforeUpdate = wearableRepository.findAll().size();
        wearable.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWearableMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(wearable)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Wearable in the database
        List<Wearable> wearableList = wearableRepository.findAll();
        assertThat(wearableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteWearable() throws Exception {
        // Initialize the database
        wearableRepository.save(wearable);

        int databaseSizeBeforeDelete = wearableRepository.findAll().size();

        // Delete the wearable
        restWearableMockMvc
            .perform(delete(ENTITY_API_URL_ID, wearable.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Wearable> wearableList = wearableRepository.findAll();
        assertThat(wearableList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
