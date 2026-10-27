
package com.cine.cine.domain;

import jakarta.persistence.*;
import lombok.Data;

/**

 * @author barqu
 */
@Data
@Entity
@Table(name = "empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEmpresa;
    private String nombre;
    private String identificador;
    private String ubicacion;
    private boolean activo;
}
