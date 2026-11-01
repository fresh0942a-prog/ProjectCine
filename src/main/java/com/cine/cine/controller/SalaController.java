/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cine.cine.controller;

import com.cine.cine.domain.Sala;
import com.cine.cine.service.EmpresaService;
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
@RequestMapping("/sala")
public class SalaController {

    private final SalaService salaService;
    private final EmpresaService empresaService;

    public SalaController(SalaService salaService, EmpresaService empresaService) {
        this.salaService = salaService;
        this.empresaService = empresaService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        model.addAttribute("salas", salaService.getSalas(false));
        return "sala/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        Sala sala = new Sala();
        sala.setActivo(true);
        model.addAttribute("sala", sala);
        model.addAttribute("empresas", empresaService.getEmpresas(true));
        return "sala/modifica";
    }

    @GetMapping("/modificar/{idSala}")
    public String modificar(@PathVariable Integer idSala, Model model, RedirectAttributes redirectAttributes) {
        var sala = salaService.getSala(idSala);
        if (sala.isEmpty()) {
            redirectAttributes.addFlashAttribute("mensajeError", "La sala solicitada no existe.");
            return "redirect:/sala/listado";
        }
        model.addAttribute("sala", sala.get());
        model.addAttribute("empresas", empresaService.getEmpresas(true));
        return "sala/modifica";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Sala sala, BindingResult bindingResult, Model model) {
        if (sala.getEmpresa() == null || sala.getEmpresa().getIdEmpresa() == null) {
            model.addAttribute("errorEmpresa", true);
        }
        if (bindingResult.hasErrors() || sala.getEmpresa() == null || sala.getEmpresa().getIdEmpresa() == null) {
            model.addAttribute("empresas", empresaService.getEmpresas(true));
            return "sala/modifica";
        }
        salaService.save(sala);
        return "redirect:/sala/listado";
    }

    @GetMapping("/eliminar/{idSala}")
    public String eliminar(@PathVariable Integer idSala, RedirectAttributes redirectAttributes) {
        if (!salaService.delete(idSala)) {
            redirectAttributes.addFlashAttribute("mensajeError", "No se puede eliminar la sala porque tiene funciones registradas.");
        }
        return "redirect:/sala/listado";
    }
}

