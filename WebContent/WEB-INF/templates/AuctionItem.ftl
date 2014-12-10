<html>
<head><title>Create an Auction</title>
</head>
<body>

<h1>Create a New Auction</h1>

<#if (category)??>
<form method=post action="AuctionItem">
<p>Item Name: <input type=text name="item_name">
<p>Description<input type=text name="description">
<p>Minimum Price<input type=text name="min_price">
<p>Duration<select name="duration">
<option value="1 day">1 day</option>
<option value="1 week">1 week</option>
<option value="1 month">1 month</option>
</select>
<input type=hidden name="category_name" value="${category?html}">
<p>Category Attibutes
<#list attribute_types as attribute_type>
<p>${attribute_type.name}: <input type=text name="${attribute_type.name?html}_value">
</#list>

<p><input type=submit value="Submit">

</form>



<#else>
<form method=post action="PickCategory">

<p>Enter Item Category: <input type=text name="category_name">

<p><input type=submit value="Submit">

</form>


</#if>

</body>

</html>
