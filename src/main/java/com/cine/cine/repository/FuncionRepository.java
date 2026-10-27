package com.cine.cine.repository;

import com.cine.cine.domain.Funcion;
import com.cine.cine.domain.Pelicula;
import com.cine.cine.domain.Sala;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author barqu
 */


public interface FuncionRepository extends JpaRepository<Funcion, Integer> {

    public List<Funcion> findByPeliculaAndFechaHoraAfterOrderByFechaHora(Pelicula pelicula, LocalDateTime fecha);

    public boolean existsBySalaAndFechaHora(Sala sala, LocalDateTime fechaHora);
}