
package com.cine.cine.domain;

import jakarta.persistence.*;
import lombok.Data;

/**

 * @author barqu
 */
@Data
@Entity
@Table(name = "sala")
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSala;
    private String nombre;
    private Integer filas;
    private Integer asientosPorFila;
    private boolean activo;

    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;
}
