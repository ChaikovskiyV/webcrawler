package com.chaikouski.webcrawler.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "terms")
public class Term {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String termName;
    private long repetitionsNumber;
    @ManyToMany(mappedBy = "terms")
    @JsonIgnore
    private List<Seed> seeds;

    public Term(String termName, long repetitionsNumber) {
        this.termName = termName;
        this.repetitionsNumber = repetitionsNumber;
    }

    public Term() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public long getRepetitionsNumber() {
        return repetitionsNumber;
    }

    public void setRepetitionsNumber(long repetitionsNumber) {
        this.repetitionsNumber = repetitionsNumber;
    }

    public List<Seed> getSeeds() {
        return seeds;
    }

    public void setSeeds(List<Seed> seeds) {
        this.seeds = seeds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Term term = (Term) o;
        return repetitionsNumber == term.repetitionsNumber && Objects.equals(termName, term.termName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(termName, repetitionsNumber);
    }

    @Override
    public String toString() {
        return new StringBuilder("Term{")
                .append("id=")
                .append(id)
                .append(", termName='")
                .append(termName)
                .append("', repetitionsNumber=")
                .append(repetitionsNumber)
                .append(", seeds=")
                .append(seeds)
                .append('}')
                .toString();
    }
}