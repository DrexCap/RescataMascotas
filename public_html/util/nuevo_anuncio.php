<?php

if(isset($_POST['idUser']) && isset($_POST['idTipoAnun']) && isset($_POST['nomMascota']) && isset($_POST['idTipoMascota'])
    && isset($_POST['razaMascota']) && isset($_POST['colorMascota']) && isset($_POST['idCountry'])
    && isset($_POST['lastLocation']) && isset($_POST['reward']) && isset($_POST['phoneUser']) && isset($_POST['fotoMascota'])){


    $userID = $_POST['idUser'];
    $idTipoAnun = $_POST['idTipoAnun'];
    $nomMascota = $_POST['nomMascota'];
    $idTipoMascota = $_POST['idTipoMascota'];
    $razaMascota = $_POST['razaMascota'];
    $colorMascota = $_POST['colorMascota'];
    $idCountry = $_POST['idCountry'];
    $lastLocation = $_POST['lastLocation'];
    $reward = $_POST['reward'];
    $phoneUser = $_POST['phoneUser'];
    $fotoMascota = $_POST['fotoMascota'];

    crearNuevoAnuncio($userID, $idTipoAnun, $nomMascota, $idTipoMascota, $razaMascota, $colorMascota,
        $idCountry, $lastLocation, $reward, $phoneUser, $fotoMascota);
} else{
    echo "Ingresar los datos que faltan";
}

function crearNuevoAnuncio($user, $idTipoAnun, $nomMascota, $idTipoMascota, $razaMascota, $colorMascota,
                          $idCountry, $lastLocation, $reward, $phoneUser, $foto) {
    require '../cn/conexion.php';

    $user = $conexion->real_escape_string($user);
    $idTipoAnun = intval($idTipoAnun);
    $nomMascota = $conexion->real_escape_string($nomMascota);
    $idTipoMascota = intval($idTipoMascota);
    $razaMascota = $conexion->real_escape_string($razaMascota);
    $colorMascota = $conexion->real_escape_string($colorMascota);
    $idCountry = intval($idCountry);
    $lastLocation = $conexion->real_escape_string($lastLocation);
    $reward = $conexion->real_escape_string($reward);
    $phoneUser = intval($conexion->real_escape_string($phoneUser));

    $sql2 = "INSERT INTO anuncios (idUser, idTipoAnun, nomMascota, idTipoMascota, razaMascota, 
                      colorMascota, idCountry, lastLocation, reward, phoneUser, fotoMascota)
            VALUES ('$user', '$idTipoAnun', '$nomMascota', '$idTipoMascota', '$razaMascota', '$colorMascota', 
                    '$idCountry', '$lastLocation', '$reward', '$phoneUser', '$foto')";

    if ($conexion->query($sql2) === TRUE) {
        echo "Anuncio registrado exitosamente";
    } else {
        echo "Error al registrar anuncio: " . $conexion->error;
    }

    // Cerrar conexión
    $conexion->close();
}