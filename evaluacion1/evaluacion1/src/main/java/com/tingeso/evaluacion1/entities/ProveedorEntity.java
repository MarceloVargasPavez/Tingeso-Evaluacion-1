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
    public String codigo;

    public String nombre;

    public String categoria;

    public String retencion;
}
