package com.cine.cine.controller;

import com.cine.cine.domain.Usuario;
import com.cine.cine.service.EmpresaService;
import com.cine.cine.service.UsuarioService;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final EmpresaService empresaService;
    private final MessageSource messageSource;
    
    @GetMapping("/listado")
    public String listado(Principal principal, Model model) {
        Usuario actual = usuarioService.getUsuarioPorCorreo(principal.getName());
        model.addAttribute("usuarios", usuarioService.getUsuariosVisibles(actual));
        return "usuario/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Principal principal, Model model) {
        Usuario nuevo = new Usuario();
        nuevo.setActivo(true);
        nuevo.setRol("ADMIN");
        model.addAttribute("usuario", nuevo);
        return formulario(principal, model);
    }

    @GetMapping("/modificar/{idUsuario}")
    public String modificar(@PathVariable Integer idUsuario, Principal principal, Model model) {
        Usuario actual = usuarioService.getUsuarioPorCorreo(principal.getName());
        Usuario usuario = usuarioService.getUsuarioGestionable(actual, idUsuario);
        if (usuario == null) {
            return "redirect:/usuario/listado";
        }
        model.addAttribute("usuario", usuario);
        return formulario(principal, model);
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Usuario usuario, BindingResult bindingResult, Principal principal,
            Model model, Locale locale) {
        boolean edicion = usuario.getIdUsuario() != null;
        
        int erroresIgnorados = edicion ? bindingResult.getFieldErrorCount("contrasena") : 0;
        if (bindingResult.getErrorCount() > erroresIgnorados) {
            return formulario(principal, model);
        }
        Usuario actual = usuarioService.getUsuarioPorCorreo(principal.getName());
        try {
            if (edicion) {
                usuarioService.modificar(actual, usuario);
            } else {
                usuarioService.crear(actual, usuario);
            }
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", messageSource.getMessage(e.getMessage(), null, locale));
            return formulario(principal, model);
        } catch (IllegalStateException e) {
            model.addAttribute("error", messageSource.getMessage(e.getMessage(), null, locale));
            return formulario(principal, model);
        }
        return "redirect:/usuario/listado";
    }

    @PostMapping("/estado/{idUsuario}")
    public String cambiarEstado(@PathVariable Integer idUsuario, Principal principal,
            RedirectAttributes redirect, Locale locale) {
        Usuario actual = usuarioService.getUsuarioPorCorreo(principal.getName());
        try {
            usuarioService.cambiarEstado(actual, idUsuario);
        } catch (IllegalArgumentException e) {
            redirect.addFlashAttribute("error", messageSource.getMessage(e.getMessage(), null, locale));
        } catch (IllegalStateException e) {
            redirect.addFlashAttribute("error", messageSource.getMessage(e.getMessage(), null, locale));
        }
        return "redirect:/usuario/listado";
    }

    private String formulario(Principal principal, Model model) {
        Usuario actual = usuarioService.getUsuarioPorCorreo(principal.getName());
        model.addAttribute("roles", usuarioService.getRolesAsignables(actual));
        model.addAttribute("esAdminGeneral", "ADMIN_GENERAL".equals(actual.getRol()));
        model.addAttribute("empresas", empresaService.getEmpresas(true));
        return "usuario/modifica";
    }
}