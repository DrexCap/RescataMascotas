<?php

session_start();

if (!isset($_SESSION['email'])) {
  header('Location: index.php');
  exit();
}

if (isset($_SESSION['message'])) {
  echo $_SESSION['message'];
  unset($_SESSION['message']);
}
?>

<html>
<head>
  <title>Cambiar contraseña</title>
</head>
<body>
  <h1>Cambiar contraseña</h1>
  <form action="actualizar_contraseña.php" method="POST">
    <label for="password">Nueva contraseña:</label>
    <input type="password" id="password" name="password" required><br><br>

    <label for="confirm_password">Confirmar contraseña:</label>
    <input type="password" id="confirm_password" name="confirm_password" required><br><br>

    <button type="submit">Guardar</button>
  </form>
</body>
</html>