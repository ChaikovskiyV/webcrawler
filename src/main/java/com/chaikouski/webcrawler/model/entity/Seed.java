package com.chaikouski.webcrawler.model.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity(name = "seeds")
public class Seed {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String url;
    @ManyToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinTable(name = "seeds_terms",
            joinColumns = @JoinColumn(name = "seed_id"),
            inverseJoinColumns = @JoinColumn(name = "term_id"))
    private List<Term> terms;

    public Seed(String url, List<Term> terms) {
        this.url = url;
        this.terms = terms;
    }

    public Seed() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String geturl() {
        return url;
    }

    public void seturl(String url) {
        this.url = url;
    }

    public List<Term> getTerms() {
        return terms;
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seed that = (Seed) o;
        return Objects.equals(url, that.url) && Objects.equals(terms, that.terms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, terms);
    }

    @Override
    public String toString() {
        return new StringBuilder("seed{")
                .append("id=")
                .append(id)
                .append(", url='")
                .append(url)
                .append("', terms=")
                .append(terms)
                .append('}')
                .toString();
    }
}