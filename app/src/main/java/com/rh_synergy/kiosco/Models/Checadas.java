package com.rh_synergy.kiosco.Models;

import java.io.Serializable;

/**
 * Created by PC-108 on 3/29/2016.
 */
@SuppressWarnings("serial")
public class Checadas implements Serializable {
    public String AU_FECHA;
    public String preFecActual;
    public int preDay;
    public int preMonth;
    public int preYear;
    public String preEntrada;
    public String preSalida;
    public float preHoras;
    public float preNumExt;
    public float preAutExt;
    public float prePerCg;
    public float prePerSg;
    public float preAutTra;
    public String preElement;
    public float preJorDia;
    public float preJorSem;
    public short preEstDia;

    public void Checadas(){

    }

    public void Checadas(String AU_FECHA, int preDay, int preMonth, int preYear, String preEntrada, String preSalida, float preHoras, float preNumExt, float preAutExt, float prePerCg, float prePerSg, float preAutTra, String preElement, float preJorDia, float preJorSem, short preEstDia, String preFecActual){
        this.AU_FECHA = AU_FECHA;
        this.preDay = preDay;
        this.preMonth = preMonth;
        this.preYear = preYear;
        this.preEntrada = preEntrada;
        this.preSalida = preSalida;
        this.preHoras = preHoras;
        this.preNumExt = preNumExt;
        this.preAutExt = preAutExt;
        this.prePerCg = prePerCg;
        this.prePerSg = prePerSg;
        this.preAutTra = preAutTra;
        this.preElement = preElement;
        this.preJorDia = preJorDia;
        this.preJorSem = preJorSem;
        this.preEstDia = preEstDia;
        this.preFecActual = preFecActual;
    }
}
