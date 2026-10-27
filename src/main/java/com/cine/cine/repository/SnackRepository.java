
package com.cine.cine.repository;

import com.cine.cine.domain.Snack;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SnackRepository extends JpaRepository<Snack, Integer> {

    public List<Snack> findByActivoTrue();
}
