package com.tingeso.evaluacion1.entities;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "Proveedores")
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ProveedorEntity {

    @Id
    @NotNull
    private String codigo;

    private String nombre;

    private String categoria;

    private String retencion;
}
