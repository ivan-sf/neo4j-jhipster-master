package com.be4tech.b4collectneo.repository;

import com.be4tech.b4collectneo.domain.DataVital;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Neo4j repository for the DataVital entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataVitalRepository extends Neo4jRepository<DataVital, String> {}
