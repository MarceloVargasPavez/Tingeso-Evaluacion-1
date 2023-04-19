package com.tingeso.evaluacion1.services;

import com.tingeso.evaluacion1.entities.AcopioLecheEntity;
import com.tingeso.evaluacion1.entities.PagoEntity;
import com.tingeso.evaluacion1.entities.PropiedadesLecheEntity;
import com.tingeso.evaluacion1.entities.ProveedorEntity;
import com.tingeso.evaluacion1.repositories.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

@Service
public class PagoService {

    @Autowired
    PagoRepository pagoRepository;

    @Autowired
    ProveedorService proveedorService;

    @Autowired
    AcopioLecheService acopioLecheService;

    @Autowired
    PropiedadesLecheService propiedadesLecheService;

    public void guardarPagos() {
        ArrayList<ProveedorEntity> proveedores = proveedorService.obtenerProveedores();
        PagoEntity pago;
        for (int i = 0; i < proveedores.size(); ++i) {
            pago = crearPago(proveedores.get(i).getCodigo());
            if (pago != null) {
                pagoRepository.save(pago);
            }
        }
        acopioLecheService.eliminarAcopios();
        propiedadesLecheService.eliminarPropiedades();
    }

    public ArrayList<PagoEntity> obtenerPagos() {
        return (ArrayList<PagoEntity>) pagoRepository.findAll();
    }

    /*
    Descripcion metodo: Metodo que crea un nuevo pago de la quincena actual para un proveedor.
    Parametros de entrada: Codigo del proveedor(String).
    Retorno: Nuevo pago(PagoEntity).
    */
    public PagoEntity crearPago(String codigo_proveedor) {
        PagoEntity nuevo_pago = new PagoEntity();

        ProveedorEntity proveedor_actual = proveedorService.proveedorRepository.findByCodigo(codigo_proveedor);

        //Se obtienen los acopios de la quincena del proveedor.
        ArrayList<AcopioLecheEntity> acopios = new ArrayList<>();
        acopios = acopioLecheService.obtenerAcopiosProveedor(codigo_proveedor);

        if (acopios.size() == 0) {
            return null;
        }

        //Se obtienen las propiedades de la leche de la quincena del proveedor.
        PropiedadesLecheEntity propiedades_proveedor = new PropiedadesLecheEntity();
        propiedades_proveedor = propiedadesLecheService.obtenerPropiedadesProveedor(codigo_proveedor);

        //Se crea un arreglo de fechas de tipo LocalDate y se transforman las de acopios.
        ArrayList<LocalDate> fechas = transformarfechas(acopios);

        nuevo_pago.setQuincena(establecerQuincena(fechas));

        PagoEntity quincenaAnterior = obtenerPagoQuincenaAnterior(codigo_proveedor, nuevo_pago.getQuincena());

        nuevo_pago.setTotal_klsleche(totalKlsLeche(acopios));

        nuevo_pago.setNumero_dias_envio(cantidadDiasEnvio(fechas));

        nuevo_pago.setPromedio_diario_klsleche(promedioDiarioLeche(nuevo_pago.getTotal_klsleche(), nuevo_pago.getQuincena()));

        nuevo_pago.setPorcentaje_variacion_leche(porcentajeVariacionLeche(nuevo_pago.getTotal_klsleche(), quincenaAnterior));

        nuevo_pago.setPorcentaje_grasa(propiedades_proveedor.getPorcentaje_grasa());

        nuevo_pago.setPorcentaje_variacion_grasa(porcentajeVariacionGrasa(propiedades_proveedor.getPorcentaje_grasa(), quincenaAnterior));

        nuevo_pago.setPorcentaje_solidos(propiedades_proveedor.getPorcentaje_solidos());

        nuevo_pago.setPorcentaje_variacion_st(porcentajeVariacionSolidos(propiedades_proveedor.getPorcentaje_solidos(), quincenaAnterior));

        nuevo_pago.setPago_leche(pagoPorLeche(proveedor_actual.getCategoria(), nuevo_pago.getTotal_klsleche()));

        nuevo_pago.setPago_grasa(pagoPorGrasa(nuevo_pago.getPorcentaje_grasa(), nuevo_pago.getTotal_klsleche()));

        nuevo_pago.setPago_solidos(pagoPorSolidos(nuevo_pago.getPorcentaje_solidos(), nuevo_pago.getTotal_klsleche()));

        nuevo_pago.setBonificacion_frecuencia(calculoBonificaionFrecuencia(acopios, fechas, nuevo_pago.getQuincena()));

        nuevo_pago.setDescuento_variacion_klsleche(porcentajeDescuentoVariacionLeche(nuevo_pago.getPorcentaje_variacion_leche()));

        nuevo_pago.setDescuento_variacion_grasa(porcentajeDescuentoVariacionGrasa(nuevo_pago.getPorcentaje_variacion_grasa()));

        nuevo_pago.setDescuento_variacion_st(porcentajeDescuentoVariacionSolidos(nuevo_pago.getPorcentaje_variacion_st()));

        int pago_acopio_leche = pagoAcopioLeche(nuevo_pago.getPago_leche(), nuevo_pago.getPago_grasa(), nuevo_pago.getPago_solidos(), nuevo_pago.getBonificacion_frecuencia());
        int descuentos = descuentos(pago_acopio_leche, nuevo_pago.getDescuento_variacion_klsleche(), nuevo_pago.getDescuento_variacion_grasa(), nuevo_pago.getDescuento_variacion_st());
        nuevo_pago.setPago_total(pagoTotal(pago_acopio_leche, descuentos));

        if (Objects.equals(proveedor_actual.getRetencion(), "SI")) {
            nuevo_pago.setMonto_retencion(950000);
        } else {
            nuevo_pago.setMonto_retencion(0);
        }

        nuevo_pago.setMonto_final(pagoFinal(nuevo_pago.getPago_total(), nuevo_pago.getMonto_retencion()));

        return nuevo_pago;
    }

    /*
    Descripcion metodo: Metodo que establece la quincena actual.
    Parametros de entrada: Fechas(ArrayList<LocalDate>).
    Retorno: Quincena(LocalDate) o null en caso de que no existan acopios.
    */
    public LocalDate establecerQuincena(ArrayList<LocalDate> fechas) {
        int dia;
        int mes;
        int anio;
        if (fechas != null) {
            LocalDate primeraFecha = fechas.get(0);
            if (fechas.get(0).getDayOfMonth() > 15) {
                dia = primeraFecha.lengthOfMonth();
            } else {
                dia = 15;
            }
            mes = primeraFecha.getMonthValue();
            anio = primeraFecha.getYear();
        }
        //Caso en el que el arreglo fechas esta vacio.
        else {
            return null;
        }
        return LocalDate.of(anio, mes, dia);
    }

    /*
    Descripcion metodo: Metodo que obtiene el total de kls de leche de la quincena de un proveedor.
    Parametros de entrada: Acopios del proveedor(ArrayList<AcopioLecheEntity>).
    Retorno: Total de kilos de leche de la quincena(int).
    */
    public int totalKlsLeche(ArrayList<AcopioLecheEntity> acopios_proveedor) {
        int totalKls = 0;
        for (int i = 0; i < acopios_proveedor.size(); ++i) {
            totalKls = totalKls + acopios_proveedor.get(i).getKilos_leche();
        }
        return totalKls;
    }

    /*
    Descripcion metodo: Metodo que obtiene la cantidad de dias dentro de la quincena que un proveedor envio leche.
    Parametros de entrada: Fechas(ArrayList<LocalDate>).
    Retorno: Cantidad de dias en los que se realizaron acopios(int).
    */
    public int cantidadDiasEnvio(ArrayList<LocalDate> fechas) {
        int diaActual;
        ArrayList<Integer> dias = new ArrayList<>();
        //Se recorren las fechas en las que se realizaron envios y se agregan a la lista
        //dias, a menos que el dia se repita.
        for (LocalDate fecha : fechas) {
            diaActual = fecha.getDayOfMonth();
            if (!dias.contains(diaActual)) {
                dias.add(diaActual);
            }
        }
        return dias.size();
    }

    /*
    Descripcion metodo: Metodo que obtiene el promedio diario de kls de leche enviados por un proveedor.
    Parametros de entrada: Total de kls de leche(int) y fechas de acopio(ArrayList<LocalDate>).
    Retorno: Promedio diario de kls de leche entregados por el proveedor(int).
    */
    public Integer promedioDiarioLeche(int total_Klsleche, LocalDate quincena) {
        int total_dias;
        int promedio = 0;
        if (quincena != null) {
            if (quincena.getDayOfMonth() > 15) {
                total_dias = quincena.lengthOfMonth() - 15;
            } else {
                total_dias = 15;
            }
        } else {
            return promedio;
        }
        promedio = total_Klsleche / total_dias;
        return promedio;
    }

    /*
    Descripcion metodo: Metodo que transforma cada fecha String de los acopios a tipo LocalDate.
    Parametros de entrada: Acopios(ArrayList<AcopioLecheEntity>).
    Retorno: ArrayList con las fechas de tipo LocalDate(ArrayList<LocalDate>).
    */
    public ArrayList<LocalDate> transformarfechas(ArrayList<AcopioLecheEntity> acopios_proveedor) {
        ArrayList<LocalDate> fechas = new ArrayList<>();
        // Crear un objeto DateTimeFormatter para analizar la cadena de fecha
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        for (AcopioLecheEntity acopioLecheEntity : acopios_proveedor) {
            fechas.add(LocalDate.parse(acopioLecheEntity.getFecha(), formato));
        }
        return fechas;
    }

    /*
    Descripcion metodo: Metodo para calcular porcentaje de variacion.
    Parametros de entrada: Valor actual(int) y valor anterior(int).
    Retorno: Porcentaje de variacion(int).
    */
    public int calculoVariacion(int valor_actual, int valor_anterior) {
        return ((valor_actual - valor_anterior) / valor_anterior) * 100;
    }

    /*
    Descripcion metodo: Metodo para calcular porcentaje de variacion de leche.
    Parametros de entrada: Total de kls de leche(int) y pago anterior del proveedor(PagoEntity).
    Retorno: Porcentaje de variacion de leche(int).
    */
    public int porcentajeVariacionLeche(int total_klsleche, PagoEntity pago_anterior) {
        int porcentaje_variacion = 0;
        if (pago_anterior != null) {
            porcentaje_variacion = calculoVariacion(total_klsleche, pago_anterior.getTotal_klsleche());
        }
        return porcentaje_variacion;
    }

    /*
    Descripcion metodo: Metodo para calcular porcentaje de variacion de grasa.
    Parametros de entrada: Porcentaje de grasa(int) y pago anterior del proveedor(PagoEntity).
    Retorno: Porcentaje de variacion de grasa(int).
    */
    public int porcentajeVariacionGrasa(int porcentaje_grasa, PagoEntity pago_anterior) {
        int porcentaje_variacion_grasa = 0;
        if (pago_anterior != null) {
            porcentaje_variacion_grasa = calculoVariacion(porcentaje_grasa, pago_anterior.getPorcentaje_grasa());
        }
        return porcentaje_variacion_grasa;
    }

    /*
    Descripcion metodo: Metodo para calcular porcentaje de variacion de solidos.
    Parametros de entrada: Porcentaje de solidos(int) y pago anterior del proveedor(PagoEntity).
    Retorno: Porcentaje de variacion de solidos(int).
    */
    public int porcentajeVariacionSolidos(int porcentaje_solidos, PagoEntity pago_anterior) {
        int porcentaje_variacion_solidos = 0;
        if (pago_anterior != null) {
            porcentaje_variacion_solidos = calculoVariacion(porcentaje_solidos, pago_anterior.getPorcentaje_solidos());
        }
        return porcentaje_variacion_solidos;
    }

    /*
    Descripcion metodo: Metodo que obtiene el pago de la quincena anterior de un proveedor.
    Parametros de entrada: Codigo del proveedor(String) y quincena actual(LocalDate).
    Retorno: Pago anterior(PagoEntity).
    */
    public PagoEntity obtenerPagoQuincenaAnterior(String codigo_proveedor, LocalDate quincena_actual) {
        ArrayList<PagoEntity> pagos = new ArrayList<>();
        pagos = (ArrayList<PagoEntity>) pagoRepository.findAll();
        PagoEntity pago_anterior = null;
        LocalDate quincena_anterior;
        //Si es que la quincena corresponde a la segunda del mes la fecha es el mismo anio, mes y el dia es igual a 15.
        if (quincena_actual.getDayOfMonth() > 15) {
            quincena_anterior = LocalDate.of(quincena_actual.getYear(), quincena_actual.getMonth(), 15);
        }
        //En caso contrario se debe cambiar el mes e incluso el anio.
        else {
            //Si el mes actual es enero se debe cambiar el anio.
            if (quincena_actual.getMonthValue() == 1) {
                quincena_anterior = LocalDate.of(quincena_actual.getYear() - 1, 12, 31);
            }
            //Si es otro mes se debe calcular la cantidad de dias del mes anterior.
            else {
                //Fecha para calcular la cantidad de dias del mes anterior.
                LocalDate nuevaFecha = LocalDate.of(quincena_actual.getYear(), quincena_actual.getMonthValue() - 1, 1);
                int cantidadDiasMes = nuevaFecha.lengthOfMonth();
                quincena_anterior = LocalDate.of(quincena_actual.getYear(), quincena_actual.getMonthValue() - 1, cantidadDiasMes);
            }
        }
        if (pagos != null) {
            for (PagoEntity pago : pagos) {
                if (Objects.equals(pago.getCodigo_proveedor(), codigo_proveedor) && pago.getQuincena() == quincena_anterior) {
                    pago_anterior = pago;
                    break;
                }
            }
        } else {
            return null;
        }
        return pago_anterior;
    }

    /*
    Descripcion metodo: Metodo que obtiene el porcentaje de descuento de variacion de leche.
    Parametros de entrada: Porcentaje de variacion de leche actual(int).
    Retorno: Porcentaje de descuento de variacion de leche(int).
    */
    public int porcentajeDescuentoVariacionLeche(int porcentaje_variacion_leche) {
        int porcentaje_descuento = 0;
        if (porcentaje_variacion_leche <= -8) {
            if (porcentaje_variacion_leche <= -9 && porcentaje_variacion_leche >= -25) {
                porcentaje_descuento = 7;
            }
            if (porcentaje_variacion_leche <= -26 && porcentaje_variacion_leche >= -45) {
                porcentaje_descuento = 15;
            }
            if (porcentaje_variacion_leche <= -46) {
                porcentaje_descuento = 30;
            }
        }
        return porcentaje_descuento;
    }

    /*
    Descripcion metodo: Metodo que obtiene el porcentaje de descuento de variacion de grasa.
    Parametros de entrada: Porcentaje de variacion de grasa actual(int).
    Retorno: Porcentaje de descuento de variacion de grasa(int).
    */
    public int porcentajeDescuentoVariacionGrasa(int porcentaje_variacion_grasa) {
        int porcentaje_descuento = 0;
        if (porcentaje_variacion_grasa <= -15) {
            if (porcentaje_variacion_grasa <= -16 && porcentaje_variacion_grasa >= -25) {
                porcentaje_descuento = 12;
            }
            if (porcentaje_variacion_grasa <= -26 && porcentaje_variacion_grasa >= -40) {
                porcentaje_descuento = 20;
            }
            if (porcentaje_variacion_grasa <= -41) {
                porcentaje_descuento = 30;
            }
        }
        return porcentaje_descuento;
    }

    /*
    Descripcion metodo: Metodo que obtiene el porcentaje de descuento de variacion de solidos.
    Parametros de entrada: Porcentaje de variacion de solidos actual(int).
    Retorno: Porcentaje de descuento de variacion de solidos(int).
    */
    public int porcentajeDescuentoVariacionSolidos(int porcentaje_variacion_solidos) {
        int porcentaje_descuento = 0;
        if (porcentaje_variacion_solidos <= -6) {
            if (porcentaje_variacion_solidos <= -7 && porcentaje_variacion_solidos >= -12) {
                porcentaje_descuento = 18;
            }
            if (porcentaje_variacion_solidos <= -13 && porcentaje_variacion_solidos >= -35) {
                porcentaje_descuento = 27;
            }
            if (porcentaje_variacion_solidos <= -36) {
                porcentaje_descuento = 45;
            }
        }
        return porcentaje_descuento;
    }

    /*
    Descripcion metodo: Metodo que obtiene pago de leche segun la categoria del proveedor.
    Parametros de entrada: Categoria del proveedor(String) y total de kls de leche enviados(int).
    Retorno: Pago por leche(int).
    */
    public int pagoPorLeche(String categoria_proveedor, int total_klsleche) {
        int total = 0;
        if (Objects.equals(categoria_proveedor, "A")) {
            total = 700 * total_klsleche;
        }
        if (Objects.equals(categoria_proveedor, "B")) {
            total = 550 * total_klsleche;
        }
        if (Objects.equals(categoria_proveedor, "C")) {
            total = 400 * total_klsleche;
        }
        if (Objects.equals(categoria_proveedor, "D")) {
            total = 250 * total_klsleche;
        }
        return total;
    }

    /*
    Descripcion metodo: Metodo que obtiene el pago por grasa.
    Parametros de entrada: Porcentaje de grasa(int) y total de kls de leche enviados(int).
    Retorno: Pago por grasa(int).
    */
    public int pagoPorGrasa(int porcentaje_grasa, int total_klsleche) {
        int total = 0;
        if (porcentaje_grasa >= 0 && porcentaje_grasa <= 20) {
            total = 30 * total_klsleche;
        }
        if (porcentaje_grasa >= 21 && porcentaje_grasa <= 45) {
            total = 80 * total_klsleche;
        }
        if (porcentaje_grasa >= 46) {
            total = 120 * total_klsleche;
        }
        return total;
    }

    /*
    Descripcion metodo: Metodo que obtiene el pago por solidos.
    Parametros de entrada: Porcentaje de solidos(int) y total de kls de leche enviados(int).
    Retorno: Pago por solidos(int).
    */
    public int pagoPorSolidos(int porcentaje_solidos, int total_klsleche) {
        int total = 0;
        if (porcentaje_solidos >= 0 && porcentaje_solidos <= 7) {
            total = -130 * total_klsleche;
        }
        if (porcentaje_solidos >= 8 && porcentaje_solidos <= 18) {
            total = -90 * total_klsleche;
        }
        if (porcentaje_solidos >= 19 && porcentaje_solidos <= 35) {
            total = 95 * total_klsleche;
        }
        if (porcentaje_solidos >= 36) {
            total = 150 * total_klsleche;
        }
        return total;
    }

    /*
    Descripcion metodo: Metodo que obtiene la bonificacion por frecuencia.
    Parametros de entrada: Acopios(ArrayList<AcopioLecheEntity>), fechas(ArrayList<LocalDate>) y quincena(LocalDate).
    Retorno: Bonificacion por frecuencia(int).
    */
    public int calculoBonificaionFrecuencia(ArrayList<AcopioLecheEntity> acopios, ArrayList<LocalDate> fechas, LocalDate quincena) {
        int bonificacion = 0;
        ArrayList<String> turnos = new ArrayList<>();
        int inicio_mes;
        int final_quincena;
        boolean maniana;
        boolean tarde;
        if (quincena.getDayOfMonth() > 15) {
            inicio_mes = 16;
            final_quincena = quincena.lengthOfMonth();
        } else {
            inicio_mes = 1;
            final_quincena = 15;
        }
        for (int i = inicio_mes; i <= final_quincena; ++i) {
            maniana = false;
            tarde = false;
            for (int j = 0; j < fechas.size(); ++j) {
                if (fechas.get(j).getDayOfMonth() == i) {
                    if (Objects.equals(acopios.get(j).getTurno(), "M")) {
                        maniana = true;
                    }
                    if (Objects.equals(acopios.get(j).getTurno(), "T")) {
                        tarde = true;
                    }
                }
            }
            if (maniana && tarde) {
                turnos.add("MyT");
            }
            if (maniana && !tarde) {
                turnos.add("M");
            }
            if (!maniana && tarde) {
                turnos.add("T");
            }
            if (!maniana && !tarde) {
                turnos.add("Null");
            }
        }
        int contador_maniana_tarde = 0;
        int contador_maniana = 0;
        int contador_tarde = 0;
        for (String turno : turnos) {
            if (Objects.equals(turno, "MyT")) {
                contador_maniana_tarde = contador_maniana_tarde + 1;
            }
            if (Objects.equals(turno, "M") || Objects.equals(turno, "MyT")) {
                contador_maniana = contador_maniana + 1;
            }
            if (Objects.equals(turno, "T") || Objects.equals(turno, "MyT")) {
                contador_tarde = contador_tarde + 1;
            }
        }
        if (contador_maniana_tarde >= 10) {
            bonificacion = 20;
        } else {
            if (contador_maniana >= 10) {
                bonificacion = 12;
            } else {
                if (contador_tarde >= 10) {
                    bonificacion = 8;
                }
            }
        }
        return bonificacion;
    }

    public int pagoAcopioLeche(int pago_leche, int pago_grasa, int pago_solidos, int bonificacion_frecuencia) {
        return pago_leche + pago_grasa + pago_solidos + pago_leche * (bonificacion_frecuencia / 100);
    }

    public int descuentos(int pago_acopio_leche, int descuento_variacion_leche, int descuento_variacion_grasa, int descuento_variacion_solidos) {
        return -pago_acopio_leche * (descuento_variacion_leche / 100) -
                pago_acopio_leche * (descuento_variacion_grasa / 100) -
                pago_acopio_leche * (descuento_variacion_solidos / 100);
    }

    public int pagoTotal(int pago_acopio_leche, int descuentos) {
        return pago_acopio_leche - descuentos;
    }

    public int pagoFinal(int pago_total, int retencion) {
        return pago_total - retencion;
    }
}