package com.damgrupo5.rescatapetsfinal.ClasesApoyo;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DatosAnuncios {


    public void RecogerDatosAnuncios(String URL, Context contexto, final VolleyCallback callback) {
        final ArrayList<Anuncios> datosList = new ArrayList<>();

        // Crea la solicitud JSONArrayRequest
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Log.e("jose", "NO HAY RESPUESTA DEL JSON");
                        // Procesa el JSONArray y guarda los datos en el ArrayList
                        try {
                            ArrayList<String> listaValores = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject objeto = response.getJSONObject(i);

                                // Parsea los valores del objeto JSON
                                String idAnuncio = objeto.getString("idAnuncio");
                                String idUser = objeto.getString("idUser");
                                String idTipoAnun = objeto.getString("idTipoAnun");
                                String descTipoAnun = objeto.getString("descTipoAnun");
                                String nomMascota = objeto.getString("nomMascota");
                                String idTipoMascota = objeto.getString("idTipoMascota");
                                String descTipoMasc = objeto.getString("descTipoMasc");
                                String razaMascota = objeto.getString("razaMascota");
                                String colorMascota = objeto.getString("colorMascota");
                                String idCountry = objeto.getString("idCountry");
                                String nomCountry = objeto.getString("nomCountry");
                                String lastLocation = objeto.getString("lastLocation");
                                String reward = objeto.getString("reward");
                                String fotoMascota = objeto.getString("fotoMascota");

                                // Construye el objeto Anuncio con los datos obtenidos
                                Anuncios anuncio = new Anuncios(idAnuncio, idUser, idTipoAnun, descTipoAnun,
                                        nomMascota, idTipoMascota, descTipoMasc, razaMascota, colorMascota,
                                        idCountry, nomCountry, lastLocation, reward, fotoMascota);

                                // Agrega el objeto Anuncio al ArrayList
                                datosList.add(anuncio);
                            }
                            callback.onSuccess(datosList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onError(e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Maneja el error de la solicitud
                        callback.onError(error.getMessage());
                    }
                });

        // Agrega la solicitud a la cola de solicitudes
        RequestQueue requestQueue = Volley.newRequestQueue(contexto);
        requestQueue.add(jsonArrayRequest);
    }

    public interface VolleyCallback {
        void onSuccess(ArrayList<Anuncios> datosList);
        void onError(String mensajeError);
    }


}
