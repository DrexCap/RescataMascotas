package com.damgrupo5.rescatapetsfinal.ClasesApoyo;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class DatosMascota {

    public int TipoMascota(Spinner comboTipos){
        String combo = comboTipos.getSelectedItem().toString();
        int idSeleccion = 0;
        switch (combo){
            case "Selecciona un tipo de mascota":
                idSeleccion = 0;
                break;
            case "Perro":
                idSeleccion = 1;
                break;
            case "Gato":
                idSeleccion = 2;
                break;
            case "Otro":
                idSeleccion = 3;
                break;
        }
        return idSeleccion;
    }

    public SpinnerAdapter TMAdapter(Context context){

        String[] paises = {"Selecciona un tipo de mascota", "Perro", "Gato", "Otro"};
        ArrayAdapter<String> paises_adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item,
                paises);
        return paises_adapter;
    }

}
