package com.cine.cine.controller;

import com.cine.cine.domain.Usuario;
import com.cine.cine.service.UsuarioService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class UsuarioActualAdvice {

    private final UsuarioService usuarioService;

    public UsuarioActualAdvice(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @ModelAttribute("usuarioActual")
    public Usuario usuarioActual() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            return null;
        }
        return usuarioService.getUsuarioPorCorreo(auth.getName());
    }
}