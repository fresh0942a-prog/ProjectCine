package com.cine.cine.service;

import com.cine.cine.domain.Funcion;
import com.cine.cine.repository.FuncionRepository;
import java.time.LocalDateTime;
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
        if (funcion.getSala() == null || funcion.getPelicula() == null || funcion.getFechaHora() == null) {
            throw new IllegalArgumentException("Debe seleccionar película, sala, fecha y hora.");
        }
        LocalDateTime inicio = funcion.getFechaHora();
        LocalDateTime fin = inicio.plusMinutes(funcion.getPelicula().getDuracion() == null ? 0 : funcion.getPelicula().getDuracion());
        List<Funcion> funcionesSala = funcionRepository.findBySala(funcion.getSala());
        for (Funcion existente : funcionesSala) {
            if (funcion.getIdFuncion() != null && funcion.getIdFuncion().equals(existente.getIdFuncion())) {
                continue;
            }
            if (existente.getFechaHora() == null || existente.getPelicula() == null) {
                continue;
            }
            int duracionExistente = existente.getPelicula().getDuracion() == null ? 0 : existente.getPelicula().getDuracion();
            LocalDateTime inicioExistente = existente.getFechaHora();
            LocalDateTime finExistente = inicioExistente.plusMinutes(duracionExistente);
            if (inicio.isBefore(finExistente) && fin.isAfter(inicioExistente)) {
                throw new IllegalArgumentException("La sala ya tiene una función en un horario incompatible.");
            }
        }
        funcionRepository.save(funcion);
    }

    @Transactional
    public void delete(Integer idFuncion) {
        funcionRepository.deleteById(idFuncion);
    }
}
