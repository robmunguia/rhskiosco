package com.rh_synergy.kiosco.Models;

import java.io.Serializable;

/**
 * Created by PC-108 on 3/15/2016.
 */
@SuppressWarnings("serial")
public class DetalleNomina implements Serializable {
    public short CO_TIPO;
    public String CO_DESCRIP;
    public float MO_PERCEPC;
    public float MO_DEDUCCI;

    public DetalleNomina(){

    }

    public DetalleNomina(short tipo, String descrip, float percepciones, float deducciones){
        this.CO_TIPO = tipo;
        this.CO_DESCRIP = descrip;
        this.MO_PERCEPC = percepciones;
        this.MO_DEDUCCI = deducciones;
    }
}
