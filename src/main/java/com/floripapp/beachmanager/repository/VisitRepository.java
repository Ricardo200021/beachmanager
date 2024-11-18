package com.floripapp.beachmanager.repository;

import com.floripapp.beachmanager.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    // Pode adicionar métodos personalizados se necessário
}
