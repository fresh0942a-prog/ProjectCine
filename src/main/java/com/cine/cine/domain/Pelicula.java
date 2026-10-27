
package com.cine.cine.domain;

import jakarta.persistence.*;
import lombok.Data;

/**

 * @author barqu
 */
@Data
@Entity
@Table(name = "pelicula")
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPelicula;
    private String titulo;
    private String sinopsis;
    private String genero;
    private String clasificacion;
    private Integer duracion;
    private String rutaImagen;
    private boolean activo;

    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;
}
