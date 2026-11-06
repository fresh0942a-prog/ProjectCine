package com.cine.cine.repository;

import com.cine.cine.domain.Empresa;
import com.cine.cine.domain.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    public Usuario findByCorreo(String correo);

    public boolean existsByCorreo(String correo);

    public boolean existsByCorreoAndIdUsuarioNot(String correo, Integer idUsuario);

    public List<Usuario> findByEmpresa(Empresa empresa);

    public long countByEmpresaAndRolAndActivoTrue(Empresa empresa, String rol);

    public long countByRolAndActivoTrue(String rol);
}