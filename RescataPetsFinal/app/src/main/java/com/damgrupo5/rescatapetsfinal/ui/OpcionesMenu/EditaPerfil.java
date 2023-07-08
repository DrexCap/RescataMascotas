package com.damgrupo5.rescatapetsfinal.ui.OpcionesMenu;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.damgrupo5.rescatapetsfinal.ClasesApoyo.CambiarTipos;
import com.damgrupo5.rescatapetsfinal.ClasesApoyo.DatosUsuario;
import com.damgrupo5.rescatapetsfinal.PerfilActivity;
import com.damgrupo5.rescatapetsfinal.R;
import com.damgrupo5.rescatapetsfinal.RegisterActivity;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class EditaPerfil extends Fragment {

    private Button btn_cambiar_foto, btn_registrar_edicion;
    private ImageView foto_perfil;
    private Bitmap fotoBitmap;
    private EditText telefono;
    private TextView etiTelefono;
    private String fotoString, fotoBD;

    private int telefonoBD;

    private String ApiLink = "https://rescatapet.000webhostapp.com/util/editar_perfil.php";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_edita_perfil, container, false);


        btn_cambiar_foto = (Button) vista.findViewById(R.id.btnSubirFoto);
        btn_registrar_edicion = (Button) vista.findViewById(R.id.btnRegistrarEdicion);
        telefono = (EditText) vista.findViewById(R.id.nuevoTelef);
        foto_perfil = (ImageView) vista.findViewById(R.id.fotoPerfil);
        etiTelefono = (TextView) vista.findViewById(R.id.etiquetaTelefono);
        Context context = getActivity();

        CambiarTipos ct = new CambiarTipos();

        SharedPreferences datosLogin = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences datosUsuario = context.getSharedPreferences("datosUsuario", Context.MODE_PRIVATE);

        String user = datosLogin.getString("user", "");
        String pass = datosLogin.getString("pass", "");

        fotoBD = datosUsuario.getString("fotoUsuario", "");
        telefonoBD = datosUsuario.getInt("telefUsuario", 0);

        if(fotoBD!=""){
            ///Si existe foto en la bd lo muestra en perfil
            try {

                Drawable fotoCambiadaBD = ct.StringABitmap(fotoBD, context);
                foto_perfil.setImageDrawable(fotoCambiadaBD);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if(telefonoBD!=0){
            try {
                etiTelefono.setText("Teléfono actual: " + telefonoBD);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        btn_cambiar_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cargarImagen();
            }
        });

        btn_registrar_edicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarImagen();
                if(fotoString!=""){
                    ////esto muestra si el campo de string de la foto convertida a bitmap NO esta vacia
                }else{
                    btn_registrar_edicion.setError("NO SE ELIGIO LA FOTO");
                }

                StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiLink, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().matches("Se actualizo la foto")){
                            Snackbar snackbar = Snackbar.make(view, "Foto Actualizada", Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }else{
                            Snackbar snackbar = Snackbar.make(view, "No se pudo actualizar", Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context,"Ups algo salió mal", Toast.LENGTH_LONG).show();
                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parametrosReg = new HashMap<String, String>();
                        parametrosReg.put("mail", user);
                        parametrosReg.put("password", pass);
                        parametrosReg.put("nuevaFoto", fotoString);
                        parametrosReg.put("nuevoTelef", telefono.getText().toString());

                        return parametrosReg;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);
            }
        });

        return vista;
    }



    private void cargarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la imagen"), 10);
    }

    private void registrarImagen(){
        ImagentoBitmap();
        BitmaptoString();
    }

    private void ImagentoBitmap(){
        Drawable drawable = foto_perfil.getDrawable();

        if(drawable instanceof BitmapDrawable){
            // Si el drawable es un BitmapDrawable, extrae el Bitmap
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            fotoBitmap = bitmapDrawable.getBitmap();
        }else {
            // Si el drawable no es un BitmapDrawable, crea un nuevo Bitmap basado en el ancho y alto del ImageView
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            fotoBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(fotoBitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }
    }

    private void BitmaptoString(){
        // Convierte el bitmap en un array de bytes
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        fotoBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        fotoString = Base64.encodeToString(byteArray, Base64.DEFAULT);
        //Log.e("jose", "se convirtió a string" + fotoString);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Uri path = data.getData();
            foto_perfil.setImageURI(path);
        }
    }
}