<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Edit Profile &middot; DawgTrades</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="../assets/css/bootstrap.css" rel="stylesheet">
    <style>
      body {
        padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
          background-color: #ff3333;
      }
        
        form-edit .form-edit-heading,
        .form-edit .checkbox {
            margin-bottom: 10px;
        }
        .form-edit input[type="text"],
        .form-edit input[type="password"] {
            font-size: 16px;
            height: auto;
            margin-bottom: 15px;
            padding: 7px 9px;
    </style>
    <link href="../assets/css/bootstrap-responsive.css" rel="stylesheet">

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="../assets/js/html5shiv.js"></script>
    <![endif]-->

    <!-- Fav and touch icons -->
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="../assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="../assets/ico/apple-touch-icon-114-precomposed.png">
      <link rel="apple-touch-icon-precomposed" sizes="72x72" href="../assets/ico/apple-touch-icon-72-precomposed.png">
                    <link rel="apple-touch-icon-precomposed" href="../assets/ico/apple-touch-icon-57-precomposed.png">
                                   <link rel="shortcut icon" href="../assets/ico/favicon.png">
  </head>

  <body>

    <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="brand" href="MainWindow">DawgTrades</a>
          <div class="nav-collapse collapse">
            <ul class="nav">
              <li><a href="MainWindow">Home</a></li>
              <li><a href="Logout">Logout</a></li>
              <li><a href="Contact">Contact</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>

    <div class="container">

        <h1>Edit ${user_name}'s Profile</h1>
        <p>
        <form method=post class="form-edit" action="EditProfile">Please Complete the Form Below</h2>
            
                <p>First Name: <input type="text" name="first_name" class="input-block-level" placeholer="${first_name}">
                <p>Last Name: <input type="text" name="last_name" class="input-block-level" placeholer="${last_name}">
                <p>User Name: <input type="text" name="user_name" class="input-block-level" placeholer="${user_name}">
                <p>Email: <input type="text" name="email_address" class="input-block-level" placeholer="${email_address}">
                <p>Phone #: <input type="text" name="phone" class="input-block-level" placeholer="${phone_number}">
                    <label class="checkbox">
                        <input type="checkbox" value="text"> Opt to recieve text message notifications?
                        </label>
                <button class="btn btn-large btn-inverse" type="submit">Sign in</button>
                <button class="btn btn-large btn-inverse" type="reset">Reset</button>
        </form>
        
        
    </div> <!-- /container -->

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="../assets/js/jquery.js"></script>
    <script src="../assets/js/bootstrap-transition.js"></script>
    <script src="../assets/js/bootstrap-alert.js"></script>
    <script src="../assets/js/bootstrap-modal.js"></script>
    <script src="../assets/js/bootstrap-dropdown.js"></script>
    <script src="../assets/js/bootstrap-scrollspy.js"></script>
    <script src="../assets/js/bootstrap-tab.js"></script>
    <script src="../assets/js/bootstrap-tooltip.js"></script>
    <script src="../assets/js/bootstrap-popover.js"></script>
    <script src="../assets/js/bootstrap-button.js"></script>
    <script src="../assets/js/bootstrap-collapse.js"></script>
    <script src="../assets/js/bootstrap-carousel.js"></script>
    <script src="../assets/js/bootstrap-typeahead.js"></script>

  </body>
</html>
