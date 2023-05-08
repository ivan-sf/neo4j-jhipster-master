package com.be4tech.b4collectneo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

/**
 * A DataVital.
 */
@Node
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DataVital implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    private String id;

    @Property("vital_key")
    private String vitalKey;

    @Property("event_type")
    private String eventType;

    @Property("date")
    private Instant date;

    @Property("data")
    private String data;

    @Relationship(value = "HAS_DATA_VITAL", direction = Relationship.Direction.INCOMING)
    @JsonIgnoreProperties(value = { "dataVitals", "userCollect" }, allowSetters = true)
    private Wearable wearable;

    @Relationship(value = "HAS_DATA_VITAL", direction = Relationship.Direction.INCOMING)
    @JsonIgnoreProperties(value = { "wearable", "dataVitals" }, allowSetters = true)
    private UserCollect userCollect;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public DataVital id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVitalKey() {
        return this.vitalKey;
    }

    public DataVital vitalKey(String vitalKey) {
        this.setVitalKey(vitalKey);
        return this;
    }

    public void setVitalKey(String vitalKey) {
        this.vitalKey = vitalKey;
    }

    public String getEventType() {
        return this.eventType;
    }

    public DataVital eventType(String eventType) {
        this.setEventType(eventType);
        return this;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Instant getDate() {
        return this.date;
    }

    public DataVital date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getData() {
        return this.data;
    }

    public DataVital data(String data) {
        this.setData(data);
        return this;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Wearable getWearable() {
        return this.wearable;
    }

    public void setWearable(Wearable wearable) {
        this.wearable = wearable;
    }

    public DataVital wearable(Wearable wearable) {
        this.setWearable(wearable);
        return this;
    }

    public UserCollect getUserCollect() {
        return this.userCollect;
    }

    public void setUserCollect(UserCollect userCollect) {
        this.userCollect = userCollect;
    }

    public DataVital userCollect(UserCollect userCollect) {
        this.setUserCollect(userCollect);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataVital)) {
            return false;
        }
        return id != null && id.equals(((DataVital) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DataVital{" +
            "id=" + getId() +
            ", vitalKey='" + getVitalKey() + "'" +
            ", eventType='" + getEventType() + "'" +
            ", date='" + getDate() + "'" +
            ", data='" + getData() + "'" +
            "}";
    }
}
