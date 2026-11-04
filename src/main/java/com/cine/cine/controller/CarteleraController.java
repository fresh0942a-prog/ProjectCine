/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cine.cine.controller;

import com.cine.cine.service.EmpresaService;
import com.cine.cine.service.FuncionService;
import com.cine.cine.service.PeliculaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author AlexanderParallelsVM
 */
@Controller
@RequestMapping("/cartelera")
public class CarteleraController {

    private final PeliculaService peliculaService;
    private final EmpresaService empresaService;
    private final FuncionService funcionService;

    public CarteleraController(PeliculaService peliculaService, EmpresaService empresaService, FuncionService funcionService) {
        this.peliculaService = peliculaService;
        this.empresaService = empresaService;
        this.funcionService = funcionService;
    }

    @GetMapping
    public String inicio() {
        return "redirect:/";
    }

    @GetMapping("/{identificador}")
    public String listado(@PathVariable String identificador, Model model) {
        var empresa = empresaService.getEmpresaPorIdentificador(identificador);
        if (empresa == null || !empresa.isActivo()) {
            return "redirect:/";
        }
        model.addAttribute("empresa", empresa);
        model.addAttribute("peliculas", peliculaService.getPeliculasPorEmpresa(empresa));
        return "cartelera/listado";
    }

    @GetMapping("/detalle/{idPelicula}")
    public String detalle(@PathVariable Integer idPelicula,
            @RequestParam(value = "idFuncion", required = false) Integer idFuncion, Model model) {
        var pelicula = peliculaService.getPelicula(idPelicula);
        if (pelicula.isEmpty() || !pelicula.get().isActivo() || !pelicula.get().getEmpresa().isActivo()) {
            return "redirect:/";
        }
        model.addAttribute("pelicula", pelicula.get());
        model.addAttribute("funciones", funcionService.getFuncionesDisponibles(pelicula.get()));
        model.addAttribute("idFuncion", idFuncion);
        return "cartelera/detalle";
    }
}