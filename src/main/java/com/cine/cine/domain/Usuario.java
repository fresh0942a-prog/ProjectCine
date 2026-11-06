package com.cine.cine.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    @NotBlank(message = "{validacion.requerido}")
    @Size(max = 100, message = "{validacion.longitud}")
    private String nombre;

    @NotBlank(message = "{validacion.requerido}")
    @Size(max = 100, message = "{validacion.longitud}")
    @Email(regexp = "^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$", message = "{validacion.correo}")
    private String correo;

    @NotBlank(message = "{validacion.requerido}")
    @Size(min = 6, max = 64, message = "{validacion.contrasena}")
    private String contrasena;

    private String rol;
    private boolean activo;

    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;
}