package com.damgrupo5.rescatapetsfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private String URL_Login = "https://rescatapet.000webhostapp.com/util/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        ImageView logoImagen = findViewById(R.id.logoRescataPets);
        ///Agregando animaciones
        Animation animacionLogo = AnimationUtils.loadAnimation(this, R.anim.fadein);

        logoImagen.setAnimation(animacionLogo);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ///revisa si hay archivo de shared preferences guardado y si existe logea automaticamente
                cargarLogin();
            }
        }, 4000);
    }

    private void cargarLogin(){
        SharedPreferences datos = getSharedPreferences("login", Context.MODE_PRIVATE);

        String user = datos.getString("user", "");
        String pass = datos.getString("pass", "");



        if(user!=""){
            //Toast.makeText(LoginActivity.this, "Si hay contenido", Toast.LENGTH_LONG).show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_Login, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.trim().matches("exito")){
                        Intent perfil = new Intent(getApplicationContext(), PerfilActivity.class);
                        startActivity(perfil);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parametros = new HashMap<String, String>();
                    parametros.put("mail", user);
                    parametros.put("password", pass);
                    return parametros;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(stringRequest);
        }else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }
}