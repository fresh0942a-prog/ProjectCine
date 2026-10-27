
package com.cine.cine.repository;

import com.cine.cine.domain.Empresa;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author barqu
 */
public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

    public List<Empresa> findByActivoTrue();

    public Empresa findByIdentificador(String identificador);
}
