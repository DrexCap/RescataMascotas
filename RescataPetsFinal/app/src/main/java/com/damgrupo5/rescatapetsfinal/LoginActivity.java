package com.damgrupo5.rescatapetsfinal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private Button btn_ftPass;
    private Button btn_register;
    private String urlRecoveryPass = "https://rescatapet.000webhostapp.com/pwrecovery.php";

    private EditText mail;
    private EditText password;
    private Button btn_login;

    private String URL_Login = "https://rescatapet.000webhostapp.com/util/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        btn_ftPass = findViewById(R.id.ftPassBtn);
        btn_register = findViewById(R.id.btnRegScreen);

        mail = findViewById(R.id.username);
        password = findViewById(R.id.pass);
        btn_login = findViewById(R.id.btnLogin);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "F", Toast.LENGTH_LONG).show();
                ValidarUsuario(URL_Login);
            }
        });

        btn_ftPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri ftPass = Uri.parse(urlRecoveryPass);
                Intent ft = new Intent(Intent.ACTION_VIEW, ftPass);
                startActivity(ft);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(register);
            }
        });
    }

    private void ValidarUsuario(String LINK){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LINK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().matches("exito")){
                    ////si logra autenticar, guarda en un archivo de shared preferences los datos
                    guardarLogin();
                    ////luego envía al usuario a la actividad perfil
                    Intent perfil = new Intent(getApplicationContext(), PerfilActivity.class);
                    startActivity(perfil);
                    //Toast.makeText(LoginActivity.this,response, Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(LoginActivity.this, "Usuario o contraseña Incorrecta", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ///Mensaje de error para desarrollador
                ///Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                ///Mensaje de error para usuario
                Toast.makeText(LoginActivity.this, "Ups! algo salió mal", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("mail", mail.getText().toString());
                parametros.put("password", password.getText().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(stringRequest);
    }

    private void guardarLogin(){
        SharedPreferences datos = getSharedPreferences("login", Context.MODE_PRIVATE);

        String mailSP = mail.getText().toString();
        String passSP = password.getText().toString();

        SharedPreferences.Editor editor = datos.edit();

        editor.putString("user", mailSP);
        editor.putString("pass", passSP);

        editor.commit();

    }


}