package com.cine.cine.controller;

import com.cine.cine.domain.Usuario;
import com.cine.cine.service.UsuarioService;
import jakarta.validation.Valid;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/registro")
@RequiredArgsConstructor
public class RegistroController {

    private final UsuarioService usuarioService;
    private final MessageSource messageSource;

    @GetMapping
    public String formulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario/registro";
    }

    @PostMapping
    public String registrar(@Valid Usuario usuario, BindingResult bindingResult, Model model, Locale locale) {
        if (bindingResult.hasErrors()) {
            return "usuario/registro";
        }
        try {
            usuarioService.registrar(usuario);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", messageSource.getMessage(e.getMessage(), null, locale));
            return "usuario/registro";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("error", messageSource.getMessage("registro.errorCorreo", null, locale));
            return "usuario/registro";
        }
        return "redirect:/login?registrado";
    }
}