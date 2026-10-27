
package com.cine.cine.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "detalle_venta")
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDetalleVenta;
    private Integer idVenta;
    private LocalDateTime fecha;
    private String asiento;
    private Integer cantidad;
    private Double precio;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_funcion")
    private Funcion funcion;

    @ManyToOne
    @JoinColumn(name = "id_snack")
    private Snack snack;
}
