package com.floripapp.beachmanager.controller;

import com.floripapp.beachmanager.exception.BeachNotFoundException;
import com.floripapp.beachmanager.model.Beach;
import com.floripapp.beachmanager.service.BeachService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/beach")
public class BeachController {

    private final BeachService beachService;

    @Autowired
    public BeachController(BeachService beachService) {
        this.beachService = beachService;
    }

    // Endpoint para criar praias de exemplo
    @PostMapping("/create-beach")
    public ResponseEntity<String> createSampleBeaches() {
        beachService.createSampleBeaches();
        return ResponseEntity.status(HttpStatus.CREATED).body("Praias cadastradas com sucesso.");
    }

    // Endpoint para criar uma nova praia
    @PostMapping
    public ResponseEntity<Beach> createBeach(@Valid @RequestBody Beach beach) {
        Beach createdBeach = beachService.create(beach);
        return new ResponseEntity<>(createdBeach, HttpStatus.CREATED);
    }

    // Endpoint para buscar uma praia por ID
    @GetMapping("/{id}")
    public ResponseEntity<Beach> getBeachById(@PathVariable Long id) {
        try {
            Beach beach = beachService.findById(id);
            return new ResponseEntity<>(beach, HttpStatus.OK);
        } catch (BeachNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint para atualizar uma praia
    @PutMapping("/{id}")
    public ResponseEntity<Beach> updateBeach(@PathVariable Long id, @RequestBody Beach beach) {
        try {
            beach.setId(id);  // Garantir que o ID da praia seja o mesmo do PathVariable
            Beach updatedBeach = beachService.update(beach);
            return new ResponseEntity<>(updatedBeach, HttpStatus.OK);
        } catch (BeachNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint para excluir uma praia
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBeach(@PathVariable Long id) {
        try {
            beachService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (BeachNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
