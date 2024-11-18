package com.floripapp.beachmanager.repository;

import com.floripapp.beachmanager.model.Beach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeachRepository extends JpaRepository<Beach, Long> {
    
    // Método para buscar praias pelo nome
    List<Beach> findByName(String name);
}
