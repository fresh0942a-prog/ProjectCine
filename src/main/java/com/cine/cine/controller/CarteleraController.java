/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cine.cine.controller;

import com.cine.cine.service.PeliculaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author barqu
 */
@Controller
@RequestMapping("/cartelera")
public class CarteleraController {

    private final PeliculaService peliculaService;

    public CarteleraController(PeliculaService peliculaService) {
        this.peliculaService = peliculaService;
    }

    @GetMapping
    public String listado(Model model) {
        model.addAttribute("peliculas", peliculaService.getPeliculas(true));
        return "cartelera/listado";
    }
}
