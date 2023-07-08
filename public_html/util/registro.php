<?php

if(isset($_POST['nombres'])){
    $names = $_POST['nombres'];
    $lastnames = $_POST['apellidos'];
    $correo = $_POST['correo'];
    $passW = $_POST['password'];
    $psecreta = $_POST['psecreta'];
    $rsecreta = $_POST['rsecreta'];
    $pais_res = $_POST['pais'];
    $telef = $_POST['telef'];
    
    registrarUsuario($names, $lastnames, $correo, $passW, $psecreta, $rsecreta, $pais_res, $telef);
}

function registrarUsuario($nombres, $apellidos, $email, $contrasena, $preguntaSecreta, $respuestaSecreta, $pais, $telefono) {
    require '../cn/conexion.php';

    // Escapar caracteres especiales en los datos ingresados
    $nombres = $conexion->real_escape_string($nombres);
    $apellidos = $conexion->real_escape_string($apellidos);
    $email = $conexion->real_escape_string($email);
    $contrasena1 = $conexion->real_escape_string($contrasena);
    // Aplicar SHA-256 al campo de contraseña
    $contrasena2 = hash('sha256', $contrasena1);
    // id de la pregunta secreta y la respuesta como string
    $preguntaSecreta = intval($conexion->real_escape_string($preguntaSecreta));
    $respuestaSecreta = $conexion->real_escape_string($respuestaSecreta);
    //
    $pais = intval($pais);
    $telefono = intval($telefono); // Convertir el teléfono a entero

    // Consulta para insertar el nuevo usuario en la base de datos
    $sql_datos_usuario = "INSERT INTO usuarios (nomUser, apeUser, mailUser, passUser, idCountry, phoneUser, fotoUser)
            VALUES ('$nombres', '$apellidos', '$email', '$contrasena2', '$pais', $telefono, '')";
    // Consulta para insertar nueva respuesta en la base de datos
    $sql_respuestas_usuario = "INSERT INTO respuestas_seguridad (pregunta_id, respuesta) 
            VALUES ($preguntaSecreta, '$respuestaSecreta') ";
    
    if ($conexion->query($sql_datos_usuario) === TRUE && $conexion->query($sql_respuestas_usuario) === TRUE) {
        echo "Usuario registrado exitosamente.";
    } else {
        echo "Error al registrar usuario: " . $conexion->error;
    }

    // Cerrar conexión
    $conexion->close();
}

?>
