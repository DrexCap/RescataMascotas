<?php

if(isset($_POST['mail']) && isset($_POST['password'])){
    
    $correo = $_POST['mail'];
    $clave = $_POST['password'];
    
    recogerDatos($correo, $clave);
    
    
}

function recogerDatos($mail, $pass){
    // Establecer la conexión a la base de datos MySQL
    require '../cn/conexion.php';
    
    // Escapar caracteres especiales en el correo y contraseña
    $mail2 = $conexion->real_escape_string($mail);
    $contrasena1 = $conexion->real_escape_string($pass);
    
    // Aplicar SHA-256 al campo de contraseña
    $contrasena2 = hash('sha256', $contrasena1);
    
    // Consulta SQL para obtener los campos de la tabla usuario
    $query = "SELECT * FROM usuarios WHERE mailUser = '$mail' AND passUser = '$contrasena2'";
    
    // Ejecutar la consulta
    $resultado = mysqli_query($conexion, $query);
    
    // Verificar si la consulta fue exitosa
    if ($resultado) {
        // Obtener los resultados como un array asociativo
        $filas = mysqli_fetch_all($resultado, MYSQLI_ASSOC);
    
        // Convertir el array en formato JSON
        $json = json_encode($filas);
    
        // Imprimir el JSON
        echo $json;
    } else {
        echo "Error al ejecutar la consulta: " . mysqli_error($conexion);
    }
    
    // Cerrar conexión
    $conexion->close();
}