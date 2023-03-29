package com.tingeso.evaluacion1.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "AcopioLeche")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcopioLecheEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String fecha;

    private String turno;

    private String codigo_proveedor;

    private Integer kilos_leche;
}
