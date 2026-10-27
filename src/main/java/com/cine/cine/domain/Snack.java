
package com.cine.cine.domain;

import jakarta.persistence.*;
import lombok.Data;

/**

 * @author barqu
 */
@Data
@Entity
@Table(name = "snack")
public class Snack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSnack;
    private String nombre;
    private String descripcion;
    private Double precio;
    private String rutaImagen;
    private boolean activo;

    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;
}