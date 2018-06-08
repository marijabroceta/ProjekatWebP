package youtube.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import youtube.dao.UserDAO;
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 10)


public class SubscribersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String status = "failure";
		String channel = request.getParameter("channel");
		String subscriber = request.getParameter("subscriber");
		UserDAO.deleteSubscribers(channel, subscriber);
		int subsNum = UserDAO.getSubsNumber(channel);
		status = "success";
		
		Map<String,Object> data = new HashMap<>();
		data.put("status",status);
		data.put("subsNum", subsNum);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);
		
		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String status = "failure";
		String channel = request.getParameter("channel");
		String subscriber = request.getParameter("subscriber");
		UserDAO.addSubscribers(channel, subscriber);
		int subsNum = UserDAO.getSubsNumber(channel);
		status = "success";
		
		Map<String,Object> data = new HashMap<>();
		data.put("status",status);
		data.put("subsNum", subsNum);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);
		
		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

}
