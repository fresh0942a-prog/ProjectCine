
package com.cine.cine.controller;

import com.cine.cine.service.SnackService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author barqu
 */

@Controller
@RequestMapping("/snack")
public class SnackController {

    private final SnackService snackService;

    public SnackController(SnackService snackService) {
        this.snackService = snackService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        model.addAttribute("snacks", snackService.getSnacks(false));
        return "snack/listado";
    }
}
