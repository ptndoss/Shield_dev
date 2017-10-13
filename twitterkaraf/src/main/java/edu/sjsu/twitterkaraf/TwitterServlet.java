package edu.sjsu.twitterkaraf;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ConfigurationBuilder configBuilder = new ConfigurationBuilder();
		configBuilder.setDebugEnabled(true);
		configBuilder.setOAuthConsumerKey("Y6oETI68468kyFTJrtIr72KJN");
		configBuilder.setOAuthConsumerSecret("m7cDS8snMfr2erJPEGh21pZjngEBKYnw2Km6AGQGFUmhdxaAcJ");
		configBuilder.setOAuthAccessToken("910234730183794688-qDk13KxpS8bnDzVbh7vCEZ07YNBF6Gj");
		configBuilder.setOAuthAccessTokenSecret("tQxgqvuqIY30PZWAPuDHZELgEZH9IJDr6POj9T6EQ8NOa");
		TwitterFactory twitterFactory = new TwitterFactory(configBuilder.build());
		Twitter twitter = twitterFactory.getInstance();
		Query query = new Query("bigboss");
        QueryResult result;
		try {
			result = twitter.search(query);
			for (Status status : result.getTweets()) {
	            System.out.println("@" + status.getUser().getScreenName() + " : " + status.getText() + " : " + status.getGeoLocation());
	        }
			request.getSession().setAttribute("tweet", result.getTweets().get(0).getText());
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		RequestDispatcher requestDispatcher = request
                .getRequestDispatcher("/index.jsp");
        requestDispatcher.forward(request, response);

	}

}
