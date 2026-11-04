
package com.cine.cine.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

/**

 * @author AlexanderParallelsVM
 */
@Data
@Entity
@Table(name = "pelicula")
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPelicula;
    @NotBlank(message = "{validacion.requerido}")
    @Size(max = 100, message = "{validacion.longitud}")
    private String titulo;

    @NotBlank(message = "{validacion.requerido}")
    private String sinopsis;

    @NotBlank(message = "{validacion.requerido}")
    @Size(max = 30, message = "{validacion.longitud}")
    private String genero;

    @NotBlank(message = "{validacion.requerido}")
    @Size(max = 10, message = "{validacion.longitud}")
    private String clasificacion;

    @NotNull(message = "{validacion.requerido}")
    @Min(value = 1, message = "{validacion.positivo}")
    private Integer duracion;

    private String rutaImagen;
    private boolean activo;

    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;
}