package edu.sjsu.twitterkaraf;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

@SuppressWarnings({ "deprecation", "resource" })
public class TwitterHelper {

	private Properties getProperties() throws IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream("/properties/application.properties");
		Properties properties = new Properties();
		properties.load(input);
		return properties;
	}

	public OAuthConsumer getOAuthConsumer() throws IOException {
		Properties properties = getProperties();
		String consumerKeyStr = properties.getProperty("twitter.consumer.key");
		String consumerSecretStr = properties.getProperty("twitter.consumet.secret");
		String accessTokenStr = properties.getProperty("twitter.access.token");
		String accessTokenSecretStr = properties.getProperty("twitter.access.secret");
		OAuthConsumer oAuthConsumer = new CommonsHttpOAuthConsumer(consumerKeyStr, consumerSecretStr);
		oAuthConsumer.setTokenWithSecret(accessTokenStr, accessTokenSecretStr);
		return oAuthConsumer;
	}

	private HttpResponse executeHttpPost(String apiUrl) throws OAuthMessageSignerException,
			OAuthExpectationFailedException, OAuthCommunicationException, IOException {
		HttpPost httprequest = new HttpPost(apiUrl);
		getOAuthConsumer().sign(httprequest);
		HttpClient client = new DefaultHttpClient();
		HttpResponse httpresponse = client.execute(httprequest);
		int statusCode = httpresponse.getStatusLine().getStatusCode();
		System.out.println(statusCode + ":" + httpresponse.getStatusLine().getReasonPhrase());
		return httpresponse;
	}

	private HttpResponse executeHttpGet(String apiUrl) throws OAuthMessageSignerException,
			OAuthExpectationFailedException, OAuthCommunicationException, IOException {
		HttpGet httprequest = new HttpGet(apiUrl);
		getOAuthConsumer().sign(httprequest);
		HttpClient client = new DefaultHttpClient();
		HttpResponse httpresponse = client.execute(httprequest);
		int statusCode = httpresponse.getStatusLine().getStatusCode();
		System.out.println(statusCode + ":" + httpresponse.getStatusLine().getReasonPhrase());
		return httpresponse;
	}

	public void searchHashTag(HttpServletRequest request, HttpServletResponse response) {
		try {
			Properties properties = getProperties();
			String apiUrl = properties.getProperty("twitter.api.search.tweets");

			String searchHashTag = request.getParameter("searchStr");
			apiUrl = apiUrl + "?count=10&q=%23" + searchHashTag;

			HttpResponse apiResponse = executeHttpGet(apiUrl);

			if (200 == apiResponse.getStatusLine().getStatusCode()) {

				List<String> twitterResponseList = new ArrayList<String>();

				JSONObject jsonobject = new JSONObject(EntityUtils.toString(apiResponse.getEntity()));
				JSONArray jsonArray = (JSONArray) jsonobject.get("statuses");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = (JSONObject) jsonArray.get(i);
					JSONObject userObject = (JSONObject) jsonObject.get("user");
					String displayText = (String) userObject.get("screen_name") + " : "
							+ (String) jsonObject.get("text");
					twitterResponseList.add(displayText);
				}

				request.getSession().setAttribute("twitterResponse", twitterResponseList);
				request.getSession().setAttribute("option", "Search Tweet");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void createFriendship(HttpServletRequest request, HttpServletResponse response) {
		try {
			Properties properties = getProperties();
			String apiUrl = properties.getProperty("twitter.api.createfriendship");

			String searchStr = request.getParameter("searchStr");
			apiUrl = apiUrl + "?screen_name=" + searchStr;

			HttpResponse apiResponse = executeHttpPost(apiUrl);

			if (200 == apiResponse.getStatusLine().getStatusCode()) {
				request.getSession().setAttribute("twitterResponse", "API call was Successful");
				request.getSession().setAttribute("option", "Create Friendship");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void statusupdate(HttpServletRequest request, HttpServletResponse response) {
		try {
			Properties properties = getProperties();
			String apiUrl = properties.getProperty("twitter.api.status.update");

			String searchStr = request.getParameter("searchStr");
			apiUrl = apiUrl + "?status=" + searchStr;

			HttpResponse apiResponse = executeHttpPost(apiUrl);

			if (200 == apiResponse.getStatusLine().getStatusCode()) {
				request.getSession().setAttribute("twitterResponse", "API call was Successful");
				request.getSession().setAttribute("option", "Status Update");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void trendsavailable(HttpServletRequest request, HttpServletResponse response) {
		try {
			Properties properties = getProperties();
			String apiUrl = properties.getProperty("twitter.api.trends.available");

			HttpResponse apiResponse = executeHttpGet(apiUrl);

			if (200 == apiResponse.getStatusLine().getStatusCode()) {

				List<String> twitterResponseList = new ArrayList<String>();
				JSONArray jsonArray = new JSONArray(EntityUtils.toString(apiResponse.getEntity()));
				for (int i = 0; i < jsonArray.length() || i == 10; i++) {
					JSONObject object = (JSONObject) jsonArray.get(0);
					String displayText = (String) object.get("country") + " : " + (String) object.get("url");
					twitterResponseList.add(displayText);
				}

				request.getSession().setAttribute("twitterResponse", twitterResponseList);
				request.getSession().setAttribute("option", "Trends Available");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void languageSupport(HttpServletRequest request, HttpServletResponse response) {
		try {
			Properties properties = getProperties();
			String apiUrl = properties.getProperty("twitter.api.language.support");

			HttpResponse apiResponse = executeHttpGet(apiUrl);

			if (200 == apiResponse.getStatusLine().getStatusCode()) {

				List<String> twitterResponseList = new ArrayList<String>();
				JSONArray jsonArray = new JSONArray(EntityUtils.toString(apiResponse.getEntity()));
				for (int i = 0; i < jsonArray.length() || i == 10; i++) {
					JSONObject object = (JSONObject) jsonArray.get(i);
					String displayText = (String) object.get("name");
					twitterResponseList.add(displayText);
				}

				request.getSession().setAttribute("twitterResponse", twitterResponseList);
				request.getSession().setAttribute("option", "Language Support");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void homeTimeline(HttpServletRequest request, HttpServletResponse response) {
		try {
			Properties properties = getProperties();
			String apiUrl = properties.getProperty("twitter.api.home.timeline") + "?count=10";

			HttpResponse apiResponse = executeHttpGet(apiUrl);

			if (200 == apiResponse.getStatusLine().getStatusCode()) {

				List<String> twitterResponseList = new ArrayList<String>();

				JSONArray jsonArray = new JSONArray(EntityUtils.toString(apiResponse.getEntity()));
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = (JSONObject) jsonArray.get(i);
					JSONObject userObject = (JSONObject) jsonObject.get("user");
					String displayText = (String) userObject.get("screen_name") + " : "
							+ (String) jsonObject.get("text");
					twitterResponseList.add(displayText);
				}

				request.getSession().setAttribute("twitterResponse", twitterResponseList);
				request.getSession().setAttribute("option", "Home Timeline");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void trendsClosest(HttpServletRequest request, HttpServletResponse response) {
		try {
			Properties properties = getProperties();
			String apiUrl = properties.getProperty("twitter.api.trends.closest") + "?lat=37.7749&long=122.4194";

			HttpResponse apiResponse = executeHttpGet(apiUrl);

			if (200 == apiResponse.getStatusLine().getStatusCode()) {

				List<String> twitterResponseList = new ArrayList<String>();
				JSONArray jsonArray = new JSONArray(EntityUtils.toString(apiResponse.getEntity()));
				for (int i = 0; i < jsonArray.length() || i == 10; i++) {
					JSONObject object = (JSONObject) jsonArray.get(i);
					String displayText = (String) object.get("country") + " : " + (String) object.get("url");
					twitterResponseList.add(displayText);
				}

				request.getSession().setAttribute("twitterResponse", twitterResponseList);
				request.getSession().setAttribute("option", "Trends Closest");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void followersList(HttpServletRequest request, HttpServletResponse response) {
		try {
			Properties properties = getProperties();
			String searchStr = request.getParameter("searchStr");
			String apiUrl = properties.getProperty("twitter.api.follower.list") + "?screen_name=" + searchStr;

			HttpResponse apiResponse = executeHttpGet(apiUrl);

			if (200 == apiResponse.getStatusLine().getStatusCode()) {

				List<String> twitterResponseList = new ArrayList<String>();
				JSONArray jsonArray = new JSONArray(EntityUtils.toString(apiResponse.getEntity()));
				for (int i = 0; i < jsonArray.length() || i == 10; i++) {
					JSONObject object = (JSONObject) jsonArray.get(i);
					String displayText = (String) object.get("name") + " : " + (String) object.get("screen_name");
					twitterResponseList.add(displayText);
				}

				request.getSession().setAttribute("twitterResponse", twitterResponseList);
				request.getSession().setAttribute("option", "Followers List");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
