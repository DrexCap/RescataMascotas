<?php
session_start();

if (isset($_SESSION['message'])) {
  echo $_SESSION['message'];
  unset($_SESSION['message']);
}
?>

<html>
<head>
  <title>Recuperar contraseña</title>
</head>
<body>
  <h1>Recuperar contraseña</h1>
  <?php
  require 'cn/conexion.php';

      // Obtener preguntas de seguridad predeterminadas
      $query = "SELECT * FROM preguntas_seguridad";
      $result = mysqli_query($conexion, $query);
  ?>
  <form action="/util/verificar_respuesta.php" method="POST">
    <label for="email">Correo electrónico:</label>
    <input type="email" id="email" name="email" required><br><br>

    <label for="question">Pregunta de seguridad:</label>
    <select id="question" name="question" required>
      <option value="">Seleccionar pregunta</option>
      <?php
      while ($row = mysqli_fetch_assoc($result)) {
        echo "<option value='" . $row['id'] . "'>" . $row['pregunta'] . "</option>";
      }
      ?>
    </select><br><br>

    <label for="answer">Respuesta de seguridad:</label>
    <input type="text" id="answer" name="answer" required>
    <br><br>
    <button type="submit">Enviar</button>
  </form>
</body>
</html>