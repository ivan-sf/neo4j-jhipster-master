package com.be4tech.b4collectneo.service;

import com.be4tech.b4collectneo.domain.UserCollect;
import com.be4tech.b4collectneo.repository.UserCollectRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link UserCollect}.
 */
@Service
public class UserCollectService {

    private final Logger log = LoggerFactory.getLogger(UserCollectService.class);

    private final UserCollectRepository userCollectRepository;

    public UserCollectService(UserCollectRepository userCollectRepository) {
        this.userCollectRepository = userCollectRepository;
    }

    /**
     * Save a userCollect.
     *
     * @param userCollect the entity to save.
     * @return the persisted entity.
     */
    public UserCollect save(UserCollect userCollect) {
        log.debug("Request to save UserCollect : {}", userCollect);
        return userCollectRepository.save(userCollect);
    }

    /**
     * Update a userCollect.
     *
     * @param userCollect the entity to save.
     * @return the persisted entity.
     */
    public UserCollect update(UserCollect userCollect) {
        log.debug("Request to update UserCollect : {}", userCollect);
        return userCollectRepository.save(userCollect);
    }

    /**
     * Partially update a userCollect.
     *
     * @param userCollect the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UserCollect> partialUpdate(UserCollect userCollect) {
        log.debug("Request to partially update UserCollect : {}", userCollect);

        return userCollectRepository
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
    }

    /**
     * Get all the userCollects.
     *
     * @return the list of entities.
     */
    public List<UserCollect> findAll() {
        log.debug("Request to get all UserCollects");
        return userCollectRepository.findAll();
    }

    /**
     * Get one userCollect by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<UserCollect> findOne(String id) {
        log.debug("Request to get UserCollect : {}", id);
        return userCollectRepository.findById(id);
    }

    /**
     * Delete the userCollect by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete UserCollect : {}", id);
        userCollectRepository.deleteById(id);
    }
}
