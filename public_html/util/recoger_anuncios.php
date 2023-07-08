<?php
require '../cn/conexion.php';
if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $sql = "SELECT idAnuncio, idUser, a.idTipoAnun, ta.descTipoAnun, nomMascota, 
        a.idTipoMascota, tm.descTipoMasc, razaMascota, colorMascota,
        a.idCountry, p.nomCountry, lastLocation, reward, phoneUser, fotoMascota
        FROM anuncios a INNER JOIN tipo_anuncio ta 
        on a.idTipoAnun = ta.idTipoAnun
        INNER JOIN paises p 
        on a.idCountry = p.idCountry
        INNER JOIN tipo_mascota tm
        on a.idTipoMascota = tm.idTipoMascota";

    $consulta = $conexion->query($sql);
    $response = array();

    while ($registro = $consulta->fetch_assoc()) {
        $registroFinal = array();
        $registroFinal['idAnuncio'] = $registro['idAnuncio'];
        $registroFinal["idUser"] = $registro["idUser"];
        $registroFinal["idTipoAnun"] = $registro["idTipoAnun"];
        $registroFinal["descTipoAnun"] = $registro["descTipoAnun"];
        $registroFinal["nomMascota"] = $registro["nomMascota"];
        $registroFinal["idTipoMascota"] = $registro["idTipoMascota"];
        $registroFinal["descTipoMasc"] = $registro["descTipoMasc"];
        $registroFinal["razaMascota"] = $registro["razaMascota"];
        $registroFinal["colorMascota"] = $registro["colorMascota"];
        $registroFinal["idCountry"] = $registro["idCountry"];
        $registroFinal["nomCountry"] = $registro["nomCountry"];
        $registroFinal["lastLocation"] = $registro["lastLocation"];
        $registroFinal["reward"] = $registro["reward"];
        $registroFinal["fotoMascota"] = $registro["fotoMascota"];

        $response[] = $registroFinal;
    }

    $conexion->close();

    header('Content-Type: application/json');
    echo json_encode($response);
    exit();
} else {
    echo 'No hay petición';
}