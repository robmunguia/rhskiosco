package com.rh_synergy.kiosco.Models;

import java.io.Serializable;

/**
 * Created by PC-108 on 4/12/2016.
 */
@SuppressWarnings("serial")
public class Retiros implements Serializable {
    public String RetiFecha;
    public float RetiCargo;
    public String RetiObservacion;

    public Retiros(String RetiFecha, float RetiCargo, String RetiObservacion){
        this.RetiFecha = RetiFecha;
        this.RetiCargo = RetiCargo;
        this.RetiObservacion = RetiObservacion;
    }
}
