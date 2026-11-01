
package com.cine.cine.domain;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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

    @NotBlank(message = "{validacion.requerido}")
    @Size(max = 50, message = "{validacion.longitud}")
    private String nombre;

    @NotNull(message = "{validacion.requerido}")
    @Min(value = 1, message = "{validacion.positivo}")
    private Integer filas;

    @NotNull(message = "{validacion.requerido}")
    @Min(value = 1, message = "{validacion.positivo}")
    private Integer asientosPorFila;

    private boolean activo;

    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;

    @Transient
    public Integer getCapacidad() {
        if (filas == null || asientosPorFila == null) {
            return 0;
        }
        return filas * asientosPorFila;
    }
}

