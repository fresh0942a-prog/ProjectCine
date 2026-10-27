/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cine.cine.controller;
import com.cine.cine.service.EmpresaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private final EmpresaService empresaService;

    public IndexController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("empresas", empresaService.getEmpresas(true));
        return "index";
    }
}
