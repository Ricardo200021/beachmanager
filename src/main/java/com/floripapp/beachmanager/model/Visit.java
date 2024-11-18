package com.floripapp.beachmanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "visit")
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "visitor_id", nullable = false)
    private Visitor visitor;

    @ManyToOne
    @JoinColumn(name = "beach_id", nullable = false)
    private Beach beach;

    @NotNull
    @Column(name = "visit_date", nullable = false)
    private LocalDate visitDate;

    // Construtores
    public Visit() {}

    public Visit(Visitor visitor, Beach beach, LocalDate visitDate) {
        this.visitor = visitor;
        this.beach = beach;
        this.visitDate = visitDate;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    public Beach getBeach() {
        return beach;
    }

    public void setBeach(Beach beach) {
        this.beach = beach;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    // Override toString() para exibir uma representação adequada da visita
    @Override
    public String toString() {
        return "Visit{" +
                "id=" + id +
                ", visitor=" + visitor +
                ", beach=" + beach +
                ", visitDate=" + visitDate +
                '}';
    }
}
