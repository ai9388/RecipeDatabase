<!DOCTYPE html>

<?php
  $username = "123456";
  $password = "12345";

  exec("java C:\wamp64\www\src\Login.java $username $password");

  //header("Location: home.html");
?>

<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <!-- linking necessary stylesheets -->
  <link rel="stylesheet" type="text/css" href="style.css">
  <title>Recipe Database - Returning User</title>
</head>
<body>
  <h1><strong>Log in</strong></h1><br>
    <div class="login">
    <form id="login" method="post" action="logIn.php?id=1">
      <center>
        <div id="errorMessage"></div>
        <label><b>User Name</b></label>
        <input type="text" name="Uname" id="Uname" placeholder="Username">
        <br><br><br>
        <label><b>Password</b></label>
        <input type="Password" name="Pass" id="Pass" placeholder="Password">
        <br><br>
        <input type="button" name="log" class="log" value="Log In" onclick="validateForm()">
        <br><br><br>
        <input type="button" value="New user?" class="log" onclick="location.href='register.php'">
      </center>
    </form>
</div>

<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="script.js"></script>

<script type="text/javascript">
function validateForm(){
  var username = document.getElementById("Uname").value;
  var password = document.getElementById("Pass").value;

  var usernameBool = true;
  var passwordBool = true;

  if ( username == "" || username == " " || username == null ) {
      alert("Username must be filled out");
      login.Uname.focus();
      usernameBool = false;
  }

  if ( password == "" || password == " " || password == null ) {
      alert("Password must be filled out");
      login.Pass.focus();
      passwordBool = false;
  }

  if (usernameBool == false || passwordBool == false) {
      alert("One or more credentials are missing");
  }
  console.log("button clicked");
}

</script>
</body>
</html>
