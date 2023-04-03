package com.tingeso.evaluacion1.services;

import com.tingeso.evaluacion1.repositories.AcopioLecheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AcopioLecheService {

    @Autowired
    AcopioLecheRepository acopioLecheRepository;


}
