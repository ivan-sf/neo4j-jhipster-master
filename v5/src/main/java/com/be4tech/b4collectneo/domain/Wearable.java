package com.be4tech.b4collectneo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

/**
 * A Wearable.
 */
@Node
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Wearable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    private String id;

    @Property("name_wearable")
    private String nameWearable;

    @Property("type")
    private String type;

    @Relationship("HAS_DATA_VITAL")
    @JsonIgnoreProperties(value = { "wearable", "userCollect" }, allowSetters = true)
    private Set<DataVital> dataVitals = new HashSet<>();

    @Relationship("HAS_USER_COLLECT")
    private UserCollect userCollect;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Wearable id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameWearable() {
        return this.nameWearable;
    }

    public Wearable nameWearable(String nameWearable) {
        this.setNameWearable(nameWearable);
        return this;
    }

    public void setNameWearable(String nameWearable) {
        this.nameWearable = nameWearable;
    }

    public String getType() {
        return this.type;
    }

    public Wearable type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<DataVital> getDataVitals() {
        return this.dataVitals;
    }

    public void setDataVitals(Set<DataVital> dataVitals) {
        if (this.dataVitals != null) {
            this.dataVitals.forEach(i -> i.setWearable(null));
        }
        if (dataVitals != null) {
            dataVitals.forEach(i -> i.setWearable(this));
        }
        this.dataVitals = dataVitals;
    }

    public Wearable dataVitals(Set<DataVital> dataVitals) {
        this.setDataVitals(dataVitals);
        return this;
    }

    public Wearable addDataVital(DataVital dataVital) {
        this.dataVitals.add(dataVital);
        return this;
    }

    public Wearable removeDataVital(DataVital dataVital) {
        this.dataVitals.remove(dataVital);
        return this;
    }

    public UserCollect getUserCollect() {
        return this.userCollect;
    }

    public void setUserCollect(UserCollect userCollect) {
        if (this.userCollect != null) {
            this.userCollect.setWearable(null);
        }
        if (userCollect != null) {
            userCollect.setWearable(this);
        }
        this.userCollect = userCollect;
    }

    public Wearable userCollect(UserCollect userCollect) {
        this.setUserCollect(userCollect);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Wearable)) {
            return false;
        }
        return id != null && id.equals(((Wearable) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Wearable{" +
            "id=" + getId() +
            ", nameWearable='" + getNameWearable() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
