package com.cine.cine.service;

import com.cine.cine.domain.Empresa;
import com.cine.cine.domain.Usuario;
import com.cine.cine.repository.EmpresaRepository;
import com.cine.cine.repository.UsuarioRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }


    @Transactional(readOnly = true)
    public List<Usuario> getUsuariosVisibles(Usuario actual) {
        if ("ADMIN_GENERAL".equals(actual.getRol())) {
            return usuarioRepository.findAll();
        }
        if (actual.getEmpresa() == null) {
            return List.of();
        }
        return usuarioRepository.findByEmpresa(actual.getEmpresa());
    }

 
    @Transactional(readOnly = true)
    public Usuario getUsuarioGestionable(Usuario actual, Integer idUsuario) {
        if (idUsuario == null) {
            return null;
        }
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (usuario == null) {
            return null;
        }
        if ("ADMIN_GENERAL".equals(actual.getRol())) {
            return usuario;
        }
        boolean mismaEmpresa = actual.getEmpresa() != null && usuario.getEmpresa() != null
                && actual.getEmpresa().getIdEmpresa().equals(usuario.getEmpresa().getIdEmpresa());
        return mismaEmpresa ? usuario : null;
    }

    public List<String> getRolesAsignables(Usuario actual) {
        if ("ADMIN_GENERAL".equals(actual.getRol())) {
            return List.of("ADMIN_GENERAL", "ADMIN", "CLIENTE");
        }
        return List.of("ADMIN", "CLIENTE");
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> getUsuario(Integer idUsuario) {
        return usuarioRepository.findById(idUsuario);
    }

    @Transactional(readOnly = true)
    public Usuario getUsuarioPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    @Transactional
    public void save(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void delete(Integer idUsuario) {
        usuarioRepository.deleteById(idUsuario);
    }

    @Transactional
    public void registrar(Usuario usuario) {
        String correo = usuario.getCorreo().trim().toLowerCase();
        if (usuarioRepository.existsByCorreo(correo)) {
            throw new IllegalArgumentException("registro.errorCorreo");
        }
        usuario.setIdUsuario(null);
        usuario.setNombre(usuario.getNombre().trim());
        usuario.setCorreo(correo);
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        usuario.setRol("CLIENTE");
        usuario.setActivo(true);
        usuario.setEmpresa(null);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void crear(Usuario actual, Usuario nuevo) {
        if (!getRolesAsignables(actual).contains(nuevo.getRol())) {
            throw new IllegalArgumentException("usuario.errorRol");
        }
        Empresa empresa = empresaPara(actual, nuevo);
        if ("ADMIN".equals(nuevo.getRol()) && empresa == null) {
            throw new IllegalArgumentException("usuario.errorEmpresa");
        }

        String correo = nuevo.getCorreo().trim().toLowerCase();
        if (usuarioRepository.existsByCorreo(correo)) {
            throw new IllegalArgumentException("registro.errorCorreo");
        }

        nuevo.setIdUsuario(null);
        nuevo.setNombre(nuevo.getNombre().trim());
        nuevo.setCorreo(correo);
        nuevo.setContrasena(passwordEncoder.encode(nuevo.getContrasena()));
        nuevo.setEmpresa(empresa);
        usuarioRepository.save(nuevo);
    }


    @Transactional
    public void modificar(Usuario actual, Usuario datos) {
        Usuario existente = getUsuarioGestionable(actual, datos.getIdUsuario());
        if (existente == null) {
            throw new IllegalArgumentException("usuario.errorNoExiste");
        }
        if (!getRolesAsignables(actual).contains(datos.getRol())) {
            throw new IllegalArgumentException("usuario.errorRol");
        }
        Empresa empresa = empresaPara(actual, datos);
        if ("ADMIN".equals(datos.getRol()) && empresa == null) {
            throw new IllegalArgumentException("usuario.errorEmpresa");
        }

        String correo = datos.getCorreo().trim().toLowerCase();
 
        if (existente.getIdUsuario().equals(actual.getIdUsuario()) && !correo.equals(existente.getCorreo())) {
            throw new IllegalStateException("usuario.errorCorreoPropio");
        }
        if (usuarioRepository.existsByCorreoAndIdUsuarioNot(correo, existente.getIdUsuario())) {
            throw new IllegalArgumentException("registro.errorCorreo");
        }

        if (dejaEmpresaSinAdmin(existente, datos.getRol(), empresa, existente.isActivo())) {
            throw new IllegalStateException("usuario.errorUltimoAdmin");
        }
        if (dejaSinAdminGeneral(existente, datos.getRol(), existente.isActivo())) {
            throw new IllegalStateException("usuario.errorUltimoAdminGeneral");
        }

        existente.setNombre(datos.getNombre().trim());
        existente.setCorreo(correo);
        existente.setRol(datos.getRol());
        existente.setEmpresa(empresa);
        usuarioRepository.save(existente);
    }


    @Transactional
    public void cambiarEstado(Usuario actual, Integer idUsuario) {
        Usuario usuario = getUsuarioGestionable(actual, idUsuario);
        if (usuario == null) {
            throw new IllegalArgumentException("usuario.errorNoExiste");
        }
        boolean nuevoEstado = !usuario.isActivo();
        if (dejaEmpresaSinAdmin(usuario, usuario.getRol(), usuario.getEmpresa(), nuevoEstado)) {
            throw new IllegalStateException("usuario.errorUltimoAdmin");
        }
        if (dejaSinAdminGeneral(usuario, usuario.getRol(), nuevoEstado)) {
            throw new IllegalStateException("usuario.errorUltimoAdminGeneral");
        }
        usuario.setActivo(nuevoEstado);
        usuarioRepository.save(usuario);
    }

    private boolean dejaEmpresaSinAdmin(Usuario usuario, String nuevoRol, Empresa nuevaEmpresa, boolean nuevoActivo) {
        if (!"ADMIN".equals(usuario.getRol()) || !usuario.isActivo() || usuario.getEmpresa() == null) {
            return false;
        }
        boolean sigueSiendoAdmin = nuevoActivo
                && "ADMIN".equals(nuevoRol)
                && nuevaEmpresa != null
                && nuevaEmpresa.getIdEmpresa().equals(usuario.getEmpresa().getIdEmpresa());
        if (sigueSiendoAdmin) {
            return false;
        }
        return usuarioRepository.countByEmpresaAndRolAndActivoTrue(usuario.getEmpresa(), "ADMIN") <= 1;
    }


    private boolean dejaSinAdminGeneral(Usuario usuario, String nuevoRol, boolean nuevoActivo) {
        if (!"ADMIN_GENERAL".equals(usuario.getRol()) || !usuario.isActivo()) {
            return false;
        }
        boolean sigueSiendoAdminGeneral = nuevoActivo && "ADMIN_GENERAL".equals(nuevoRol);
        if (sigueSiendoAdminGeneral) {
            return false;
        }
        return usuarioRepository.countByRolAndActivoTrue("ADMIN_GENERAL") <= 1;
    }

    private Empresa empresaPara(Usuario actual, Usuario datos) {
        if (!"ADMIN_GENERAL".equals(actual.getRol())) {
            return actual.getEmpresa();
        }
        if ("ADMIN_GENERAL".equals(datos.getRol())) {
            return null;
        }
        if (datos.getEmpresa() != null && datos.getEmpresa().getIdEmpresa() != null) {
            return empresaRepository.findById(datos.getEmpresa().getIdEmpresa()).orElse(null);
        }
        return null;
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo.trim().toLowerCase());
        if (usuario == null) {
            throw new UsernameNotFoundException("Credenciales invalidas");
        }
        return User.withUsername(usuario.getCorreo())
                .password(usuario.getContrasena())
                .roles(usuario.getRol())
                .disabled(!usuario.isActivo())
                .build();
    }
}