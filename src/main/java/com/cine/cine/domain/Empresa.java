
package com.cine.cine.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

/**

 * @author AlexanderParallelsVM
 */
@Data
@Entity
@Table(name = "empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEmpresa;
    @NotBlank(message = "{validacion.requerido}")
    @Size(max = 100, message = "{validacion.longitud}")
    private String nombre;

    @NotBlank(message = "{validacion.requerido}")
    @Size(max = 50, message = "{validacion.longitud}")
    private String identificador;

    @Size(max = 100, message = "{validacion.longitud}")
    private String ubicacion;
    private boolean activo;
}