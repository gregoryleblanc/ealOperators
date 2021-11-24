package edu.ohio.inpp.acceleratorOperators;

import java.time.Duration;
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
public class Certification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id; 

    private String name;
    private String shortName;
    private String comment;
    private Duration duration;

    @OneToMany
    @JoinColumn(name = "certification")
    @Fetch(FetchMode.JOIN)
    private Set<Training> trainings;

    // Getters and Setters

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Duration getDuration() {
        return this.duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Set<Training> getTrainings() {
        return this.trainings;
    }

    public void setTrainings(Set<Training> trainings) {
        this.trainings = trainings;
    }

}
