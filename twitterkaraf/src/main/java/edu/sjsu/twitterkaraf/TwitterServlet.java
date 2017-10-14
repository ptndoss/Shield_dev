package edu.sjsu.twitterkaraf;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TwitterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			TwitterHelper twitterHelper = new TwitterHelper();

			String twitteroptions = request.getParameter("twitteroptions");

			switch (twitteroptions) {
			case "createfriendship":
				twitterHelper.createFriendship(request, response);
				break;
			case "statusupdate":
				twitterHelper.statusupdate(request, response);
				break;
			case "trendsavailable":
				twitterHelper.trendsavailable(request, response);
				break;
			case "searchHashTag":
				twitterHelper.searchHashTag(request, response);
				break;
			case "languageSupport":
				twitterHelper.languageSupport(request, response);
				break;

			default:
				break;

			}
		} catch (Exception e) {
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
