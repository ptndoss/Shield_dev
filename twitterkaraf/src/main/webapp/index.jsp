<html>
<body>
<h2>Twitter Options</h2>
<form action="doPost" method="POST" >
<input type="text" name="parameter" value ="">
<select name="twitteroptions">
  <option value="createfriendship">Create Friendship</option>
  <option value="statusupdate">Status Update</option>
  <option value="trendsavailable">Trends Available</option>
  <option value="searchHashTag">Search HashTag</option>
</select>
    <input type="submit" value="Submit" />
</form>
<div><%= session.getAttribute("tweet") %>
<table style="width:100%">
<tr>
    <th><%= session.getAttribute("header1") %></th>
    <th><%= session.getAttribute("header2") %></th> 
  </tr>
</table>
</div>
</body>
</html>
