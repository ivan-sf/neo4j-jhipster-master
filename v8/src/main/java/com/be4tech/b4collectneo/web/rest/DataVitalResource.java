package com.be4tech.b4collectneo.web.rest;

import com.be4tech.b4collectneo.domain.DataVital;
import com.be4tech.b4collectneo.repository.DataVitalRepository;
import com.be4tech.b4collectneo.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.be4tech.b4collectneo.domain.DataVital}.
 */
@RestController
@RequestMapping("/api")
public class DataVitalResource {

    private final Logger log = LoggerFactory.getLogger(DataVitalResource.class);

    private static final String ENTITY_NAME = "dataVital";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DataVitalRepository dataVitalRepository;

    public DataVitalResource(DataVitalRepository dataVitalRepository) {
        this.dataVitalRepository = dataVitalRepository;
    }

    /**
     * {@code POST  /data-vitals} : Create a new dataVital.
     *
     * @param dataVital the dataVital to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dataVital, or with status {@code 400 (Bad Request)} if the dataVital has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/data-vitals")
    public ResponseEntity<DataVital> createDataVital(@RequestBody DataVital dataVital) throws URISyntaxException {
        log.debug("REST request to save DataVital : {}", dataVital);
        if (dataVital.getId() != null) {
            throw new BadRequestAlertException("A new dataVital cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DataVital result = dataVitalRepository.save(dataVital);
        return ResponseEntity
            .created(new URI("/api/data-vitals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /data-vitals/:id} : Updates an existing dataVital.
     *
     * @param id the id of the dataVital to save.
     * @param dataVital the dataVital to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dataVital,
     * or with status {@code 400 (Bad Request)} if the dataVital is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dataVital couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/data-vitals/{id}")
    public ResponseEntity<DataVital> updateDataVital(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody DataVital dataVital
    ) throws URISyntaxException {
        log.debug("REST request to update DataVital : {}, {}", id, dataVital);
        if (dataVital.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dataVital.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dataVitalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DataVital result = dataVitalRepository.save(dataVital);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dataVital.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /data-vitals/:id} : Partial updates given fields of an existing dataVital, field will ignore if it is null
     *
     * @param id the id of the dataVital to save.
     * @param dataVital the dataVital to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dataVital,
     * or with status {@code 400 (Bad Request)} if the dataVital is not valid,
     * or with status {@code 404 (Not Found)} if the dataVital is not found,
     * or with status {@code 500 (Internal Server Error)} if the dataVital couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/data-vitals/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DataVital> partialUpdateDataVital(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody DataVital dataVital
    ) throws URISyntaxException {
        log.debug("REST request to partial update DataVital partially : {}, {}", id, dataVital);
        if (dataVital.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dataVital.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dataVitalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DataVital> result = dataVitalRepository
            .findById(dataVital.getId())
            .map(existingDataVital -> {
                if (dataVital.getDate() != null) {
                    existingDataVital.setDate(dataVital.getDate());
                }
                if (dataVital.getData() != null) {
                    existingDataVital.setData(dataVital.getData());
                }
                if (dataVital.getEventType() != null) {
                    existingDataVital.setEventType(dataVital.getEventType());
                }
                if (dataVital.getVitalKey() != null) {
                    existingDataVital.setVitalKey(dataVital.getVitalKey());
                }

                return existingDataVital;
            })
            .map(dataVitalRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dataVital.getId())
        );
    }

    /**
     * {@code GET  /data-vitals} : get all the dataVitals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dataVitals in body.
     */
    @GetMapping("/data-vitals")
    public List<DataVital> getAllDataVitals() {
        log.debug("REST request to get all DataVitals");
        return dataVitalRepository.findAll();
    }

    /**
     * {@code GET  /data-vitals/:id} : get the "id" dataVital.
     *
     * @param id the id of the dataVital to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dataVital, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/data-vitals/{id}")
    public ResponseEntity<DataVital> getDataVital(@PathVariable String id) {
        log.debug("REST request to get DataVital : {}", id);
        Optional<DataVital> dataVital = dataVitalRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dataVital);
    }

    /**
     * {@code DELETE  /data-vitals/:id} : delete the "id" dataVital.
     *
     * @param id the id of the dataVital to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/data-vitals/{id}")
    public ResponseEntity<Void> deleteDataVital(@PathVariable String id) {
        log.debug("REST request to delete DataVital : {}", id);
        dataVitalRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
