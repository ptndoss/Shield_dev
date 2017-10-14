package edu.sjsu.twitterkaraf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementing Twitter APIs to be deployed on karaf
 * @author Anushri Srinath Aithal
 * Nidhi Jamar
 * Aswhwini Shankar Narayan
 * Anuradha Rajashekar
 *
 */
public class TwitterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * servlet doPost method to implement all 8 Twitter APIs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			TwitterHelper twitterHelper = new TwitterHelper();

			String twitteroptions = request.getParameter("twitteroptions");
			String searchStr = request.getParameter("searchStr");
			List<String> twitterResponseList = new ArrayList<String>();

			switch (twitteroptions) {
			case "createfriendship":
				twitterResponseList = twitterHelper.createFriendship(searchStr);
				break;
			case "statusupdate":
				twitterResponseList = twitterHelper.statusUpdate(searchStr);
				break;
			case "trendsavailable":
				twitterResponseList = twitterHelper.trendsAvailable();
				break;
			case "searchHashTag":
				twitterResponseList = twitterHelper.searchHashTag(searchStr);
				break;
			case "homeTimeline":
				twitterResponseList = twitterHelper.homeTimeline();
				break;
			case "languageSupport":
				twitterResponseList = twitterHelper.languageSupport();
				break;
			case "trendsClosest":
				twitterResponseList = twitterHelper.trendsClosest();
				break;
			case "followerList":
				twitterResponseList = twitterHelper.followersList(searchStr);
				break;

			default:
				break;

			}
			request.getSession().setAttribute("twitterResponse", twitterResponseList);
			request.getSession().setAttribute("option", "twitteroptions");
		} catch (Exception e) {
			e.printStackTrace();
		}

		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
		requestDispatcher.forward(request, response);
	}

	/**
	 * Servlet doGet method
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
		requestDispatcher.forward(request, response);
	}

}
