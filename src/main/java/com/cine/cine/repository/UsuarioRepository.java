
package com.cine.cine.repository;

import com.cine.cine.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author barqu
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    public Usuario findByCorreoAndContrasenaAndActivoTrue(String correo, String contrasena);

    public boolean existsByCorreo(String correo);
}
