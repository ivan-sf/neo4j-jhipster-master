package com.be4tech.b4collectneo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.be4tech.b4collectneo.IntegrationTest;
import com.be4tech.b4collectneo.domain.DataVital;
import com.be4tech.b4collectneo.repository.DataVitalRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link DataVitalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DataVitalResourceIT {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DATA = "AAAAAAAAAA";
    private static final String UPDATED_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_EVENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_VITAL_KEY = "AAAAAAAAAA";
    private static final String UPDATED_VITAL_KEY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/data-vitals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private DataVitalRepository dataVitalRepository;

    @Autowired
    private MockMvc restDataVitalMockMvc;

    private DataVital dataVital;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataVital createEntity() {
        DataVital dataVital = new DataVital()
            .date(DEFAULT_DATE)
            .data(DEFAULT_DATA)
            .eventType(DEFAULT_EVENT_TYPE)
            .vitalKey(DEFAULT_VITAL_KEY);
        return dataVital;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataVital createUpdatedEntity() {
        DataVital dataVital = new DataVital()
            .date(UPDATED_DATE)
            .data(UPDATED_DATA)
            .eventType(UPDATED_EVENT_TYPE)
            .vitalKey(UPDATED_VITAL_KEY);
        return dataVital;
    }

    @BeforeEach
    public void initTest() {
        dataVitalRepository.deleteAll();
        dataVital = createEntity();
    }

    @Test
    void createDataVital() throws Exception {
        int databaseSizeBeforeCreate = dataVitalRepository.findAll().size();
        // Create the DataVital
        restDataVitalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataVital)))
            .andExpect(status().isCreated());

        // Validate the DataVital in the database
        List<DataVital> dataVitalList = dataVitalRepository.findAll();
        assertThat(dataVitalList).hasSize(databaseSizeBeforeCreate + 1);
        DataVital testDataVital = dataVitalList.get(dataVitalList.size() - 1);
        assertThat(testDataVital.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testDataVital.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testDataVital.getEventType()).isEqualTo(DEFAULT_EVENT_TYPE);
        assertThat(testDataVital.getVitalKey()).isEqualTo(DEFAULT_VITAL_KEY);
    }

    @Test
    void createDataVitalWithExistingId() throws Exception {
        // Create the DataVital with an existing ID
        dataVital.setId("existing_id");

        int databaseSizeBeforeCreate = dataVitalRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataVitalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataVital)))
            .andExpect(status().isBadRequest());

        // Validate the DataVital in the database
        List<DataVital> dataVitalList = dataVitalRepository.findAll();
        assertThat(dataVitalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllDataVitals() throws Exception {
        // Initialize the database
        dataVitalRepository.save(dataVital);

        // Get all the dataVitalList
        restDataVitalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA)))
            .andExpect(jsonPath("$.[*].eventType").value(hasItem(DEFAULT_EVENT_TYPE)))
            .andExpect(jsonPath("$.[*].vitalKey").value(hasItem(DEFAULT_VITAL_KEY)));
    }

    @Test
    void getDataVital() throws Exception {
        // Initialize the database
        dataVitalRepository.save(dataVital);

        // Get the dataVital
        restDataVitalMockMvc
            .perform(get(ENTITY_API_URL_ID, dataVital.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA))
            .andExpect(jsonPath("$.eventType").value(DEFAULT_EVENT_TYPE))
            .andExpect(jsonPath("$.vitalKey").value(DEFAULT_VITAL_KEY));
    }

    @Test
    void getNonExistingDataVital() throws Exception {
        // Get the dataVital
        restDataVitalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingDataVital() throws Exception {
        // Initialize the database
        dataVitalRepository.save(dataVital);

        int databaseSizeBeforeUpdate = dataVitalRepository.findAll().size();

        // Update the dataVital
        DataVital updatedDataVital = dataVitalRepository.findById(dataVital.getId()).get();
        updatedDataVital.date(UPDATED_DATE).data(UPDATED_DATA).eventType(UPDATED_EVENT_TYPE).vitalKey(UPDATED_VITAL_KEY);

        restDataVitalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDataVital.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDataVital))
            )
            .andExpect(status().isOk());

        // Validate the DataVital in the database
        List<DataVital> dataVitalList = dataVitalRepository.findAll();
        assertThat(dataVitalList).hasSize(databaseSizeBeforeUpdate);
        DataVital testDataVital = dataVitalList.get(dataVitalList.size() - 1);
        assertThat(testDataVital.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDataVital.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testDataVital.getEventType()).isEqualTo(UPDATED_EVENT_TYPE);
        assertThat(testDataVital.getVitalKey()).isEqualTo(UPDATED_VITAL_KEY);
    }

    @Test
    void putNonExistingDataVital() throws Exception {
        int databaseSizeBeforeUpdate = dataVitalRepository.findAll().size();
        dataVital.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataVitalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dataVital.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dataVital))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataVital in the database
        List<DataVital> dataVitalList = dataVitalRepository.findAll();
        assertThat(dataVitalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchDataVital() throws Exception {
        int databaseSizeBeforeUpdate = dataVitalRepository.findAll().size();
        dataVital.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataVitalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dataVital))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataVital in the database
        List<DataVital> dataVitalList = dataVitalRepository.findAll();
        assertThat(dataVitalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamDataVital() throws Exception {
        int databaseSizeBeforeUpdate = dataVitalRepository.findAll().size();
        dataVital.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataVitalMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataVital)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DataVital in the database
        List<DataVital> dataVitalList = dataVitalRepository.findAll();
        assertThat(dataVitalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateDataVitalWithPatch() throws Exception {
        // Initialize the database
        dataVitalRepository.save(dataVital);

        int databaseSizeBeforeUpdate = dataVitalRepository.findAll().size();

        // Update the dataVital using partial update
        DataVital partialUpdatedDataVital = new DataVital();
        partialUpdatedDataVital.setId(dataVital.getId());

        partialUpdatedDataVital.eventType(UPDATED_EVENT_TYPE).vitalKey(UPDATED_VITAL_KEY);

        restDataVitalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDataVital.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDataVital))
            )
            .andExpect(status().isOk());

        // Validate the DataVital in the database
        List<DataVital> dataVitalList = dataVitalRepository.findAll();
        assertThat(dataVitalList).hasSize(databaseSizeBeforeUpdate);
        DataVital testDataVital = dataVitalList.get(dataVitalList.size() - 1);
        assertThat(testDataVital.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testDataVital.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testDataVital.getEventType()).isEqualTo(UPDATED_EVENT_TYPE);
        assertThat(testDataVital.getVitalKey()).isEqualTo(UPDATED_VITAL_KEY);
    }

    @Test
    void fullUpdateDataVitalWithPatch() throws Exception {
        // Initialize the database
        dataVitalRepository.save(dataVital);

        int databaseSizeBeforeUpdate = dataVitalRepository.findAll().size();

        // Update the dataVital using partial update
        DataVital partialUpdatedDataVital = new DataVital();
        partialUpdatedDataVital.setId(dataVital.getId());

        partialUpdatedDataVital.date(UPDATED_DATE).data(UPDATED_DATA).eventType(UPDATED_EVENT_TYPE).vitalKey(UPDATED_VITAL_KEY);

        restDataVitalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDataVital.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDataVital))
            )
            .andExpect(status().isOk());

        // Validate the DataVital in the database
        List<DataVital> dataVitalList = dataVitalRepository.findAll();
        assertThat(dataVitalList).hasSize(databaseSizeBeforeUpdate);
        DataVital testDataVital = dataVitalList.get(dataVitalList.size() - 1);
        assertThat(testDataVital.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDataVital.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testDataVital.getEventType()).isEqualTo(UPDATED_EVENT_TYPE);
        assertThat(testDataVital.getVitalKey()).isEqualTo(UPDATED_VITAL_KEY);
    }

    @Test
    void patchNonExistingDataVital() throws Exception {
        int databaseSizeBeforeUpdate = dataVitalRepository.findAll().size();
        dataVital.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataVitalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dataVital.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dataVital))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataVital in the database
        List<DataVital> dataVitalList = dataVitalRepository.findAll();
        assertThat(dataVitalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchDataVital() throws Exception {
        int databaseSizeBeforeUpdate = dataVitalRepository.findAll().size();
        dataVital.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataVitalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dataVital))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataVital in the database
        List<DataVital> dataVitalList = dataVitalRepository.findAll();
        assertThat(dataVitalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamDataVital() throws Exception {
        int databaseSizeBeforeUpdate = dataVitalRepository.findAll().size();
        dataVital.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataVitalMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dataVital))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DataVital in the database
        List<DataVital> dataVitalList = dataVitalRepository.findAll();
        assertThat(dataVitalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteDataVital() throws Exception {
        // Initialize the database
        dataVitalRepository.save(dataVital);

        int databaseSizeBeforeDelete = dataVitalRepository.findAll().size();

        // Delete the dataVital
        restDataVitalMockMvc
            .perform(delete(ENTITY_API_URL_ID, dataVital.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DataVital> dataVitalList = dataVitalRepository.findAll();
        assertThat(dataVitalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
