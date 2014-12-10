<html>
<head><title>Create an Auction</title>
</head>
<body>

<h1>Create a New Auction</h1>

<#if category_name??>
<form method=post action="AuctionItem">
<p>Item Name: <input type=text name="item_name">
<p>Description<input type=text name="description">
<p>Minimum Price<input type=text name="min_price">
<p>Duration<select name="duration">
<option value="1 day">1 day</option>
<option value="1 week">1 week</option>
<option value="1 month">1 month</option>
</select>

</form>

<#list attribute_types as attribute_type>
<p>${attribute_type.name}: <input type=text name="${attribute_type.name?html}_value">
</#list>

<#else>
<form method=get action="AuctionItem">

<p>Enter Item Category: <input type=text name="category_name">

<p><input type=submit>

</form>


</#if>

</body>

</html>
