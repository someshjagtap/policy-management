package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PolicyUsersType.
 */
@Entity
@Table(name = "policy_users_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PolicyUsersType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "last_modified", nullable = false)
    private String lastModified;

    @NotNull
    @Column(name = "last_modified_by", nullable = false)
    private String lastModifiedBy;

    @JsonIgnoreProperties(value = { "policyUsersType", "policies", "addresses" }, allowSetters = true)
    @OneToOne(mappedBy = "policyUsersType")
    private PolicyUsers policyUsers;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PolicyUsersType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public PolicyUsersType name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastModified() {
        return this.lastModified;
    }

    public PolicyUsersType lastModified(String lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public PolicyUsersType lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public PolicyUsers getPolicyUsers() {
        return this.policyUsers;
    }

    public void setPolicyUsers(PolicyUsers policyUsers) {
        if (this.policyUsers != null) {
            this.policyUsers.setPolicyUsersType(null);
        }
        if (policyUsers != null) {
            policyUsers.setPolicyUsersType(this);
        }
        this.policyUsers = policyUsers;
    }

    public PolicyUsersType policyUsers(PolicyUsers policyUsers) {
        this.setPolicyUsers(policyUsers);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PolicyUsersType)) {
            return false;
        }
        return id != null && id.equals(((PolicyUsersType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PolicyUsersType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
