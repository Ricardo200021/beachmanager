package com.floripapp.beachmanager.service;

import com.floripapp.beachmanager.model.Visit;
import com.floripapp.beachmanager.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VisitService {

    private final VisitRepository visitRepository;

    @Autowired
    public VisitService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    // Criar nova visita
    public Visit createVisit(Visit visit) {
        return visitRepository.save(visit);
    }

    // Obter todas as visitas
    public List<Visit> getAllVisits() {
        return visitRepository.findAll();
    }

    // Obter visita por ID
    public Optional<Visit> getVisitById(Long id) {
        return visitRepository.findById(id);
    }

    // Atualizar visita
    public Visit updateVisit(Long id, Visit visitDetails) {
        return visitRepository.findById(id)
                .map(visit -> {
                    visit.setVisitor(visitDetails.getVisitor());
                    visit.setBeach(visitDetails.getBeach());
                    visit.setVisitDate(visitDetails.getVisitDate());
                    return visitRepository.save(visit);
                })
                .orElseThrow(() -> new RuntimeException("Visit not found with id " + id));
    }

    // Deletar visita
    public void deleteVisit(Long id) {
        visitRepository.deleteById(id);
    }
}
