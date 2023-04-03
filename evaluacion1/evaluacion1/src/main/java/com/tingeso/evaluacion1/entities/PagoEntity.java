package com.tingeso.evaluacion1.entities;

import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PagoEntity {

    @Id
    @NotNull
    private Long id;

    public ProveedorEntity proveedor;


}
