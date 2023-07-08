<?php

session_start();

// Obtener las respuestas del formulario
$email = $_POST['email'];
$questionID = $_POST['question'];
$answer = $_POST['answer'];

// Establecer conexión con la base de datos
require '../cn/conexion.php'; 

// Verificar si el correo electrónico existe en la base de datos
$query = "SELECT * FROM usuarios WHERE mailUser = '$email'";
$result = mysqli_query($conexion, $query);

if (mysqli_num_rows($result) == 0) {
  $_SESSION['message'] = "El correo electrónico no está registrado.";
  header('Location: ../pwrecovery.php');
  exit();
}else{
    
    // Verificar si las respuestas de seguridad son correctas
    $query = "SELECT * FROM respuestas_seguridad WHERE usuario_id = (SELECT idUser FROM usuarios WHERE mailUser = '$email') AND pregunta_id = '$questionID' AND respuesta = '$answer'";
    $result = mysqli_query($conexion, $query);
    
    if (mysqli_num_rows($result) == 0) {
      $_SESSION['message'] = "Las respuestas de seguridad no son correctas.";
      header('Location: ../pwrecovery.php');
      exit();
    }
    
    // Las respuestas de seguridad son correctas, redirigir al formulario de cambio de contraseña
    $_SESSION['email'] = $email;
    header('Location: formulario_cambio.php');
    exit();
    
}


