
package com.cine.cine.domain;

import jakarta.persistence.*;
import lombok.Data;

/**

 * @author barqu
 */
@Data
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;
    private String nombre;
    private String correo;
    private String contrasena;
    private String rol;
    private boolean activo;

    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;
}
