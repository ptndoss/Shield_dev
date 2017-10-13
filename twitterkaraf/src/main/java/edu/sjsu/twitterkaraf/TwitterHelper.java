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

import edu.sjsu.twitterkaraf.data.TwitterResponseData;
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

	public void searchHashTag(HttpServletRequest request, HttpServletResponse response) {
		try {
			Properties properties = getProperties();
			String apiUrl = properties.getProperty("twitter.api.search.tweets");
			String searchHashTag = request.getParameter("parameter");
			apiUrl = apiUrl + "?count=10&q=%23" + searchHashTag;
			HttpResponse apiResponse = executeHttpGet(apiUrl);
			if (200 == apiResponse.getStatusLine().getStatusCode()) {
				List<TwitterResponseData> twitterResponseList = new ArrayList<TwitterResponseData>();
				JSONObject jsonobject = new JSONObject(EntityUtils.toString(apiResponse.getEntity()));
				JSONArray jsonArray = (JSONArray) jsonobject.get("statuses");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = (JSONObject) jsonArray.get(i);
					JSONObject userObject = (JSONObject) jsonObject.get("user");
					TwitterResponseData twitterResponseData = new TwitterResponseData();
					twitterResponseData.setId((String) userObject.get("screen_name"));
					twitterResponseData.setText((String) jsonObject.get("text"));
					twitterResponseList.add(twitterResponseData);
				}

				request.getSession().setAttribute("twitterResponse", twitterResponseList);
				request.getSession().setAttribute("header1", "Screen Name");
				request.getSession().setAttribute("header2", "Tweet Text");
				request.getSession().setAttribute("option", "Search Tweet");
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

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
}
