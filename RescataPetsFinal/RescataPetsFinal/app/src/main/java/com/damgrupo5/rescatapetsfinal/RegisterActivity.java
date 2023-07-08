package com.damgrupo5.rescatapetsfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText nombres, apellidos, mail, password, rptaSecreta, telefono;

    private String LINK = "https://rescatapet.000webhostapp.com/util/registro.php";
    private Button btnRegresarLogin;

    private Spinner combo_preguntas, combo_paises;
    private int idPregunta, idPais;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);


        nombres = (EditText)findViewById(R.id.NombreEt);
        apellidos = (EditText)findViewById(R.id.ApellidoEt);
        mail = (EditText)findViewById(R.id.CorreoEt);
        password = (EditText)findViewById(R.id.ContraseñaEt);
        rptaSecreta = (EditText)findViewById(R.id.RespuestaSecreta);
        telefono = (EditText)findViewById(R.id.Telefono);

        ////Asignamos el boton para regresar al login y le damos comportamiento
        btnRegresarLogin = findViewById(R.id.btnBackLogin);
        btnRegresarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(back);
            }
        });

        ///Asignamos los combos a los spinners y sus contenidos
        ///Preguntas Secretas
        combo_preguntas = (Spinner)findViewById(R.id.combobox_preguntas);
        String [] preguntas = {"Selecciona una pregunta", "¿País donde naciste?", "¿Cuál es tu color favorito?",
                "¿Nombre de abuela materna?", "¿Nombre del superhéroe favorito?"};
        ArrayAdapter<String> preguntas_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                preguntas);
        combo_preguntas.setAdapter(preguntas_adapter);
        ///Pais del usuario
        combo_paises = (Spinner)findViewById(R.id.Pais);
        String[] paises = {"Selecciona un país", "Argentina", "Bolivia" , "Chile", "Colombia", "Costa Rica" , "Cuba", "Ecuador",
                "El Salvador", "España", "Guatemala", "Honduras", "México", "Nicaragua", "Panamá", "Paraguay", "Perú", "Puerto Rico",
                "República Dominicana", "Uruguay", "Venezuela"};
        ArrayAdapter<String> paises_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                paises);
        combo_paises.setAdapter(paises_adapter);
    }

    public void Registrar (View view){
        if(ValidarCampos()){
            ///Se crea el request con su respuesta, error y finalmente el getparams
            StringRequest stringRequest = new StringRequest(Request.Method.POST, LINK, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    ///que hacer con la respuesta;
                    if(response.trim().matches("Usuario registrado exitosamente.")){
                        Toast.makeText(RegisterActivity.this,"Registro exitoso", Toast.LENGTH_LONG).show();
                        Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(login);
                    }else {
                        Toast.makeText(RegisterActivity.this,response.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    ///Mensaje de error para desarrollador
                    Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    ///Mensaje de error para usuario
                    //Toast.makeText(RegisterActivity.this, "Ups! algo salió mal", Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parametrosReg = new HashMap<String, String>();
                    parametrosReg.put("nombres", nombres.getText().toString());
                    parametrosReg.put("apellidos", apellidos.getText().toString());
                    parametrosReg.put("correo", mail.getText().toString());
                    parametrosReg.put("password", password.getText().toString());
                    parametrosReg.put("psecreta", String.valueOf(idPregunta));
                    parametrosReg.put("rsecreta", rptaSecreta.getText().toString());
                    parametrosReg.put("pais", String.valueOf(idPais));
                    parametrosReg.put("telef", telefono.getText().toString());

                    return parametrosReg;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
            requestQueue.add(stringRequest);

        }else{
            ///se debe de alertar si algo anda mal
            Toast.makeText(this,"Te olvidaste de algo", Toast.LENGTH_LONG).show();
        }


    }

    public boolean ValidarCampos(){

        boolean respuesta = true;
        String name, app, correo, clave, sa, country, phone;

        name = nombres.getText().toString();
        app = apellidos.getText().toString();
        correo = mail.getText().toString();
        clave = password.getText().toString();
        sa = rptaSecreta.getText().toString();

        phone = telefono.getText().toString();
        ///Revisamos que el nombre no este en blanco
        if(name.isEmpty()){
            respuesta = false;
            nombres.setError("Por favor ingrese un nombre");
        }
        ///Revisamos que los apellidos no esten en blanco
        if(app.isEmpty()){
            respuesta = false;
            apellidos.setError("Por favor ingrese un apellido");
        }
        ////Revisamos que el correo no este en blanco
        if(correo.isEmpty()){
            respuesta = false;
            mail.setError("Por favor ingrese un correo electrónico");
        }
        ///Revisamos que el campo de contraseña no este en blanco
        if(clave.isEmpty()){
            respuesta = false;
            password.setError("Por favor ingrese una contraseña");
        }
        ///Revisamos que se haya seleccionado una pregunta secreta
        AsignarIDpregunta();
        if(idPregunta!=0){
            //Toast.makeText(this, "Id de la pregunta es: " + idPregunta, Toast.LENGTH_LONG).show();
            respuesta = true;
        }else {
            respuesta = false;
            Toast.makeText(this, "Por favor selecciona una pregunta", Toast.LENGTH_LONG).show();
        }
        ///Revisamos que el campo respuesta secreta no este en blanco
        if(sa.isEmpty()){
            respuesta = false;
            rptaSecreta.setError("Por favor ingrese una respuesta secreta");
        }
        ///Revisamos que se haya seleccionado un pais
        AsignarIdPais();
        if(idPais!=0){
            respuesta = true;
        }else{
            respuesta = false;
            Toast.makeText(this, "Por favor selecciona un país", Toast.LENGTH_LONG).show();
        }
        ///Revisamos que el campo telefono no este en blanco
        if(phone.isEmpty()){
            respuesta = false;
            telefono.setError("Por favor ingrese un número de teléfono");
        }else if(phone.length()>9){
            respuesta = false;
            telefono.setError("El número de teléfono tiene más de 9 dígitos");
        }
        ///Finalmente se regresa una respuesta
        return respuesta;
    }

    public void AsignarIDpregunta(){

        String preguntaS = combo_preguntas.getSelectedItem().toString();

        switch (preguntaS){
            case "Selecciona una pregunta":
                idPregunta = 0;
                break;
            case "¿País donde naciste?":
                idPregunta = 1;
                break;
            case "¿Cuál es tu color favorito?":
                idPregunta = 2;
                break;
            case "¿Nombre de abuela materna?":
                idPregunta = 3;
                break;
            case "¿Nombre del superhéroe favorito?":
                idPregunta = 4;
                break;
        }
    }
    public void AsignarIdPais(){
        String paisesS = combo_paises.getSelectedItem().toString();

        switch (paisesS){
            case  "Selecciona un país":
                idPais = 0;
                break;
            case  "Argentina":
                idPais = 1;
                break;
            case  "Bolivia":
                idPais = 2;
                break;
            case  "Chile":
                idPais = 3;
                break;
            case  "Colombia":
                idPais = 4;
                break;
            case  "Costa Rica":
                idPais = 5;
                break;
            case  "Cuba":
                idPais = 6;
                break;
            case  "Ecuador":
                idPais = 7;
                break;
            case  "El Salvador":
                idPais = 8;
                break;
            case  "España":
                idPais = 9;
                break;
            case  "Guatemala":
                idPais = 10;
                break;
            case  "Honduras":
                idPais = 11;
                break;
            case  "México":
                idPais = 12;
                break;
            case  "Nicaragua":
                idPais = 13;
                break;
            case  "Panamá":
                idPais = 14;
                break;
            case  "Paraguay":
                idPais = 15;
                break;
            case  "Perú":
                idPais = 16;
                break;
            case  "Puerto Rico":
                idPais = 17;
                break;
            case  "":
                idPais = 18;
                break;
            case  "Uruguay":
                idPais = 19;
                break;
            case  "República Dominicana":
                idPais = 20;
                break;
        }
    }
}