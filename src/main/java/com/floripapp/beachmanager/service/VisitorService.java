package com.floripapp.beachmanager.service;

import com.floripapp.beachmanager.model.Beach;
import com.floripapp.beachmanager.model.Visitor;
import com.floripapp.beachmanager.model.Visit;
import com.floripapp.beachmanager.repository.VisitorRepository;
import com.floripapp.beachmanager.repository.VisitRepository; // Adicionado para a manipulação das visitas
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Service
public class VisitorService {

    private final VisitorRepository visitorRepository;
    private final BeachService beachService; // Para buscar a praia corretamente
    private final VisitRepository visitRepository; // Repositório para manipulação da visita

    @Autowired
    public VisitorService(VisitorRepository visitorRepository, BeachService beachService, VisitRepository visitRepository) {
        this.visitorRepository = visitorRepository;
        this.beachService = beachService;
        this.visitRepository = visitRepository;
    }

    // Buscar um visitante por ID
    @Transactional(readOnly = true)
    public Visitor findById(Long id) {
        Optional<Visitor> visitor = visitorRepository.findById(id);
        if (visitor.isEmpty()) {
            throw new RuntimeException("Visitor not found with id: " + id);
        }
        return visitor.get();
    }

    // Listar todas as praias visitadas por um visitante específico
    @Transactional(readOnly = true)
    public Set<Beach> findVisitedBeachesByVisitorId(Long visitorId) {
        Visitor visitor = findById(visitorId); // Verifica se o visitante existe
        return visitor.getVisitedBeaches();
    }

    // Salvar ou atualizar um visitante
    @Transactional
    public Visitor saveOrUpdateVisitor(Visitor visitor) {
        return visitorRepository.save(visitor);
    }

    // Criar um novo visitante e associar uma praia a ele
    @Transactional
    public Visitor createVisitorWithBeach(String name, String email, Long beachId) {
        // Busca a praia no banco de dados
        Beach beach = beachService.findById(beachId);

        // Cria o visitante
        Visitor visitor = new Visitor();
        visitor.setName(name);
        visitor.setEmail(email);

        // Adiciona a praia à lista de praias visitadas
        visitor.getVisitedBeaches().add(beach);

        // Atualiza o relacionamento bidirecional (se necessário)
        beach.getVisitors().add(visitor);

        // Persiste o visitante
        return saveOrUpdateVisitor(visitor);
    }

    // Remover uma praia da lista de praias visitadas de um visitante
    @Transactional
    public Visitor removeBeachFromVisitor(Long visitorId, Long beachId) {
        Visitor visitor = findById(visitorId); // Encontra o visitante
        Set<Beach> visitedBeaches = visitor.getVisitedBeaches();

        // Remove a praia com o ID fornecido
        visitedBeaches.removeIf(beach -> beach.getId().equals(beachId));
        visitor.setVisitedBeaches(visitedBeaches);

        // Atualiza o relacionamento bidirecional (se necessário)
        Beach beach = beachService.findById(beachId);
        beach.getVisitors().remove(visitor);

        return saveOrUpdateVisitor(visitor); // Atualiza o visitante no banco
    }

    // Método para registrar uma visita
    @Transactional
    public Visit addVisit(Long visitorId, Long beachId, LocalDate visitDate) {
        // Encontra o visitante e a praia
        Visitor visitor = findById(visitorId);
        Beach beach = beachService.findById(beachId);

        // Cria o objeto Visit
        Visit visit = new Visit(visitor, beach, visitDate);

        // Salva a visita no banco de dados
        visitRepository.save(visit);

        // Adiciona a visita à lista de praias visitadas do visitante
        visitor.getVisitedBeaches().add(beach);

        // Atualiza o relacionamento bidirecional, se necessário
        beach.getVisitors().add(visitor);

        // Retorna o objeto Visit
        return visit;
    }
}
