package com.be4tech.b4collectneo.web.rest;

import com.be4tech.b4collectneo.domain.Wearable;
import com.be4tech.b4collectneo.repository.WearableRepository;
import com.be4tech.b4collectneo.service.WearableService;
import com.be4tech.b4collectneo.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.be4tech.b4collectneo.domain.Wearable}.
 */
@RestController
@RequestMapping("/api")
public class WearableResource {

    private final Logger log = LoggerFactory.getLogger(WearableResource.class);

    private static final String ENTITY_NAME = "wearable";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WearableService wearableService;

    private final WearableRepository wearableRepository;

    public WearableResource(WearableService wearableService, WearableRepository wearableRepository) {
        this.wearableService = wearableService;
        this.wearableRepository = wearableRepository;
    }

    /**
     * {@code POST  /wearables} : Create a new wearable.
     *
     * @param wearable the wearable to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wearable, or with status {@code 400 (Bad Request)} if the wearable has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/wearables")
    public ResponseEntity<Wearable> createWearable(@RequestBody Wearable wearable) throws URISyntaxException {
        log.debug("REST request to save Wearable : {}", wearable);
        if (wearable.getId() != null) {
            throw new BadRequestAlertException("A new wearable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Wearable result = wearableService.save(wearable);
        return ResponseEntity
            .created(new URI("/api/wearables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /wearables/:id} : Updates an existing wearable.
     *
     * @param id the id of the wearable to save.
     * @param wearable the wearable to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wearable,
     * or with status {@code 400 (Bad Request)} if the wearable is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wearable couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/wearables/{id}")
    public ResponseEntity<Wearable> updateWearable(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Wearable wearable
    ) throws URISyntaxException {
        log.debug("REST request to update Wearable : {}, {}", id, wearable);
        if (wearable.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wearable.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wearableRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Wearable result = wearableService.update(wearable);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wearable.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /wearables/:id} : Partial updates given fields of an existing wearable, field will ignore if it is null
     *
     * @param id the id of the wearable to save.
     * @param wearable the wearable to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wearable,
     * or with status {@code 400 (Bad Request)} if the wearable is not valid,
     * or with status {@code 404 (Not Found)} if the wearable is not found,
     * or with status {@code 500 (Internal Server Error)} if the wearable couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/wearables/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Wearable> partialUpdateWearable(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Wearable wearable
    ) throws URISyntaxException {
        log.debug("REST request to partial update Wearable partially : {}, {}", id, wearable);
        if (wearable.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wearable.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wearableRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Wearable> result = wearableService.partialUpdate(wearable);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wearable.getId())
        );
    }

    /**
     * {@code GET  /wearables} : get all the wearables.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wearables in body.
     */
    @GetMapping("/wearables")
    public List<Wearable> getAllWearables(@RequestParam(required = false) String filter) {
        if ("usercollect-is-null".equals(filter)) {
            log.debug("REST request to get all Wearables where userCollect is null");
            return wearableService.findAllWhereUserCollectIsNull();
        }
        log.debug("REST request to get all Wearables");
        return wearableService.findAll();
    }

    /**
     * {@code GET  /wearables/:id} : get the "id" wearable.
     *
     * @param id the id of the wearable to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wearable, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/wearables/{id}")
    public ResponseEntity<Wearable> getWearable(@PathVariable String id) {
        log.debug("REST request to get Wearable : {}", id);
        Optional<Wearable> wearable = wearableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wearable);
    }

    /**
     * {@code DELETE  /wearables/:id} : delete the "id" wearable.
     *
     * @param id the id of the wearable to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/wearables/{id}")
    public ResponseEntity<Void> deleteWearable(@PathVariable String id) {
        log.debug("REST request to delete Wearable : {}", id);
        wearableService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
