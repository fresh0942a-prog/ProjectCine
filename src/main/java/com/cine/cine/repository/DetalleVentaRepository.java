package com.cine.cine.repository;

import com.cine.cine.domain.DetalleVenta;
import com.cine.cine.domain.Funcion;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Integer> {

    public DetalleVenta findTopByOrderByIdVentaDesc();

    public long countByFuncionAndAsientoIsNotNull(Funcion funcion);
}