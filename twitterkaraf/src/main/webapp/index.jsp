<html>
<body>
<h2>Twitter Options</h2>
<form action="executeApi" method="POST" >
<input type="text" name="searchStr" value ="">
<select name="twitteroptions">
  <option value="createfriendship">Create Friendship</option>
  <option value="statusupdate">Status Update</option>
  <option value="trendsavailable">Trends Available</option>
  <option value="searchHashTag">Search HashTag</option>
  <option value="homeTimeline">Home Timeline</option>
  <option value="languageSupport">Language Support</option>
  <option value="trendsClosest">Trends Closest</option>
  <option value="followerList">Followers List</option>
</select>
    <input type="submit" value="Submit" />
</form>
<h2>Tweet: <%= session.getAttribute("twitterResponse") %></h2>
</body>
</html>
