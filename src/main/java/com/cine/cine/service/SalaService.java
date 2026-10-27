/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cine.cine.service;

import com.cine.cine.domain.Sala;
import com.cine.cine.repository.SalaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SalaService {

    private final SalaRepository salaRepository;

    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
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
    public void delete(Integer idSala) {
        salaRepository.deleteById(idSala);
    }
}
