package com.be4tech.b4collectneo.web.rest;

import com.be4tech.b4collectneo.domain.UserCollect;
import com.be4tech.b4collectneo.repository.UserCollectRepository;
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
 * REST controller for managing {@link com.be4tech.b4collectneo.domain.UserCollect}.
 */
@RestController
@RequestMapping("/api")
public class UserCollectResource {

    private final Logger log = LoggerFactory.getLogger(UserCollectResource.class);

    private static final String ENTITY_NAME = "userCollect";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserCollectRepository userCollectRepository;

    public UserCollectResource(UserCollectRepository userCollectRepository) {
        this.userCollectRepository = userCollectRepository;
    }

    /**
     * {@code POST  /user-collects} : Create a new userCollect.
     *
     * @param userCollect the userCollect to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userCollect, or with status {@code 400 (Bad Request)} if the userCollect has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-collects")
    public ResponseEntity<UserCollect> createUserCollect(@RequestBody UserCollect userCollect) throws URISyntaxException {
        log.debug("REST request to save UserCollect : {}", userCollect);
        if (userCollect.getId() != null) {
            throw new BadRequestAlertException("A new userCollect cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserCollect result = userCollectRepository.save(userCollect);
        return ResponseEntity
            .created(new URI("/api/user-collects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /user-collects/:id} : Updates an existing userCollect.
     *
     * @param id the id of the userCollect to save.
     * @param userCollect the userCollect to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userCollect,
     * or with status {@code 400 (Bad Request)} if the userCollect is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userCollect couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-collects/{id}")
    public ResponseEntity<UserCollect> updateUserCollect(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody UserCollect userCollect
    ) throws URISyntaxException {
        log.debug("REST request to update UserCollect : {}, {}", id, userCollect);
        if (userCollect.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userCollect.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userCollectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserCollect result = userCollectRepository.save(userCollect);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userCollect.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-collects/:id} : Partial updates given fields of an existing userCollect, field will ignore if it is null
     *
     * @param id the id of the userCollect to save.
     * @param userCollect the userCollect to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userCollect,
     * or with status {@code 400 (Bad Request)} if the userCollect is not valid,
     * or with status {@code 404 (Not Found)} if the userCollect is not found,
     * or with status {@code 500 (Internal Server Error)} if the userCollect couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-collects/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserCollect> partialUpdateUserCollect(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody UserCollect userCollect
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserCollect partially : {}, {}", id, userCollect);
        if (userCollect.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userCollect.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userCollectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserCollect> result = userCollectRepository
            .findById(userCollect.getId())
            .map(existingUserCollect -> {
                if (userCollect.getName() != null) {
                    existingUserCollect.setName(userCollect.getName());
                }
                if (userCollect.getLastName() != null) {
                    existingUserCollect.setLastName(userCollect.getLastName());
                }
                if (userCollect.getUsername() != null) {
                    existingUserCollect.setUsername(userCollect.getUsername());
                }
                if (userCollect.getVitalKey() != null) {
                    existingUserCollect.setVitalKey(userCollect.getVitalKey());
                }

                return existingUserCollect;
            })
            .map(userCollectRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userCollect.getId())
        );
    }

    /**
     * {@code GET  /user-collects} : get all the userCollects.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userCollects in body.
     */
    @GetMapping("/user-collects")
    public List<UserCollect> getAllUserCollects() {
        log.debug("REST request to get all UserCollects");
        return userCollectRepository.findAll();
    }

    /**
     * {@code GET  /user-collects/:id} : get the "id" userCollect.
     *
     * @param id the id of the userCollect to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userCollect, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-collects/{id}")
    public ResponseEntity<UserCollect> getUserCollect(@PathVariable String id) {
        log.debug("REST request to get UserCollect : {}", id);
        Optional<UserCollect> userCollect = userCollectRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(userCollect);
    }

    /**
     * {@code DELETE  /user-collects/:id} : delete the "id" userCollect.
     *
     * @param id the id of the userCollect to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-collects/{id}")
    public ResponseEntity<Void> deleteUserCollect(@PathVariable String id) {
        log.debug("REST request to delete UserCollect : {}", id);
        userCollectRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
