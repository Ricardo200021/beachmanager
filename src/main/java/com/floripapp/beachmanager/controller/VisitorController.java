package com.floripapp.beachmanager.controller;

import com.floripapp.beachmanager.model.Beach;
import com.floripapp.beachmanager.model.Visitor;
import com.floripapp.beachmanager.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/visitor")
public class VisitorController {

    private final VisitorService visitorService;

    @Autowired
    public VisitorController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    // Endpoint para criar um visitante com nome, email e praia visitada
    @PostMapping("/create")
    public ResponseEntity<Visitor> createVisitor(@RequestBody VisitorRequest visitorRequest) {
        try {
            // Cria e salva o visitante
            Visitor savedVisitor = visitorService.createVisitorWithBeach(
                    visitorRequest.getName(), 
                    visitorRequest.getEmail(), 
                    visitorRequest.getBeachId()
            );
            return ResponseEntity.ok(savedVisitor); // Retorna o visitante criado
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // Retorna erro se houver algum problema
        }
    }

    // Classe auxiliar para receber a requisição no formato JSON
    public static class VisitorRequest {
        private String name;
        private String email;
        private Long beachId;

        // Getters e Setters
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

        public Long getBeachId() {
            return beachId;
        }

        public void setBeachId(Long beachId) {
            this.beachId = beachId;
        }
    }
}
