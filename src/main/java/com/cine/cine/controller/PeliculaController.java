package com.cine.cine.controller;

import com.cine.cine.domain.Pelicula;
import com.cine.cine.service.EmpresaService;
import com.cine.cine.service.PeliculaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/pelicula")
public class PeliculaController {

    private final PeliculaService peliculaService;
    private final EmpresaService empresaService;

    public PeliculaController(PeliculaService peliculaService, EmpresaService empresaService) {
        this.peliculaService = peliculaService;
        this.empresaService = empresaService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        model.addAttribute("peliculas", peliculaService.getPeliculas(false));
        return "pelicula/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        Pelicula pelicula = new Pelicula();
        pelicula.setActivo(true);
        model.addAttribute("pelicula", pelicula);
        model.addAttribute("empresas", empresaService.getEmpresas(false));
        return "pelicula/modifica";
    }

    @GetMapping("/modificar/{idPelicula}")
    public String modificar(@PathVariable Integer idPelicula, Model model) {
        var pelicula = peliculaService.getPelicula(idPelicula);
        if (pelicula.isEmpty()) {
            return "redirect:/pelicula/listado";
        }
        model.addAttribute("pelicula", pelicula.get());
        model.addAttribute("empresas", empresaService.getEmpresas(false));
        return "pelicula/modifica";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Pelicula pelicula, BindingResult bindingResult,
            @RequestParam(value = "imagenFile", required = false) MultipartFile imagenFile, Model model) {
        model.addAttribute("empresas", empresaService.getEmpresas(false));
        if (pelicula.getEmpresa() == null || pelicula.getEmpresa().getIdEmpresa() == null) {
            model.addAttribute("errorEmpresa", true);
            return "pelicula/modifica";
        }
        if (bindingResult.hasErrors()) {
            return "pelicula/modifica";
        }
        try {
            peliculaService.save(pelicula, imagenFile);
        } catch (IllegalStateException e) {
            model.addAttribute("errorImagen", true);
            return "pelicula/modifica";
        }
        return "redirect:/pelicula/listado";
    }
}