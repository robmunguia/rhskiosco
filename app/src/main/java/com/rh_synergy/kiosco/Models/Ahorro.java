package com.rh_synergy.kiosco.Models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by PC-108 on 1/25/2016.
 */
@SuppressWarnings("serial")
public class Ahorro implements Serializable {
    public float AhorSaldo;
    public float AhorCargos;
    public float AhorTotal;
    public float AhorDescPeriodo;
    public String AhorFecha;
    public List<Retiros> AhorRetiros;
    public List<Depositos> AhorDepositos;

    public Ahorro (float AhorSaldo, float AhorCargos, float AhorTotal, float AhorDescPeriodo, String AhorFecha, List<Retiros> AhorRetiros, List<Depositos> AhorDepositos){
        this.AhorSaldo = AhorSaldo;
        this.AhorCargos = AhorCargos;
        this.AhorTotal = AhorTotal;
        this.AhorDescPeriodo = AhorDescPeriodo;
        this.AhorFecha = AhorFecha;
        this.AhorRetiros = AhorRetiros;
        this.AhorDepositos = AhorDepositos;
    }
}
