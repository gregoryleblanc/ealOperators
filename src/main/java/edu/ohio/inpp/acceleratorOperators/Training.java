package edu.ohio.inpp.acceleratorOperators;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id; 

    private LocalDate date;
    private Integer operator;
    private Integer trainer;
    private LocalDateTime entered;
    private Integer certification;

    // Getters and Setters

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getOperator() {
        return this.operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public Integer getTrainer() {
        return this.trainer;
    }

    public void setTrainer(Integer trainer) {
        this.trainer = trainer;
    }

    public LocalDateTime getEntered() {
        return this.entered;
    }

    public void setEntered(LocalDateTime entered) {
        this.entered = entered;
    }

    public Integer getCertification() {
        return this.certification;
    }

    public void setCertification(Integer certification) {
        this.certification = certification;
    }

}
