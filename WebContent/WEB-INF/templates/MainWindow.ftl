<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Homepage &middot; DawgTrades</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet">    <style>
      body {
        padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
          background-color: #ff3333;
      }
    </style>
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
          <a class="brand" href="ShowMainWindow">DawgTrades</a>
          <div class="nav-collapse collapse">
            <ul class="nav">
              <li class="active"><a href="ShowMainWindow">Home</a></li>
              <li><a href="Logout">Logout</a></li>
              <li><a href="Contact">Contact</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>

    <div class="container">

        <h1>Welcome ${user_name}!</h1>
        <p>You may:
        <ol>
        <li> Browse Category:
                <form method=post action="BrowseCategory">
                    <input type=text name="category_name">
                    <input type=submit value="Submit">
                </form>
            </li>
            <li>Track Auction:
                <form action="TrackAuction">
                    <input type=text name="auction_id">
                    <input type=submit value="Submit">
                </form>
</li>

<li>Define Category:
<form method=post action="DefineCategory">
<p>Category Name<input type=text name="category_name">
<p>Parent Name<input type=text name="parent_name">
<p><p>
AttributeType Names:
<input type=text name="at_0">
<input type=text name="at_1">
<input type=text name="at_2">
<input type=text name="at_3">
<input type=submit value="Submit">
</form>
</li>

            <li>
                View your <a href="ViewProfile"> profile</a>
            </li>
            <li>
               <a href="ViewMyAuctions">View your auctions</a>
            </li>
            <li>
                Create a new <a href="AuctionItem">
                    auction</a>
            </li>
            <li>
                Unregister your <a href="Unregister">
                    account</a>
            </li>
            
            <li>
                <a href="Logout"> Logout</a> from the DawgTrades system.
            </li>
        </ol>
</p>
        
    </div> <!-- /container -->

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
 <script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
<script src="http://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.2/html5shiv-printshiv.min.js"></script>
<![endif]-->


  </body>
</html>
