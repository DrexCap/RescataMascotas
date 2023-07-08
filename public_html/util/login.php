
<?php
if(isset($_POST['mail'])){
    $correo = $_POST['mail'];
    $contra = $_POST['password'];
    autenticarUsuario($correo, $contra);
}else{
    echo "No se ha enviado datos";
}

function autenticarUsuario($mail, $contrasena) {
    require '../cn/conexion.php';
    // Escapar caracteres especiales en el correo y contraseña
    $mail2 = $conexion->real_escape_string($mail);
    $contrasena1 = $conexion->real_escape_string($contrasena);
    
    // Aplicar SHA-256 al campo de contraseña
    $contrasena2 = hash('sha256', $contrasena1);

    // Consulta para autenticar al usuario
    $sql = "SELECT * FROM usuarios WHERE mailUser = '$mail2' AND passUser = '$contrasena2'";
    $resultado = $conexion->query($sql);

    if ($resultado && $resultado->num_rows > 0) {
        // Usuario autenticado correctamente
        echo "exito";
    }else{
        echo "fallo";
    }

    // Cerrar conexión
    $conexion->close();
}
?>

