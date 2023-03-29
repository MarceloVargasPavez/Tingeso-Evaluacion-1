package com.tingeso.evaluacion1.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "PropiedadesLeche")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropiedadesLecheEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private int porcentaje_solidos;

    private int porcentaje_grasa;
}
