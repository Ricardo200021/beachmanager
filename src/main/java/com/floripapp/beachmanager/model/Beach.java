package com.floripapp.beachmanager.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Beach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String name;

    @NotBlank
    @Size(max = 255)
    private String location;

    @NotBlank
    @Size(max = 255)
    private String seaConditions;

    // Construtor padrão (necessário para o Hibernate)
    public Beach() {
    }

    // Construtor com parâmetros
    public Beach(String name, String location, String seaConditions) {
        this.name = name;
        this.location = location;
        this.seaConditions = seaConditions;
    }
    
    // Relacionamento bidirecional com Visitor
    @ManyToMany(mappedBy = "visitedBeaches")
    private Set<Visitor> visitors = new HashSet<>();
   // private List<Visitor> visitors = new ArrayList<>();

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSeaConditions() {
        return seaConditions;
    }

    public void setSeaConditions(String seaConditions) {
        this.seaConditions = seaConditions;
    }
    
    public Set<Visitor> getVisitors() {
        return visitors;
    }

    public void setVisitors(Set<Visitor> visitors) {
        this.visitors = visitors;
    }

	/*public Beach orElseThrow(Object object) {
		// TODO Auto-generated method stub
		return null;
	} */

	
}
