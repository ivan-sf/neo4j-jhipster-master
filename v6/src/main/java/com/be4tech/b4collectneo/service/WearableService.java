package com.be4tech.b4collectneo.service;

import com.be4tech.b4collectneo.domain.Wearable;
import com.be4tech.b4collectneo.repository.WearableRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Wearable}.
 */
@Service
public class WearableService {

    private final Logger log = LoggerFactory.getLogger(WearableService.class);

    private final WearableRepository wearableRepository;

    public WearableService(WearableRepository wearableRepository) {
        this.wearableRepository = wearableRepository;
    }

    /**
     * Save a wearable.
     *
     * @param wearable the entity to save.
     * @return the persisted entity.
     */
    public Wearable save(Wearable wearable) {
        log.debug("Request to save Wearable : {}", wearable);
        return wearableRepository.save(wearable);
    }

    /**
     * Update a wearable.
     *
     * @param wearable the entity to save.
     * @return the persisted entity.
     */
    public Wearable update(Wearable wearable) {
        log.debug("Request to update Wearable : {}", wearable);
        return wearableRepository.save(wearable);
    }

    /**
     * Partially update a wearable.
     *
     * @param wearable the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Wearable> partialUpdate(Wearable wearable) {
        log.debug("Request to partially update Wearable : {}", wearable);

        return wearableRepository
            .findById(wearable.getId())
            .map(existingWearable -> {
                if (wearable.getNameWearable() != null) {
                    existingWearable.setNameWearable(wearable.getNameWearable());
                }
                if (wearable.getType() != null) {
                    existingWearable.setType(wearable.getType());
                }

                return existingWearable;
            })
            .map(wearableRepository::save);
    }

    /**
     * Get all the wearables.
     *
     * @return the list of entities.
     */
    public List<Wearable> findAll() {
        log.debug("Request to get all Wearables");
        return wearableRepository.findAll();
    }

    /**
     *  Get all the wearables where UserCollect is {@code null}.
     *  @return the list of entities.
     */

    public List<Wearable> findAllWhereUserCollectIsNull() {
        log.debug("Request to get all wearables where UserCollect is null");
        return StreamSupport
            .stream(wearableRepository.findAll().spliterator(), false)
            .filter(wearable -> wearable.getUserCollect() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one wearable by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<Wearable> findOne(String id) {
        log.debug("Request to get Wearable : {}", id);
        return wearableRepository.findById(id);
    }

    /**
     * Delete the wearable by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Wearable : {}", id);
        wearableRepository.deleteById(id);
    }
}
