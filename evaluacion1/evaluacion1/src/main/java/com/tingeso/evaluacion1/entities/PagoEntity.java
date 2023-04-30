package com.tingeso.evaluacion1.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

/*
Clase que representa la entidad Pago.
*/
@Entity
@Table(name = "Pagos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoEntity {

    /*
    Variable que cumple la funcion de identificador unico para la entidad Pago.
    Variable de tipo Integer.
    Variable no nula.
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    /*
    Variable que representa la fecha representativa de la quincena
    en la que se realiza el pago.
    Variable de tipo LocalDate.
     */
    private LocalDate quincena;

    /*
    Variable que representa el identificador unico del proveedor.
    Variable de tipo String.
     */
    private String codigo_proveedor;

    /*
    Variable que representa el nombre del proveedor.
    Variable de tipo String.
     */
    private String nombre_proveedor;

    /*
    Variable que representa el total de kls entregado en el periodo
    de la quincena.
    Variable de tipo int.
     */
    private int total_klsleche;

    /*
    Variable que representa el numero de dias en el que se realizaron
    envios de leche durante la quincena.
    Variable de tipo int.
     */
    private int numero_dias_envio;

    /*
    Variable que representa el promedio diario de kls de leche enviados
    durante la quincena.
    Variable de tipo float.
     */
    private float promedio_diario_klsleche;

    /*
    Variable que representa el porcentaje de variacion de kls de leche respecto
    a la quincena anterior.
    Variable de tipo float.
     */
    private float porcentaje_variacion_leche;

    /*
    Variable que representa el porcentaje de grasa de la quincena.
    Variable de tipo float.
     */
    private float porcentaje_grasa;

    /*
    Variable que representa el porcentaje de variacion de grasa respecto
    a la quincena anterior.
    Variable de tipo float.
     */
    private float porcentaje_variacion_grasa;

    /*
    Variable que representa el porcentaje de solidos totales
    de la quincena.
    Variable de tipo float.
     */
    private float porcentaje_solidos;

    /*
    Variable que representa el porcentaje de variacion de solidos
    totales respecto a la quincena anterior.
    Variable de tipo float.
     */
    private float porcentaje_variacion_st;

    /*
    Variable que representa el pago por la leche, el que depende del
    total de kls de leche y la categoria del proveedor.
    Variable de tipo int.
     */
    private int pago_leche;

    /*
    Variable que representa el pago por grasa de la leche.
    Variable de tipo int.
     */
    private int pago_grasa;

    /*
    Variable que representa el pago por solidos totales de la leche.
    Variable de tipo int.
     */
    private int pago_solidos;

    /*
    Variable que representa la bonificacion por frencuencia de envios de leche.
    Variable de tipo float.
     */
    private float bonificacion_frecuencia;

    /*
    Variable que representa el porcentaje de descuento por variacion de leche.
    Variable de tipo int.
     */
    private int descuento_variacion_klsleche;

    /*
    Variable que representa el porcentaje de descuento por variacion de grasa.
    Variable de tipo int.
     */
    private int descuento_variacion_grasa;

    /*
    Variable que representa el porcentaje de descuento por variacion de solidos
    totales.
    Variable de tipo int.
     */
    private int descuento_variacion_st;

    /*
    Variable que representa el pago total con los descuentos aplicados.
    Variable de tipo float.
     */
    private int pago_total;

    /*
    Variable que representa el monto de retencion.
    Variable de tipo int.
     */
    private int monto_retencion;

    /*
    Variable que representa el monto final, es decir, con la resta del
    monto de retencion si es que asi corresponde.
    Variable de tipo int.
     */
    private int monto_final;
}