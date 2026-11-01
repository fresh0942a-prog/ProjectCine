package com.cine.cine.controller;

import com.cine.cine.domain.Funcion;
import com.cine.cine.repository.DetalleVentaRepository;
import com.cine.cine.service.FuncionService;
import java.time.LocalDateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/funcion")
public class DisponibilidadController {

    private final FuncionService funcionService;
    private final DetalleVentaRepository detalleVentaRepository;

    public DisponibilidadController(FuncionService funcionService, DetalleVentaRepository detalleVentaRepository) {
        this.funcionService = funcionService;
        this.detalleVentaRepository = detalleVentaRepository;
    }

    @GetMapping("/disponibilidad/{idFuncion}")
    public String disponibilidad(@PathVariable Integer idFuncion, Model model, RedirectAttributes redirectAttributes) {
        var resultado = funcionService.getFuncion(idFuncion);
        if (resultado.isEmpty()) {
            redirectAttributes.addFlashAttribute("mensajeError", "La función solicitada no existe.");
            return "redirect:/funcion/listado";
        }
        Funcion funcion = resultado.get();
        int capacidad = funcion.getSala().getCapacidad();
        long ocupados = detalleVentaRepository.countByFuncionAndAsientoIsNotNull(funcion);
        long disponibles = Math.max(0, capacidad - ocupados);
        model.addAttribute("funcion", funcion);
        model.addAttribute("capacidad", capacidad);
        model.addAttribute("ocupados", ocupados);
        model.addAttribute("disponibles", disponibles);
        model.addAttribute("pasada", funcion.getFechaHora().isBefore(LocalDateTime.now()));
        model.addAttribute("agotada", disponibles == 0 && !funcion.getFechaHora().isBefore(LocalDateTime.now()));
        return "funcion/disponibilidad";
    }
}
