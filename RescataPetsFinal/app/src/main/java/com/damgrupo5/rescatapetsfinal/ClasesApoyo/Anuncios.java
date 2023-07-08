package com.damgrupo5.rescatapetsfinal.ClasesApoyo;

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

    public Anuncios(String descTipoAnun, String nomMascota) {
        this.descTipoAnun = descTipoAnun;
        this.nomMascota = nomMascota;
    }

    public Anuncios(String idAnuncio, String idUser, String idTipoAnun, String descTipoAnun, String nomMascota, String idTipoMascota, String descTipoMasc, String razaMascota, String colorMascota, String idCountry, String nomCountry, String lastLocation, String reward, String fotoMascota) {
        this.idAnuncio = idAnuncio;
        this.idUser = idUser;
        this.idTipoAnun = idTipoAnun;
        this.descTipoAnun = descTipoAnun;
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
