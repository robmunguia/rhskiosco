package com.rh_synergy.kiosco.Models;

import java.io.Serializable;

/**
 * Created by PC-108 on 4/12/2016.
 */
@SuppressWarnings("serial")
public class Depositos implements Serializable {
    public int DepoYear;
    public int DepoPeriodo;
    public String DepoFecha;
    public float DepoDeposito;

    public Depositos(int DepoYear, int DepoPeriodo, String DepoFecha, float DepoDeposito){
        this.DepoYear = DepoYear;
        this.DepoPeriodo = DepoPeriodo;
        this.DepoFecha = DepoFecha;
        this.DepoDeposito = DepoDeposito;
    }
}
