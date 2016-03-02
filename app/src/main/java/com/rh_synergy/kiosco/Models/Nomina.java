package com.rh_synergy.kiosco.Models;

import java.util.List;

/**
 * Created by PC-108 on 1/25/2016.
 */
public class Nomina {
    public int NomiPeriodo;
    public int NomiYear;
    public String NomiFecInicio;
    public String NomiFecFinal;
    public String ColaNomiPrettyName;
    public float NomiNeto;
    public float NomiPercepciones;
    public float NomiDeducciones;

    public Nomina (){

    }

    public Nomina (int NomiPeriodo, int NomiYear, String NomiFecInicio, String NomiFecFinal, String ColaNomiPrettyName, float NomiNeto, float NomiPercepciones, float NomiDeducciones) {
        this.NomiPeriodo = NomiPeriodo;
        this.NomiYear = NomiYear;
        this.NomiFecInicio = NomiFecInicio;
        this.NomiFecFinal = NomiFecFinal;
        this.ColaNomiPrettyName = ColaNomiPrettyName;
        this.NomiNeto = NomiNeto;
        this.NomiPercepciones = NomiPercepciones;
        this.NomiDeducciones = NomiDeducciones;
    }
}
