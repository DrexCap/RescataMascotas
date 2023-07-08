package com.damgrupo5.rescatapetsfinal.ClasesApoyo;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Anuncios {

    String idAnuncio;
    String idUser;
    String idTipoAnun;
    String descTipoAnun;
    String nomMascota;
    String idTipoMascota;
    String descTipoMasc;
    String razaMascota;
    String colorMascota;
    String idCountry;
    String nomCountry;
    String lastLocation;
    String reward;
    String fotoMascota;

    int imagenAnuncio;

    public String getIdAnuncio() {
        return idAnuncio;
    }

    public void setIdAnuncio(String idAnuncio) {
        this.idAnuncio = idAnuncio;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdTipoAnun() {
        return idTipoAnun;
    }

    public void setIdTipoAnun(String idTipoAnun) {
        this.idTipoAnun = idTipoAnun;
    }

    public String getDescTipoAnun() {
        return descTipoAnun;
    }

    public void setDescTipoAnun(String descTipoAnun) {
        this.descTipoAnun = descTipoAnun;
    }

    public String getNomMascota() {
        return nomMascota;
    }

    public void setNomMascota(String nomMascota) {
        this.nomMascota = nomMascota;
    }

    public String getIdTipoMascota() {
        return idTipoMascota;
    }

    public void setIdTipoMascota(String idTipoMascota) {
        this.idTipoMascota = idTipoMascota;
    }

    public String getDescTipoMasc() {
        return descTipoMasc;
    }

    public void setDescTipoMasc(String descTipoMasc) {
        this.descTipoMasc = descTipoMasc;
    }

    public String getRazaMascota() {
        return razaMascota;
    }

    public void setRazaMascota(String razaMascota) {
        this.razaMascota = razaMascota;
    }

    public String getColorMascota() {
        return colorMascota;
    }

    public void setColorMascota(String colorMascota) {
        this.colorMascota = colorMascota;
    }

    public String getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(String idCountry) {
        this.idCountry = idCountry;
    }

    public String getNomCountry() {
        return nomCountry;
    }

    public void setNomCountry(String nomCountry) {
        this.nomCountry = nomCountry;
    }

    public String getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(String lastLocation) {
        this.lastLocation = lastLocation;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getFotoMascota() {
        return fotoMascota;
    }

    public void setFotoMascota(String fotoMascota) {
        this.fotoMascota = fotoMascota;
    }

    public Anuncios(String idAnuncio, String idUser, String idTipoAnun, String descTipoAnun, String nomMascota,
                    String idTipoMascota, String descTipoMasc, String razaMascota, String colorMascota,
                    String idCountry, String nomCountry, String lastLocation, String reward, String fotoMascota) {


        this.idAnuncio = idAnuncio;
        this.idUser = idUser;
        this.idTipoAnun = idTipoAnun;
        this.descTipoAnun = descTipoAnun;
        ///Log.e("jose", descTipoAnun);
        this.nomMascota = nomMascota;
        this.idTipoMascota = idTipoMascota;
        this.descTipoMasc = descTipoMasc;
        this.razaMascota = razaMascota;
        this.colorMascota = colorMascota;
        this.idCountry = idCountry;
        this.nomCountry = nomCountry;
        this.lastLocation = lastLocation;
        this.reward = reward;
        this.fotoMascota = fotoMascota;
    }
}
