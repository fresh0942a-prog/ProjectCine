
package com.cine.cine.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

/**

 * @author AlexanderParallelsVM
 */
@Data
@Entity
@Table(name = "snack")
public class Snack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSnack;
    @NotBlank(message = "{validacion.requerido}")
    @Size(max = 100, message = "{validacion.longitud}")
    private String nombre;

    @NotBlank(message = "{validacion.requerido}")
    @Size(max = 255, message = "{validacion.longitud}")
    private String descripcion;

    @NotNull(message = "{validacion.requerido}")
    @DecimalMin(value = "0.01", inclusive = true, message = "{validacion.positivo}")
    private Double precio;

    private String rutaImagen;
    private boolean activo;

    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;
}