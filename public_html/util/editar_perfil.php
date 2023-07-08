<?php
    if(
        isset($_POST['mail']) &&
        isset($_POST['password']) &&
        isset($_POST['nuevaFoto']) &&
        isset($_POST['nuevoTelef'])
    ){
        $correo = $_POST['mail'];
        $contra = $_POST['password'];
        $foto = $_POST['nuevaFoto'];
        $telef = $_POST['nuevoTelef'];
        
        actualizarFoto($correo, $contra, $foto, $telef);
        
    }else{
        echo "No se ha enviado datos";
    }
    
    function actualizarFoto($mail, $contrasena, $nfoto, $ntelef) {
        require '../cn/conexion.php';
        // Escapar caracteres especiales en el correo y contraseña
        $mail2 = $conexion->real_escape_string($mail);
        $contrasena1 = $conexion->real_escape_string($contrasena);
        
        // Aplicar SHA-256 al campo de contraseña
        $contrasena2 = hash('sha256', $contrasena1);
        
        // Consulta para autenticar al usuario
        $sql = "UPDATE usuarios SET phoneUser = '$ntelef', fotoUser = '$nfoto' WHERE mailUser = '$mail' AND passUser = '$contrasena2';";
        $resultado = $conexion->query($sql);
    
        if ($resultado) {
            // Usuario actualizado correctamente
            echo "Se actualizo la foto";
        }else{
            echo "Algo fallo";
        }
    
        // Cerrar conexión
        $conexion->close();
    }
?>