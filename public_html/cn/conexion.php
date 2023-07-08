<?php
$host = 'localhost';
$usuario = 'id20817973_jlira';
$password = '}*6QDAo=IJH-TCeq';
$base_datos = 'id20817973_rescatapet';

// Establecer conexión
$conexion = new mysqli($host, $usuario, $password, $base_datos);

// Verificar si la conexión fue exitosa
if ($conexion->connect_errno) {
    die("Error al conectarse a la base de datos: " . $conexion->connect_error);
}


?>