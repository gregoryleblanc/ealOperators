package edu.ohio.inpp.acceleratorOperators;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class Operator {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id; 

    private String fullName;
    private String loginName;
    private Boolean active;
    private LocalDateTime updated;
    private LocalDateTime created;
    private Integer createdBy;
    private String comments;

    @OneToMany
    @JoinColumn(name = "owner")
    @Fetch(FetchMode.JOIN)
    private Set<Phone> phones;

    @OneToMany
    @JoinColumn(name = "owner")
    @Fetch(FetchMode.JOIN)
    private Set<Email> email;

    @OneToMany
    @JoinColumn(name = "owner")
    @Fetch(FetchMode.JOIN)
    private Set<Address> addresses;

    @OneToMany
    @JoinColumn(name = "operator")
    @Fetch(FetchMode.JOIN)
    private Set<Training> trainings;

    // Getters and Setters

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Boolean isActive() {
        return this.active;
    }

    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getUpdated() {
        return this.updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public LocalDateTime getCreated() {
        return this.created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Integer getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Set<Phone> getPhones() {
        return this.phones;
    }

    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }

    public Set<Email> getEmail() {
        return this.email;
    }

    public void setEmail(Set<Email> email) {
        this.email = email;
    }

    public Set<Address> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Set<Training> getTrainings() {
        return this.trainings;
    }

    public void setTrainings(Set<Training> trainings) {
        this.trainings = trainings;
    }

}
