package com.damgrupo5.rescatapetsfinal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.damgrupo5.rescatapetsfinal.ClasesApoyo.CambiarTipos;
import com.damgrupo5.rescatapetsfinal.ClasesApoyo.DatosUsuario;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.damgrupo5.rescatapetsfinal.databinding.ActivityPerfilBinding;

import java.io.IOException;

public class PerfilActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityPerfilBinding binding;

    private ImageView foto_perfil_principal;
    private TextView nom_ape_principal, mail_principal;

    private String foto_perfil_bd, nombres_bd, apellidos_bd, correo_bd;

    private String ApiRD = "https://rescatapet.000webhostapp.com/util/recoger_datos.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPerfilBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarPerfil.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.inicio2, R.id.editaAnuncios, R.id.mascotaEncontrada, R.id.mascotaPerdida, R.id.mascotaAdopcion, R.id.editaPerfil2)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_perfil);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        ///////////--------------------------------------------------------
        foto_perfil_principal = binding.navView.getHeaderView(0).findViewById(R.id.fotoPerfil2);
        nom_ape_principal = binding.navView.getHeaderView(0).findViewById(R.id.nomUserPerfil);
        mail_principal = binding.navView.getHeaderView(0).findViewById(R.id.mailUserPerfil);

        DatosUsuario du = new DatosUsuario();
        CambiarTipos ct = new CambiarTipos();

        SharedPreferences datosLogin = getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences datosUsuario = getSharedPreferences("datosUsuario", Context.MODE_PRIVATE);

        String user = datosLogin.getString("user", "");
        String pass = datosLogin.getString("pass", "");

        du.RecogerDatos(user,pass,ApiRD, this);

        ///Recojo los datos del sharedpreferences de la foto y lo guardo en una variable local
        foto_perfil_bd = datosUsuario.getString("fotoUsuario", "");
        ///convierto la variable local de tipo string en un drawable que despues lo paso como imagenview
        if(foto_perfil_bd!=""){
            ///Si existe foto en la bd lo muestra en perfil
            try {
                Drawable fotoCambiadaBD = ct.StringABitmap(foto_perfil_bd, this);
                foto_perfil_principal.setImageDrawable(fotoCambiadaBD);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        ///Recojo los datos del sharedpreferences de los nombres, apellidos y correo y lo guardo en variables locales
        nombres_bd = datosUsuario.getString("nombreUsuario", "");
        apellidos_bd = datosUsuario.getString("apeUsuario", "");
        correo_bd = datosLogin.getString("user", "");
        ///Inserto las variables locales en el textview
        if(foto_perfil_bd!=""){
            try {
                nom_ape_principal.setText(nombres_bd + " " + apellidos_bd);
                mail_principal.setText(correo_bd);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_perfil);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}