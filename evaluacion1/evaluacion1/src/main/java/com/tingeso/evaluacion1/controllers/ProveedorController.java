package com.tingeso.evaluacion1.controllers;

import com.tingeso.evaluacion1.services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class ProveedorController {

    @Autowired
    ProveedorService proveedorService;
}
