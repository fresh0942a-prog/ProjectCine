
package com.cine.cine.controller;

import com.cine.cine.service.FuncionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/funcion")
public class FuncionController {

    private final FuncionService funcionService;

    public FuncionController(FuncionService funcionService) {
        this.funcionService = funcionService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        model.addAttribute("funciones", funcionService.getFunciones());
        return "funcion/listado";
    }
}