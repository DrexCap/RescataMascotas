package com.damgrupo5.rescatapetsfinal.ClasesApoyo;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DatosUsuario {

    public void RecogerDatos(String usuario, String contrasena, String URL, Context contexto){
        //Log.e("jose", String.valueOf(idUsuario));

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        // Guardar los objetos Json en las shared preferences de datos de usuarios
                        int setIdUsuario = (jsonObject.getInt("idUser"));
                        String setNombre = (jsonObject.getString("nomUser"));
                        String setApellido =(jsonObject.getString("apeUser"));
                        int codigoPais = jsonObject.getInt("idCountry");
                        String nomPais = decodPais(codigoPais);
                        int setTelefono = (jsonObject.getInt("phoneUser"));
                        String setFoto = (jsonObject.getString("fotoUser"));

                        guardarDatosUsuario(contexto, setIdUsuario, setNombre, setApellido, codigoPais, nomPais, setTelefono, setFoto );

                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametrosReg = new HashMap<String, String>();
                parametrosReg.put("mail", usuario);
                parametrosReg.put("password", contrasena);
                return parametrosReg;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(contexto);
        requestQueue.add(stringRequest);
    }

    private String decodPais(int codigoPais){
        String nomPais = "";
        switch(codigoPais){
            case 1:
                nomPais =("Argentina");
            break;
            case 2:
                nomPais =("Bolivia");
                break;
            case 3:
                nomPais =("Chile");
                break;
            case 4:
                nomPais =("Colombia");
                break;
            case 5:
                nomPais =("Costa Rica");
                break;
            case 6:
                nomPais =("Cuba");
                break;
            case 7:
                nomPais =("Ecuador");
                break;
            case 8:
                nomPais =("El Salvador");
                break;
            case 9:
                nomPais =("España");
                break;
            case 10:
                nomPais =("Guatemala");
                break;
            case 11:
                nomPais =("Honduras");
                break;
            case 12:
                nomPais =("México");
                break;
            case 13:
                nomPais =("Nicaragua");
                break;
            case 14:
                nomPais =("Panamá");
                break;
            case 15:
                nomPais =("Paraguay");
                break;
            case 16:
                nomPais =("Perú");
                break;
            case 17:
                nomPais =("Puerto Rico");
                break;
            case 18:
                nomPais =("República Dominicana");
                break;
            case 19:
                nomPais =("Uruguay");
                break;
            case 20:
                nomPais =("Venezuela");
                break;
        }
        return nomPais;
    }

    private void guardarDatosUsuario(Context contexto, int idUser, String nomUser, String apeUser,int idCountry , String nomPais, int telefUser,
                                     String fotoUser){

        SharedPreferences datos = contexto.getSharedPreferences("datosUsuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = datos.edit();

        editor.putInt("idUsuario", idUser);
        editor.putInt("telefUsuario", telefUser);
        editor.putString("nombreUsuario", nomUser);
        editor.putString("apeUsuario", apeUser);
        editor.putInt("idPais", idCountry);
        editor.putString("nombrePais", nomPais);
        editor.putString("fotoUsuario", fotoUser);
        editor.commit();

    }
}


