package com.be4tech.b4collectneo.repository;

import com.be4tech.b4collectneo.domain.UserCollect;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Neo4j repository for the UserCollect entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserCollectRepository extends Neo4jRepository<UserCollect, String> {}
