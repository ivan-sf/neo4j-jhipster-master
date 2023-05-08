package com.be4tech.b4collectneo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

/**
 * A UserCollect.
 */
@Node
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserCollect implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    private String id;

    @Property("name")
    private String name;

    @Property("last_name")
    private String lastName;

    @Property("username")
    private String username;

    @Property("vital_key")
    private String vitalKey;

    @Relationship(value = "HAS_USER_COLLECT", direction = Relationship.Direction.INCOMING)
    private Wearable wearable;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public UserCollect id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public UserCollect name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return this.lastName;
    }

    public UserCollect lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return this.username;
    }

    public UserCollect username(String username) {
        this.setUsername(username);
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVitalKey() {
        return this.vitalKey;
    }

    public UserCollect vitalKey(String vitalKey) {
        this.setVitalKey(vitalKey);
        return this;
    }

    public void setVitalKey(String vitalKey) {
        this.vitalKey = vitalKey;
    }

    public Wearable getWearable() {
        return this.wearable;
    }

    public void setWearable(Wearable wearable) {
        this.wearable = wearable;
    }

    public UserCollect wearable(Wearable wearable) {
        this.setWearable(wearable);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserCollect)) {
            return false;
        }
        return id != null && id.equals(((UserCollect) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserCollect{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", username='" + getUsername() + "'" +
            ", vitalKey='" + getVitalKey() + "'" +
            "}";
    }
}
