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

        <h1>View Auction! </h1>
        <p>Auction Information:
        <table style="border: 1px black solid;width:auto">
            <tr>
                <th style="border: 1px black solid">Id</th>
                <th style="border: 1px black solid">Amount</th>
                <th style="border: 1px black solid">Item Id</th>
                <th style="border: 1px black solid">Expiration</th>
                <th style="border: 1px black solid">Current Bid</th>
            </tr>
            <#list auctions as auction>
                <tr>
                <td style="border: 1px black solid"> ${auction.id} </td>
                <td style="border: 1px black solid"> ${auction.amount} </td>
                <td style="border: 1px black solid"> ${auction.item_id} </td>
                <td style="border: 1px black solid"> ${auction.expiration} </td>
                </tr>
            </#list>

        </table>
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
