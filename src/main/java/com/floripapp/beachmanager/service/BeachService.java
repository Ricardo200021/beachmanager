package com.floripapp.beachmanager.service;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.floripapp.beachmanager.exception.BeachNotFoundException;
import com.floripapp.beachmanager.model.Beach;
import com.floripapp.beachmanager.repository.BeachRepository;

@Service
public class BeachService {

    private final BeachRepository beachRepository;

    @Autowired
    public BeachService(BeachRepository beachRepository) {
        this.beachRepository = beachRepository;
    }
    
 // Método que retorna a praia com o id, ou lança exceção se não encontrado
  /*  public Optional<Beach> findById(Long id) {
        return beachRepository.findById(id); // Isso retorna um Optional<Beach>
    }  */

    // Método para criar várias praias no banco
    @Transactional
    public void createSampleBeaches() {
        Beach beach1 = new Beach("Praia Mole", "Florianópolis", "Areia fina e dourada");
        Beach beach2 = new Beach("Barra da Lagoa", "Florianópolis", "Praia popular e tranquila");
        Beach beach3 = new Beach("Joaquina", "Florianópolis", "Praia famosa entre surfistas");

        // Salvando as praias no banco
        beachRepository.saveAll(List.of(beach1, beach2, beach3));
    }

    // Inicialização das praias no banco logo após o start da aplicação
    @PostConstruct
    public void init() {
        createSampleBeaches();
    }

    // Encontrar uma praia por ID
    @Transactional(readOnly = true)
    public Beach findById(Long id) {
        return beachRepository.findById(id)
                .orElseThrow(() -> new BeachNotFoundException(id)); // Lançando exceção personalizada
    }

    // Criar uma nova praia
    @Transactional
    public Beach create(Beach beach) {
        return beachRepository.save(beach);
    }

    // Atualizar informações de uma praia
    @Transactional
    public Beach update(Beach beach) {
        if (!beachRepository.existsById(beach.getId())) {
            throw new BeachNotFoundException(beach.getId());
        }
        return beachRepository.save(beach);
    }

    // Excluir uma praia
    @Transactional
    public void delete(Long id) {
        if (!beachRepository.existsById(id)) {
            throw new BeachNotFoundException(id);
        }
        beachRepository.deleteById(id);
    }
}
