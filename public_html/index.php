
<html>
    <head>
        <meta charset="UTF-8">
        <title></title>
    </head>
    <body>
        <?php
         //include 'util/registro.php';
         //include 'util/login.php';
         
         
         //registrarUsuario("Jose", "Lira", "joselira@gmail.com", "123456", "donde naciste", "en lima", "peru", "123456789")
         //autenticarUsuario("joselira@gmail.com", "123456");
         
        ?>
        
        <form action="util/editar_perfil.php" method="POST">
          <label for="username">Nombre de usuario:</label>
          <input type="text" name="mail" id="mail" required>
          <br>
          <label for="password">Contraseña:</label>
          <input type="password" name="password" id="password" required>
          <br>
          <label for="nuevaFoto">Nueva foto:</label>
          <input type="text" name="nuevaFoto" id="nuevaFoto" required>
          <br>
          <label for="nuevoTelef">Nuevo telefono:</label>
          <input type="text" name="nuevoTelef" id="nuevoTelef" required>
          
          <input type="submit" value="Iniciar sesión">
        </form>
        <?php
         
        ?>
    </body>
</html>
