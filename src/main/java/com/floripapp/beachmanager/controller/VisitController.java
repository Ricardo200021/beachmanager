package com.floripapp.beachmanager.controller;

import com.floripapp.beachmanager.model.Beach;
import com.floripapp.beachmanager.model.Visitor;
import com.floripapp.beachmanager.model.Visit;
import com.floripapp.beachmanager.service.VisitorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Set;

@RestController
@RequestMapping("/visitors")
public class VisitController {

    private final VisitorService visitorService;

    public VisitController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    // Endpoint para listar praias visitadas por um visitante específico
    @GetMapping("/{visitorId}/visited-beaches")
    public ResponseEntity<Set<Beach>> getVisitedBeaches(@PathVariable Long visitorId) {
        try {
            Set<Beach> visitedBeaches = visitorService.findVisitedBeachesByVisitorId(visitorId);
            if (visitedBeaches.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(visitedBeaches);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Endpoint para criar um visitante e associar uma praia
    @PostMapping
    public ResponseEntity<Visitor> createVisitorWithBeach(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam Long beachId) {
        try {
            Visitor visitor = visitorService.createVisitorWithBeach(name, email, beachId);
            return ResponseEntity.ok(visitor);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Endpoint para remover uma praia da lista de praias visitadas por um visitante
    @DeleteMapping("/{visitorId}/visited-beaches/{beachId}")
    public ResponseEntity<Void> removeBeachFromVisitor(
            @PathVariable Long visitorId,
            @PathVariable Long beachId) {
        try {
            visitorService.removeBeachFromVisitor(visitorId, beachId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Endpoint para registrar uma visita de um visitante a uma praia
    @PostMapping("/{visitorId}/visited-beaches/{beachId}")
    public ResponseEntity<Visit> registerVisit(
            @PathVariable Long visitorId,
            @PathVariable Long beachId,
            @RequestParam String visitDate) {
        try {
            // Converte a string para LocalDate
            LocalDate visitDateParsed = LocalDate.parse(visitDate);
            
            // Registra a visita
            Visit visit = visitorService.addVisit(visitorId, beachId, visitDateParsed);
            return ResponseEntity.ok(visit);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
