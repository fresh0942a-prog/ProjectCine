package com.cine.cine.service;

import com.cine.cine.domain.Funcion;
import com.cine.cine.repository.FuncionRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FuncionService {

    private final FuncionRepository funcionRepository;

    public FuncionService(FuncionRepository funcionRepository) {
        this.funcionRepository = funcionRepository;
    }

    @Transactional(readOnly = true)
    public List<Funcion> getFunciones() {
        return funcionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Funcion> getFuncion(Integer idFuncion) {
        return funcionRepository.findById(idFuncion);
    }

    @Transactional
    public void save(Funcion funcion) {
        funcionRepository.save(funcion);
    }

    @Transactional
    public void delete(Integer idFuncion) {
        funcionRepository.deleteById(idFuncion);
    }
}