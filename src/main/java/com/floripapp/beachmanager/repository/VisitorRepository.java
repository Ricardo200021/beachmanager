package com.floripapp.beachmanager.repository;

import com.floripapp.beachmanager.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {
    // Métodos adicionais podem ser definidos aqui, se necessário
}
