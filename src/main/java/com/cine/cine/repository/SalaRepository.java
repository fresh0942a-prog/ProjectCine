
package com.cine.cine.repository;

import com.cine.cine.domain.Sala;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaRepository extends JpaRepository<Sala, Integer> {

    public List<Sala> findByActivoTrue();
}
