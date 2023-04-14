package com.tingeso.evaluacion1.services;

import com.tingeso.evaluacion1.repositories.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagoService {

    @Autowired
    PagoRepository pagoRepository;
}
