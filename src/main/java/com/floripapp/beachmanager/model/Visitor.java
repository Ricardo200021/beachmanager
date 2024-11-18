package com.floripapp.beachmanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "visitor")
public class Visitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255)
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @NotBlank
    @Email
    @Size(max = 255)
    @Column(name = "email", nullable = false, length = 255, unique = true)
    private String email;

    @ManyToMany
    @JoinTable(
        name = "visitor_beach", 
        joinColumns = @JoinColumn(name = "visitor_id"), 
        inverseJoinColumns = @JoinColumn(name = "beach_id")
    )
    
    private Set<Beach> visitedBeaches = new HashSet<>();
    
  //  private List<Beach> visitedBeaches = new ArrayList<>();

    // Construtores
    public Visitor() {}

    public Visitor(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Beach> getVisitedBeaches() {
        return visitedBeaches;
    }

    public void setVisitedBeaches(Set<Beach> visitedBeaches) {
        this.visitedBeaches = visitedBeaches;
    }

    // Método toString() aprimorado para facilitar a leitura e depuração
    @Override
    public String toString() {
        StringBuilder beaches = new StringBuilder();
        if (visitedBeaches != null && !visitedBeaches.isEmpty()) {
            for (Beach beach : visitedBeaches) {
                beaches.append(beach.getName()).append(", ");
            }
            beaches.setLength(beaches.length() - 2);  // Remove a última vírgula
        } else {
            beaches.append("No beaches visited");
        }
        
        return String.format("Visitor{id=%d, name='%s', email='%s', visitedBeaches=[%s]}",
                id, name, email, beaches.toString());
    }
}
