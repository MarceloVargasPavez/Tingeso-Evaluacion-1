package com.tingeso.evaluacion1.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Pagos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    private LocalDate quincena;

    private String codigo_proveedor;

    private int total_klsleche;

    private int numero_dias_envio;

    private int promedio_diario_klsleche;

    private int porcentaje_variacion_leche;

    private int porcentaje_grasa;

    private int porcentaje_variacion_grasa;

    private int porcentaje_solidos;

    private int porcentaje_variacion_st;

    private int pago_leche;

    private int pago_grasa;

    private int pago_solidos;

    private int bonificacion_frecuencia;

    private int descuento_variacion_klsleche;

    private int descuento_variacion_grasa;

    private int descuento_variacion_st;

    private int pago_total;

    private int monto_retencion;

    private int monto_final;
}
