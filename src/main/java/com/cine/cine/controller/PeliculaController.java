package com.cine.cine.controller;

import com.cine.cine.service.PeliculaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pelicula")
public class PeliculaController {

    private final PeliculaService peliculaService;

    public PeliculaController(PeliculaService peliculaService) {
        this.peliculaService = peliculaService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        model.addAttribute("peliculas", peliculaService.getPeliculas(false));
        return "pelicula/listado";
    }
}