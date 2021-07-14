<!DOCTYPE html>
<?php
  $_SESSION["session_count"] = 0;
?>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <!-- linking necessary stylesheets -->
  <link rel="stylesheet" type="text/css" href="style.css">
  <title>Recipe Database - Start</title>
</head>
<body>
  <h1><strong>Start</strong></h1><br>
    <div class="login">
    <form id="login" method="get" action="login.php">
      <center>
        <input type="button" class="log" value="New user?" onclick="location.href='register.php'">
        <br><br>
        <input type="button" class="log" value="Already have an account?" onclick="location.href='logIn.php'">
      </center>
    </form>
</div>
</body>
</html>
