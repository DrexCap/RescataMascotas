package com.damgrupo5.rescatapetsfinal.ui.NuevosAnuncios;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;


public class MascotaAdopcion extends Fragment {

    EditText RazaMascota, ColorMascota;

    Spinner tipoMascotaFormulario;
    ImageView foto_mascota_formulario;
    Button btn_subir_foto_formulario, btn_registrar_mascota_formulario;

    int idUsuario, tipoMascota, telefUser, idPais;

    int tipoAnuncio = 3;

    String fotoMascotaString = "";

    Bitmap fotoMascotaBM;

    String URL_Mascota_Perdida = "https://rescatapet.000webhostapp.com/util/nuevo_anuncio.php";

    private ActivityResultLauncher<String> getContentLauncher;

    public MascotaAdopcion() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_mascota_adopcion, container, false);

        tipoMascotaFormulario = vista.findViewById(R.id.TipoMascotaAdopcion);
        RazaMascota = vista.findViewById(R.id.RazaMascotaAdopcion);
        ColorMascota = vista.findViewById(R.id.ColorMascotaAdopcion);
        foto_mascota_formulario = vista.findViewById(R.id.imagenRequestAdopcion);
        btn_subir_foto_formulario = vista.findViewById(R.id.btnSubirImagenAdopcion);
        btn_registrar_mascota_formulario = vista.findViewById(R.id.btnRegistrarAnuncioAdopcion);

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

        tipoMascotaFormulario.setAdapter(dm.TMAdapter(context));
        ///Se leen los cambios de seleccion del spinner tipo mascota
        tipoMascotaFormulario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ////Se le asigna el valor a tipo mascota segun lo escogido en el spinner
                tipoMascota = dm.TipoMascota(tipoMascotaFormulario);
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

                                    // Cambiar de fragmento(no funciona, lo mejor es eliminar todos los campos
                                   /*
                                   try {
                                       FragmentManager fragmentManager = getChildFragmentManager();
                                       FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                       fragmentTransaction.replace(R.id.drawer_layout, new Inicio());
                                       fragmentTransaction.commit();

                                   } catch (Exception e) {
                                       Log.e("jose", e.toString());
                                   }*/

                                    RazaMascota.setText("");
                                    ColorMascota.setText("");
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
                                parametros.put("nomMascota", "");
                                parametros.put("idTipoMascota", String.valueOf(tipoMascota));
                                parametros.put("razaMascota", RazaMascota.getText().toString());
                                parametros.put("colorMascota", ColorMascota.getText().toString());
                                parametros.put("idCountry", String.valueOf(idPais));
                                parametros.put("lastLocation", "");
                                parametros.put("reward", "");
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

        String razaMascota, colorMascota;

        razaMascota = RazaMascota.getText().toString();
        colorMascota = ColorMascota.getText().toString();

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
            RazaMascota.setError("Por favor ingrese la raza de su mascota");
        }

        if(colorMascota.isEmpty()){
            respuesta = false;
            ColorMascota.setError("Por favor ingrese el color de su mascota");
        }

        if(fotoMascotaString.isEmpty()){
            respuesta = false;
            Toast.makeText(getContext(),"Debes elegir una foto", Toast.LENGTH_LONG).show();
        }

        return respuesta;
    }

}