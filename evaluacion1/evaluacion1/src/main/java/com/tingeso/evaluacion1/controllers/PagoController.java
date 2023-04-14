package com.tingeso.evaluacion1.controllers;

import com.tingeso.evaluacion1.services.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class PagoController {

    @Autowired
    PagoService pagoService;
}
