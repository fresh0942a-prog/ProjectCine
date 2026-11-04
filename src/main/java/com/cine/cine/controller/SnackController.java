
package com.cine.cine.controller;

import com.cine.cine.domain.Snack;
import com.cine.cine.service.EmpresaService;
import com.cine.cine.service.SnackService;
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

/**
 *
 * @author AlexanderParallelsVM
 */

@Controller
@RequestMapping("/snack")
public class SnackController {

    private final SnackService snackService;
    private final EmpresaService empresaService;

    public SnackController(SnackService snackService, EmpresaService empresaService) {
        this.snackService = snackService;
        this.empresaService = empresaService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        model.addAttribute("snacks", snackService.getSnacks(false));
        return "snack/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        Snack snack = new Snack();
        snack.setActivo(true);
        model.addAttribute("snack", snack);
        model.addAttribute("empresas", empresaService.getEmpresas(false));
        return "snack/modifica";
    }

    @GetMapping("/modificar/{idSnack}")
    public String modificar(@PathVariable Integer idSnack, Model model) {
        var snack = snackService.getSnack(idSnack);
        if (snack.isEmpty()) {
            return "redirect:/snack/listado";
        }
        model.addAttribute("snack", snack.get());
        model.addAttribute("empresas", empresaService.getEmpresas(false));
        return "snack/modifica";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Snack snack, BindingResult bindingResult,
            @RequestParam(value = "imagenFile", required = false) MultipartFile imagenFile, Model model) {
        model.addAttribute("empresas", empresaService.getEmpresas(false));
        if (snack.getEmpresa() == null || snack.getEmpresa().getIdEmpresa() == null) {
            model.addAttribute("errorEmpresa", true);
            return "snack/modifica";
        }
        if (bindingResult.hasErrors()) {
            return "snack/modifica";
        }
        try {
            snackService.save(snack, imagenFile);
        } catch (IllegalStateException e) {
            model.addAttribute("errorImagen", true);
            return "snack/modifica";
        }
        return "redirect:/snack/listado";
    }
}