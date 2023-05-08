package com.be4tech.b4collectneo.service;

import com.be4tech.b4collectneo.domain.DataVital;
import com.be4tech.b4collectneo.repository.DataVitalRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link DataVital}.
 */
@Service
public class DataVitalService {

    private final Logger log = LoggerFactory.getLogger(DataVitalService.class);

    private final DataVitalRepository dataVitalRepository;

    public DataVitalService(DataVitalRepository dataVitalRepository) {
        this.dataVitalRepository = dataVitalRepository;
    }

    /**
     * Save a dataVital.
     *
     * @param dataVital the entity to save.
     * @return the persisted entity.
     */
    public DataVital save(DataVital dataVital) {
        log.debug("Request to save DataVital : {}", dataVital);
        return dataVitalRepository.save(dataVital);
    }

    /**
     * Update a dataVital.
     *
     * @param dataVital the entity to save.
     * @return the persisted entity.
     */
    public DataVital update(DataVital dataVital) {
        log.debug("Request to update DataVital : {}", dataVital);
        return dataVitalRepository.save(dataVital);
    }

    /**
     * Partially update a dataVital.
     *
     * @param dataVital the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DataVital> partialUpdate(DataVital dataVital) {
        log.debug("Request to partially update DataVital : {}", dataVital);

        return dataVitalRepository
            .findById(dataVital.getId())
            .map(existingDataVital -> {
                if (dataVital.getVitalKey() != null) {
                    existingDataVital.setVitalKey(dataVital.getVitalKey());
                }
                if (dataVital.getEventType() != null) {
                    existingDataVital.setEventType(dataVital.getEventType());
                }
                if (dataVital.getDate() != null) {
                    existingDataVital.setDate(dataVital.getDate());
                }
                if (dataVital.getData() != null) {
                    existingDataVital.setData(dataVital.getData());
                }

                return existingDataVital;
            })
            .map(dataVitalRepository::save);
    }

    /**
     * Get all the dataVitals.
     *
     * @return the list of entities.
     */
    public List<DataVital> findAll() {
        log.debug("Request to get all DataVitals");
        return dataVitalRepository.findAll();
    }

    /**
     * Get one dataVital by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<DataVital> findOne(String id) {
        log.debug("Request to get DataVital : {}", id);
        return dataVitalRepository.findById(id);
    }

    /**
     * Delete the dataVital by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete DataVital : {}", id);
        dataVitalRepository.deleteById(id);
    }
}
