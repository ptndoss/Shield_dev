package edu.sjsu.twitterkaraf;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

@SuppressWarnings({ "deprecation", "resource" })
public class TwitterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			TwitterHelper twitterHelper = new TwitterHelper();
			OAuthConsumer oAuthConsumer = twitterHelper.getOAuthConsumer();

			String twitteroptions = request.getParameter("twitteroptions");
			String parameter = request.getParameter("parameter");

			switch (twitteroptions) {
			case "createfriendship":
				HttpPost httprequest1 = new HttpPost(
						"https://api.twitter.com/1.1/friendships/create.json?screen_name=" + parameter);
				try {
					oAuthConsumer.sign(httprequest1);
					HttpClient client = new DefaultHttpClient();
					HttpResponse httpresponse = client.execute(httprequest1);
					int statusCode = httpresponse.getStatusLine().getStatusCode();
					System.out.println(statusCode + ":" + httpresponse.getStatusLine().getReasonPhrase());
					if (statusCode == 200)
						request.getSession().setAttribute("tweet", "Request Successful");
					else
						request.getSession().setAttribute("tweet",
								statusCode + ":" + httpresponse.getStatusLine().getReasonPhrase());
				} catch (OAuthMessageSignerException e) {
					e.printStackTrace();
				}
				break;
			case "statusupdate":
				HttpPost httprequest2 = new HttpPost(
						"https://api.twitter.com/1.1/statuses/update.json?status=" + parameter);// Nidhi its working
				try {
					oAuthConsumer.sign(httprequest2);
					HttpClient client = new DefaultHttpClient();
					HttpResponse httpresponse = client.execute(httprequest2);
					int statusCode = httpresponse.getStatusLine().getStatusCode();
					System.out.println(statusCode + ":" + httpresponse.getStatusLine().getReasonPhrase());
					if (statusCode == 200)
						request.getSession().setAttribute("tweet", "Request Successful");
					else
						request.getSession().setAttribute("tweet",
								statusCode + ":" + httpresponse.getStatusLine().getReasonPhrase());
				} catch (OAuthMessageSignerException e) {
					e.printStackTrace();
				}
				break;
			case "trendsavailable":
				HttpGet httprequest3 = new HttpGet("https://api.twitter.com/1.1/trends/available.json");
				try {
					oAuthConsumer.sign(httprequest3);
					HttpClient client = new DefaultHttpClient();
					HttpResponse httpresponse = client.execute(httprequest3);
					int statusCode = httpresponse.getStatusLine().getStatusCode();
					System.out.println(statusCode + ":" + httpresponse.getStatusLine().getReasonPhrase());
					JSONArray json = new JSONArray(EntityUtils.toString(httpresponse.getEntity()));
					// JSONObject jsonobject = new
					// JSONObject(EntityUtils.toString(httpresponse.getEntity())); //for search api
					// JSONArray arr = (JSONArray) jsonobject.get("statuses");
					JSONObject object = (JSONObject) json.get(1);
					String tweetText = (String) object.get("country") + ": " + (String) object.get("url");
					request.getSession().setAttribute("tweet", tweetText);
				} catch (OAuthMessageSignerException e) {
					e.printStackTrace();
				}
				break;
			case "searchHashTag":
				twitterHelper.searchHashTag(request, response);
				break;

			default:
				break;

			}
		} catch (OAuthExpectationFailedException | OAuthCommunicationException | ParseException | JSONException e) {
			e.printStackTrace();
		}
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
		requestDispatcher.forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
		requestDispatcher.forward(request, response);
	}

}
