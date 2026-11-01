package com.cine.cine.controller;

import com.cine.cine.domain.Funcion;
import com.cine.cine.service.FuncionService;
import com.cine.cine.service.PeliculaService;
import com.cine.cine.service.SalaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/funcion")
public class FuncionController {

    private final FuncionService funcionService;
    private final PeliculaService peliculaService;
    private final SalaService salaService;

    public FuncionController(FuncionService funcionService, PeliculaService peliculaService, SalaService salaService) {
        this.funcionService = funcionService;
        this.peliculaService = peliculaService;
        this.salaService = salaService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        model.addAttribute("funciones", funcionService.getFunciones());
        return "funcion/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("funcion", new Funcion());
        cargarListas(model);
        return "funcion/modifica";
    }

    @GetMapping("/modificar/{idFuncion}")
    public String modificar(@PathVariable Integer idFuncion, Model model, RedirectAttributes redirectAttributes) {
        var funcion = funcionService.getFuncion(idFuncion);
        if (funcion.isEmpty()) {
            redirectAttributes.addFlashAttribute("mensajeError", "La función solicitada no existe.");
            return "redirect:/funcion/listado";
        }
        model.addAttribute("funcion", funcion.get());
        cargarListas(model);
        return "funcion/modifica";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Funcion funcion, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            cargarListas(model);
            return "funcion/modifica";
        }
        try {
            funcionService.save(funcion);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorHorario", e.getMessage());
            cargarListas(model);
            return "funcion/modifica";
        }
        return "redirect:/funcion/listado";
    }

    private void cargarListas(Model model) {
        model.addAttribute("peliculas", peliculaService.getPeliculas(true));
        model.addAttribute("salas", salaService.getSalas(true));
    }
}
