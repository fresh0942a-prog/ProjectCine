
package com.cine.cine.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

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
    private LocalDateTime fechaHora;
    private Double precio;

    @ManyToOne
    @JoinColumn(name = "id_pelicula")
    private Pelicula pelicula;

    @ManyToOne
    @JoinColumn(name = "id_sala")
    private Sala sala;
}
