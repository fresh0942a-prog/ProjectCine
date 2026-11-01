
package com.cine.cine.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

/**

 * @author barqu
 */
@Data
@Entity
@Table(name = "funcion")
public class Funcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFuncion;

    @NotNull(message = "{validacion.requerido}")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fechaHora;

    @NotNull(message = "{validacion.requerido}")
    @DecimalMin(value = "0.0", inclusive = false, message = "{validacion.positivo}")
    private Double precio;

    @ManyToOne
    @JoinColumn(name = "id_pelicula")
    @NotNull(message = "{validacion.requerido}")
    private Pelicula pelicula;

    @ManyToOne
    @JoinColumn(name = "id_sala")
    @NotNull(message = "{validacion.requerido}")
    private Sala sala;
}
