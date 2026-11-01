/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cine.cine.service;

import com.cine.cine.domain.Sala;
import com.cine.cine.repository.FuncionRepository;
import com.cine.cine.repository.SalaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SalaService {

    private final SalaRepository salaRepository;
    private final FuncionRepository funcionRepository;

    public SalaService(SalaRepository salaRepository, FuncionRepository funcionRepository) {
        this.salaRepository = salaRepository;
        this.funcionRepository = funcionRepository;
    }

    @Transactional(readOnly = true)
    public List<Sala> getSalas(boolean activos) {
        if (activos) {
            return salaRepository.findByActivoTrue();
        }
        return salaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Sala> getSala(Integer idSala) {
        return salaRepository.findById(idSala);
    }

    @Transactional
    public void save(Sala sala) {
        salaRepository.save(sala);
    }

    @Transactional
    public boolean delete(Integer idSala) {
        if (funcionRepository.existsBySala_IdSala(idSala)) {
            return false;
        }
        salaRepository.deleteById(idSala);
        return true;
    }
}

