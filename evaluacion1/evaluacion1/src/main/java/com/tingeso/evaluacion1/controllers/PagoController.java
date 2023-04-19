package com.tingeso.evaluacion1.controllers;

import com.tingeso.evaluacion1.entities.PagoEntity;
import com.tingeso.evaluacion1.services.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class PagoController {

    @Autowired
    PagoService pagoService;

    @GetMapping("/informacionPagos")
    public String listarPagos(Model model){
        pagoService.guardarPagos();
        ArrayList<PagoEntity> pagos=pagoService.obtenerPagos();
        model.addAttribute("pagos",pagos);
        return "informacionPagos";
    }
}
