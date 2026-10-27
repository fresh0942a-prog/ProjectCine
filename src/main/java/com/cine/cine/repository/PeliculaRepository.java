
package com.cine.cine.repository;

import com.cine.cine.domain.Empresa;
import com.cine.cine.domain.Pelicula;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PeliculaRepository extends JpaRepository<Pelicula, Integer> {

    public List<Pelicula> findByActivoTrue();

    public List<Pelicula> findByEmpresaAndActivoTrue(Empresa empresa);
}
