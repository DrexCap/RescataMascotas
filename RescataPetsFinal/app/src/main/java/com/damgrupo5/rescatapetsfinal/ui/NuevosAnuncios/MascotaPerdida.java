package com.damgrupo5.rescatapetsfinal.ui.NuevosAnuncios;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.damgrupo5.rescatapetsfinal.ClasesApoyo.CambiarTipos;
import com.damgrupo5.rescatapetsfinal.ClasesApoyo.DatosMascota;
import com.damgrupo5.rescatapetsfinal.R;
import com.damgrupo5.rescatapetsfinal.ui.OpcionesMenu.EditaAnuncios;
import com.damgrupo5.rescatapetsfinal.ui.OpcionesMenu.EditaPerfil;
import com.damgrupo5.rescatapetsfinal.ui.OpcionesMenu.Inicio;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

public class MascotaPerdida extends Fragment {

    EditText nom_mascota_formulario, raza_mascota_formulario, color_mascota_formulario, ulti_direccion_formulario,
            reward_mascota_formulario ;
    Spinner tipo_mascota_formulario;
    ImageView foto_mascota_formulario;
    Button btn_subir_foto_formulario, btn_registrar_mascota_formulario;

    int idUsuario, tipoMascota, telefUser, idPais;

    int tipoAnuncio = 1;

    String recompensa = "0";
    String fotoMascotaString = "";

    Bitmap fotoMascotaBM;

    String URL_Mascota_Perdida = "https://rescatapet.000webhostapp.com/util/nuevo_anuncio.php";

    private ActivityResultLauncher<String> getContentLauncher;

    public MascotaPerdida() {
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
        View vista = inflater.inflate(R.layout.fragment_mascota_perdida, container, false);

        nom_mascota_formulario = vista.findViewById(R.id.nomMascotaImput);
        raza_mascota_formulario = vista.findViewById(R.id.razaMascotaInput);
        color_mascota_formulario = vista.findViewById(R.id.coloMascotaInput);
        ulti_direccion_formulario = vista.findViewById(R.id.lastLocationInput);
        reward_mascota_formulario = vista.findViewById(R.id.rewardMascotaInput);
        tipo_mascota_formulario = vista.findViewById(R.id.tipoMascotaSpinner);
        foto_mascota_formulario = vista.findViewById(R.id.fotoMascotaFormulario);
        btn_subir_foto_formulario = vista.findViewById(R.id.btnSubirFotoFormulario);
        btn_registrar_mascota_formulario = vista.findViewById(R.id.btnRegAnunMascotaPerdida);


        DatosMascota dm = new DatosMascota();
        CambiarTipos ct = new CambiarTipos();
        Context context = getActivity();

        SharedPreferences datosUsuario = context.getSharedPreferences("datosUsuario", Context.MODE_PRIVATE);

        idUsuario = datosUsuario.getInt("idUsuario", 0);
        telefUser = datosUsuario.getInt("telefUsuario", 0);
        idPais = datosUsuario.getInt("idPais", 0);

        getContentLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        // Reviso si cogí una imagen
                        if(result!=null){
                            foto_mascota_formulario.setImageURI(result);
                            fotoMascotaBM = ct.ImagentoBitmap(foto_mascota_formulario);
                            fotoMascotaString = ct.BitmaptoString(fotoMascotaBM);
                        }
                    }
                });

        ////Se le asigna el adaptador TMAdapter de la clase DatosMascota
        tipo_mascota_formulario.setAdapter(dm.TMAdapter(context));
        ///Se leen los cambios de seleccion del spinner tipo mascota
        tipo_mascota_formulario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ////Se le asigna el valor a tipo mascota segun lo escogido en el spinner
                tipoMascota = dm.TipoMascota(tipo_mascota_formulario);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_subir_foto_formulario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    getContentLauncher.launch("image/*");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btn_registrar_mascota_formulario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               try{
                   if(ValidarFormularioMP()){
                       ///SI EL FORMULARIO ES ACEPTADO
                       StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_Mascota_Perdida, new Response.Listener<String>() {
                           @Override
                           public void onResponse(String response) {
                               if(response.trim().matches("Anuncio registrado exitosamente")){
                                   Snackbar snackbar = Snackbar.make(view, "Anuncio Registrado con éxito", Snackbar.LENGTH_SHORT);
                                   snackbar.show();

                                   nom_mascota_formulario.setText("");
                                   raza_mascota_formulario.setText("");
                                   color_mascota_formulario.setText("");
                                   ulti_direccion_formulario.setText("");
                                   reward_mascota_formulario.setText("");
                                   tipoMascota = 0;
                                   foto_mascota_formulario.setImageResource(R.drawable.ic_launcher_foreground);
                               }

                           }
                       }, new Response.ErrorListener() {
                           @Override
                           public void onErrorResponse(VolleyError error) {
                               Snackbar snackbar = Snackbar.make(view, error.toString(), Snackbar.LENGTH_SHORT);
                               snackbar.show();
                           }
                       }){
                           @Nullable
                           @Override
                           protected Map<String, String> getParams() throws AuthFailureError {
                               Map<String, String> parametros = new HashMap<>();

                               parametros.put("idUser", String.valueOf(idUsuario));
                               parametros.put("idTipoAnun", String.valueOf(tipoAnuncio));
                               parametros.put("nomMascota", nom_mascota_formulario.getText().toString());
                               parametros.put("idTipoMascota", String.valueOf(tipoMascota));
                               parametros.put("razaMascota", raza_mascota_formulario.getText().toString());
                               parametros.put("colorMascota", color_mascota_formulario.getText().toString());
                               parametros.put("idCountry", String.valueOf(idPais));
                               parametros.put("lastLocation", ulti_direccion_formulario.getText().toString());
                               parametros.put("reward", reward_mascota_formulario.getText().toString());
                               parametros.put("phoneUser", String.valueOf(telefUser));
                               parametros.put("fotoMascota", fotoMascotaString);

                               return parametros;
                           }
                       };
                       RequestQueue requestQueue = Volley.newRequestQueue(context);
                       requestQueue.add(stringRequest);
                   }else {
                       /// SI EL FORMULARIO NO ES ACEPTADO
                   }
               } catch (Exception e) {
                   throw new RuntimeException(e);
               }
            }
        });


        return  vista;
    }

    private boolean ValidarFormularioMP(){
        boolean respuesta;

        String nomMascota, razaMascota, colorMascota, lastUbiMascota, rewMascota;

        nomMascota = nom_mascota_formulario.getText().toString();
        razaMascota = raza_mascota_formulario.getText().toString();
        colorMascota = color_mascota_formulario.getText().toString();
        lastUbiMascota = ulti_direccion_formulario.getText().toString();
        rewMascota = reward_mascota_formulario.getText().toString();

        ///Revisamos que el nombre de la mascota no este en blanco
        if(nomMascota.isEmpty()){
            respuesta = false;
            nom_mascota_formulario.setError("Por favor ingrese un nombre");
        }
        ///Revisamos que el tipo de mascota este asignado
        if(tipoMascota!=0){
            respuesta = true;
        }else{
            respuesta = false;
            Toast.makeText(getContext(),"Debes elegir un tipo de mascota", Toast.LENGTH_LONG).show();
        }
        ///Revisamos que haya ingresado el nombre de la mascota
        if(razaMascota.isEmpty()){
            respuesta = false;
            raza_mascota_formulario.setError("Por favor ingrese la raza de su mascota");
        }
        if(colorMascota.isEmpty()){
            respuesta = false;
            color_mascota_formulario.setError("Por favor ingrese el color de su mascota");
        }
        if(lastUbiMascota.isEmpty()){
            respuesta = false;
            Toast.makeText(getContext(),"Debes ingresar una ultima ubicación", Toast.LENGTH_LONG).show();
            ulti_direccion_formulario.setError("Por favor ingrese la última ubicación de su mascota");
        }
        if(rewMascota.isEmpty()){
            recompensa = "0";
        }else{
            recompensa = reward_mascota_formulario.getText().toString();
        }
        if(fotoMascotaString.isEmpty()){
            respuesta = false;
            Toast.makeText(getContext(),"Debes elegir una foto", Toast.LENGTH_LONG).show();
        }

        return respuesta;
    }
}