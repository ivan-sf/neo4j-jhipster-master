package com.be4tech.b4collectneo.repository;

import com.be4tech.b4collectneo.domain.Wearable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Neo4j repository for the Wearable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WearableRepository extends Neo4jRepository<Wearable, String> {}
