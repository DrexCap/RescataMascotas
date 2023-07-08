package com.damgrupo5.rescatapetsfinal.ui.OpcionesMenu;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.damgrupo5.rescatapetsfinal.ClasesApoyo.Anuncios;
import com.damgrupo5.rescatapetsfinal.ClasesApoyo.AnunciosAdapter;
import com.damgrupo5.rescatapetsfinal.ClasesApoyo.DatosAnuncios;
import com.damgrupo5.rescatapetsfinal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Inicio extends Fragment {

    private ArrayList<Anuncios> anunciosArrayList;
    private String[] anunciosTitulo;
    private int[] imageResource;

    private String[] nomMascotaAnuncio;
    private RecyclerView recyclerView;

    //////////Variables de llamado a api y base de datos
    String URL_Api = "https://rescatapet.000webhostapp.com/util/recoger_anuncios.php";
    ArrayList<String> datosBD = new ArrayList<>();


    public Inicio() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /////AQUI ES DONDE HAY QUE LLAMAR A LA FUNCION DE LLENADO DE ARRAYLIST

        DatosAnuncios da = new DatosAnuncios();
        Context context = getContext();

        try {
            da.RecogerDatosAnuncios(URL_Api, context, new DatosAnuncios.VolleyCallback() {
                @Override
                public void onSuccess(ArrayList<Anuncios> datosList) {
                    Log.e("jose", "SI HAY RESPUESTA DEL VOLLEY");

                    dataInitialize();

                    recyclerView = view.findViewById(R.id.recViewInicio);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setHasFixedSize(true);

                    AnunciosAdapter anunciosAdapter = new AnunciosAdapter(getContext(),anunciosArrayList);
                    recyclerView.setAdapter(anunciosAdapter);
                    anunciosAdapter.notifyDataSetChanged();
                }

                @Override
                public void onError(String mensajeError) {
                    Log.e("jose", mensajeError);
                }
            });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void dataInitialize() {
        anunciosArrayList = new ArrayList<>();

        anunciosTitulo = new String[datosBD.size()];

        for (int i = 0; i < datosBD.size(); i++) {
            String dato = datosBD.get(3); // Obtiene el dato del ArrayList
            anunciosTitulo[i] = dato; // Agrega el dato al array
        }

        nomMascotaAnuncio = new String[datosBD.size()];

        for (int i = 0; i < datosBD.size(); i++) {
            String dato = datosBD.get(4); // Obtiene el dato del ArrayList
            //Log.e("jose", dato);
            nomMascotaAnuncio[i] = dato; // Agrega el dato al array
        }
        /*
        imageResource = new int[]{
                R.drawable.perfilvacio,
                R.drawable.perfilvacio,
                R.drawable.perfilvacio,
                R.drawable.perfilvacio,
                R.drawable.perfilvacio,
                R.drawable.perfilvacio,
                R.drawable.perfilvacio,
                R.drawable.perfilvacio,
                R.drawable.perfilvacio,
        };*/

        for(int i = 0; i < anunciosTitulo.length; i++){
            //Anuncios anuncios = new Anuncios(anunciosTitulo[i], nomMascotaAnuncio[i]);
            Anuncios anunciosDB = new Anuncios("idAnuncio", "idUser",  "idTipoAnun",  anunciosTitulo[i], nomMascotaAnuncio[i],
                     "idTipoMascota",  "descTipoMasc",  "razaMascota",  "colorMascota",
                     "idCountry",  "nomCountry",  "lastLocation",  "reward",  "fotoMascota");
            anunciosArrayList.add(anunciosDB);
        }

    }
}