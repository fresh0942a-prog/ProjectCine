package com.cine.cine.controller;

import com.cine.cine.domain.Empresa;
import com.cine.cine.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/empresa")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        model.addAttribute("empresas", empresaService.getEmpresas(false));
        return "empresa/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        Empresa empresa = new Empresa();
        empresa.setActivo(true);
        model.addAttribute("empresa", empresa);
        return "empresa/modifica";
    }

    @GetMapping("/modificar/{idEmpresa}")
    public String modificar(@PathVariable Integer idEmpresa, Model model) {
        var empresa = empresaService.getEmpresa(idEmpresa);
        if (empresa.isEmpty()) {
            return "redirect:/empresa/listado";
        }
        model.addAttribute("empresa", empresa.get());
        return "empresa/modifica";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Empresa empresa, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "empresa/modifica";
        }
        try {
            empresaService.save(empresa);
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("errorIdentificador", true);
            return "empresa/modifica";
        }
        return "redirect:/empresa/listado";
    }
}